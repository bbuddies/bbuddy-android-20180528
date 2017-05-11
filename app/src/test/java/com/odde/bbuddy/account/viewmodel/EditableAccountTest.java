package com.odde.bbuddy.account.viewmodel;

import com.nitorcreations.junit.runners.NestedRunner;
import com.odde.bbuddy.account.api.AccountsApi;
import com.odde.bbuddy.account.view.AccountView;
import com.odde.bbuddy.account.view.AccountsNavigation;
import com.odde.bbuddy.common.functional.Consumer;
import com.odde.bbuddy.common.validation.Validator;
import com.odde.bbuddy.common.validation.Violation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static com.odde.bbuddy.account.builder.AccountBuilder.emptyAccount;
import static com.odde.bbuddy.common.CallbackInvoker.callConsumerArgumentAtIndexWith;
import static com.odde.bbuddy.common.CallbackInvoker.callRunnableAtIndex;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(NestedRunner.class)
public class EditableAccountTest {

    AccountsApi mockAccountsApi = mock(AccountsApi.class);
    AccountsNavigation mockAccountsNavigation = mock(AccountsNavigation.class);
    AccountView mockAccountView = mock(AccountView.class);
    Validator stubValidator = mock(Validator.class);
    EditableAccount editableAccount = new EditableAccount(mockAccountsApi, mockAccountsNavigation, stubValidator, mockAccountView);
    Violation violation = new Violation("field", "may not be blank");

    public class Add {

        @Test
        public void add_should_correctly_add_account() {
            addAccount("name", 100);

            verifyAccountsAddWithAccount(emptyAccount().name("name").balanceBroughtForward(100).build());
        }

        @Test
        public void add_should_show_all_accounts_after_add_account_success() {
            given_add_account_will_success();

            addAccount("name", 100);

            verify(mockAccountsNavigation).navigate();
        }

        private void given_add_account_will_success() {
            callRunnableAtIndex(1).when(mockAccountsApi).addAccount(any(Account.class), any(Runnable.class));
        }

        private void verifyAccountsAddWithAccount(Account account) {
            ArgumentCaptor<Account> captor = forClass(Account.class);
            verify(mockAccountsApi).addAccount(captor.capture(), any(Runnable.class));
            assertThat(captor.getValue()).isEqualToComparingFieldByField(account);
        }

    }

    public class Edit {

        @Test
        public void edit_should_correctly_edit_account() {
            given_account_id_is(1);

            editAccount("name", 100);

            verifyAccountsEditWithAccount(emptyAccount().id(1).name("name").balanceBroughtForward(100).build());
        }

        @Test
        public void edit_should_show_all_accounts_after_success() {
            given_account_id_is(1);
            given_edit_account_will_success();

            editAccount("name", 100);

            verify(mockAccountsNavigation).navigate();
        }

        private void verifyAccountsEditWithAccount(Account account) {
            ArgumentCaptor<Account> captor = forClass(Account.class);
            verify(mockAccountsApi).editAccount(captor.capture(), any(Runnable.class));
            assertThat(captor.getValue()).isEqualToComparingFieldByField(account);
        }

        private void given_edit_account_will_success() {
            callRunnableAtIndex(1).when(mockAccountsApi).editAccount(any(Account.class), any(Runnable.class));
        }
    }

    public class Delete {

        @Test
        public void delete_should_correctly_delete_account() {
            given_account_id_is(1);

            editableAccount.delete();

            verifyAccountsDeleteWithAccount(emptyAccount().id(1).build());
        }

        @Test
        public void delete_should_show_all_accounts_after_success() {
            given_account_id_is(1);
            given_account_delete_will_success();

            editableAccount.delete();

            verify(mockAccountsNavigation).navigate();
        }

        private void verifyAccountsDeleteWithAccount(Account account) {
            ArgumentCaptor<Account> captor = forClass(Account.class);
            verify(mockAccountsApi).deleteAccount(captor.capture(), any(Runnable.class));
            assertThat(captor.getValue()).isEqualToComparingFieldByField(account);
        }

        private void given_account_delete_will_success() {
            callRunnableAtIndex(1).when(mockAccountsApi).deleteAccount(any(Account.class), any(Runnable.class));
        }

    }

    public class ShowBalance {

        @Test
        public void display_balance_brought_forward_for_view() {
            editableAccount.setBalanceBroughtForward(100);

            assertEquals("100", editableAccount.getBalanceBroughtForwardForView());
        }

        @Test
        public void get_balance_brought_forward_from_view() {
            editableAccount.setBalanceBroughtForwardForView("100");

            assertEquals(100, editableAccount.getBalanceBroughtForward());
        }

        @Test
        public void value_not_changed_if_balance_brought_forward_from_view_is_not_a_number() {
            editableAccount.setBalanceBroughtForward(50);

            editableAccount.setBalanceBroughtForwardForView("not a number");

            assertEquals(50, editableAccount.getBalanceBroughtForward());
        }

        @Test
        public void balance_should_be_0_if_balance_from_view_is_empty() {
            editableAccount.setBalanceBroughtForward(50);

            editableAccount.setBalanceBroughtForwardForView("");

            assertEquals(0, editableAccount.getBalanceBroughtForward());
        }

    }

    public class Validation {

        @Test
        public void add_should_show_error_if_any_field_is_invalid() {
            givenCredentialViolatedWith(violation);

            addAccount("", 100);

            verify(mockAccountView).showError(violation);
        }

        @Test
        public void add_should_not_call_add_account_if_any_field_is_invalid() {
            givenCredentialViolatedWith(violation);

            addAccount("", 100);

            verify(mockAccountsApi, never()).addAccount(any(Account.class), any(Runnable.class));
        }

        @Test
        public void edit_should_show_error_if_any_field_is_invalid() {
            givenCredentialViolatedWith(violation);

            editAccount("", 100);

            verify(mockAccountView).showError(violation);
        }

        @Test
        public void edit_should_not_call_add_account_if_any_field_is_invalid() {
            givenCredentialViolatedWith(violation);

            editAccount("", 100);

            verify(mockAccountsApi, never()).addAccount(any(Account.class), any(Runnable.class));
        }

        private void givenCredentialViolatedWith(final Violation... violations) {
            callConsumerArgumentAtIndexWith(1, violations).when(stubValidator).processEachViolation(any(EditableAccount.class), any(Consumer.class));
        }

    }

    private void given_account_id_is(int id) {
        editableAccount.setId(id);
    }

    private void addAccount(String name, int balanceBroughtForward) {
        editableAccount.setName(name);
        editableAccount.setBalanceBroughtForward(balanceBroughtForward);
        editableAccount.add();
    }

    private void editAccount(String name, int balanceBroughtForward) {
        editableAccount.setName(name);
        editableAccount.setBalanceBroughtForward(balanceBroughtForward);
        editableAccount.update();
    }

}
