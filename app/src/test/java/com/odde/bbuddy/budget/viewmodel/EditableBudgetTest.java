package com.odde.bbuddy.budget.viewmodel;

import com.odde.bbuddy.budget.api.BudgetsApi;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EditableBudgetTest {

    @Test
    public void addBudget() {
        BudgetsApi mockApi = mock(BudgetsApi.class);
        EditableBudget editableBudget = new EditableBudget(mockApi);
        givenAddBudgetSuccess(mockApi);

        editableBudget.setMonth("2018-06");
        editableBudget.setAmount(500);
        editableBudget.add();

        ArgumentCaptor<Budget> captor = ArgumentCaptor.forClass(Budget.class);
        verify(mockApi).addBudget(captor.capture(),any(Runnable.class));
        assertThat(captor.getValue().getMonth()).isEqualTo("2018-06");
        assertThat(captor.getValue().getAmount()).isEqualTo(500);

        assertThat(editableBudget.getAmount()).isEqualTo(390);
    }

    private void givenAddBudgetSuccess(BudgetsApi mockApi) {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Runnable afterSuccess = invocation.getArgument(1);
                afterSuccess.run();
                return null;
            }
        }).when(mockApi).addBudget(any(Budget.class), any(Runnable.class));
    }

}