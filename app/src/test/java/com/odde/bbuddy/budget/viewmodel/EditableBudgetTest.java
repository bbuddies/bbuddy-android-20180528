package com.odde.bbuddy.budget.viewmodel;

import com.odde.bbuddy.budget.api.BudgetsApi;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EditableBudgetTest {

    private List<Budget> budgets;

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

    @Test
    public void get_whole_month() {
        givenBudgets();

        int calculateBudget = calculateBudget("2018-3-1", "2018-3-31");

        assertThat(calculateBudget).isEqualTo(1000);
    }

    private int calculateBudget(String startDay, String endDay) {
        return 0;
    }

    private void givenBudgets() {
        budgets = new ArrayList<>();
        budgets.add(new Budget(){{
            setMonth("2018-3");
            setAmount(1000);
        }});
    }

    @Test
    public void get_not_period_month() {

    }

    @Test
    public void get_budget_exception() {

    }
}