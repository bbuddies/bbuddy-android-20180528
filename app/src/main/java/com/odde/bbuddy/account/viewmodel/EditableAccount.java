package com.odde.bbuddy.account.viewmodel;

import com.odde.bbuddy.account.api.AccountsApi;
import com.odde.bbuddy.account.view.AccountView;
import com.odde.bbuddy.account.view.AccountsNavigation;
import com.odde.bbuddy.common.functional.Consumer;
import com.odde.bbuddy.common.functional.ValueCaptor;
import com.odde.bbuddy.common.validation.Validator;
import com.odde.bbuddy.common.validation.Violation;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.hibernate.validator.constraints.NotBlank;
import org.robobinding.annotation.PresentationModel;

import javax.inject.Inject;
import javax.validation.constraints.Size;

@PresentationModel
@ActivityScope
public class EditableAccount {

    private final AccountsApi accountsApi;
    private final AccountsNavigation accountsNavigation;
    private final Validator validator;
    private final AccountView accountView;

    @NotBlank @Size(max = 50)
    private String name;
    private int balanceBroughtForward;
    private int id;

    @Inject
    public EditableAccount(AccountsApi accountsApi, AccountsNavigation accountsNavigation, Validator validator, AccountView accountView) {
        this.accountsApi = accountsApi;
        this.accountsNavigation = accountsNavigation;
        this.validator = validator;
        this.accountView = accountView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalanceBroughtForwardForView() {
        return String.valueOf(balanceBroughtForward);
    }

    public void setBalanceBroughtForwardForView(String balanceBroughtForward) {
        try {
            this.balanceBroughtForward = Integer.parseInt(balanceBroughtForward);
        } catch (NumberFormatException e) {

        }
    }

    public void add() {
        if (isValid())
            addAccount();
    }

    private void addAccount() {
        accountsApi.addAccount(new Account(name, balanceBroughtForward, 0), new Runnable() {
            @Override
            public void run() {
                accountsNavigation.navigate();
            }
        });
    }

    private Boolean isValid() {
        final ValueCaptor<Boolean> isValid = new ValueCaptor<>(true);
        validator.processEachViolation(this, new Consumer<Violation>() {
            @Override
            public void accept(Violation violation) {
                accountView.showError(violation);
                isValid.capture(false);
            }
        });
        return isValid.value();
    }

    public void setBalanceBroughtForward(int balanceBroughtForward) {
        this.balanceBroughtForward = balanceBroughtForward;
    }

    public int getBalanceBroughtForward() {
        return balanceBroughtForward;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void update() {
        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setBalanceBroughtForward(balanceBroughtForward);
        accountsApi.editAccount(account, new Runnable() {
            @Override
            public void run() {
                accountsNavigation.navigate();
            }
        });
    }

    public void delete() {
        Account account = new Account();
        account.setId(id);
        accountsApi.deleteAccount(account, new Runnable() {
            @Override
            public void run() {
                accountsNavigation.navigate();
            }
        });
    }

}
