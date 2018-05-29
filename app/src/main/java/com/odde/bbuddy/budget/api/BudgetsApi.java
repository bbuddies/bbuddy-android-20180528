package com.odde.bbuddy.budget.api;

import com.odde.bbuddy.account.api.RawBudgetsApi;
import com.odde.bbuddy.budget.viewmodel.Budget;

public class BudgetsApi {

    private final RawBudgetsApi rawBudgetsApi;

    public BudgetsApi(RawBudgetsApi rawBudgetsApi) {
        this.rawBudgetsApi = rawBudgetsApi;
    }
    public void addBudget(Budget budget, Runnable runnable) {
        runnable.run();
    }
}
