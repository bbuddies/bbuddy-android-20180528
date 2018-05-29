package com.odde.bbuddy.budget.api;

import android.util.Pair;

import com.odde.bbuddy.account.api.RawBudgetsApi;
import com.odde.bbuddy.budget.viewmodel.Budget;

import java.util.HashMap;
import java.util.Map;

public class BudgetsApi {

    private final RawBudgetsApi rawBudgetsApi;

    public BudgetsApi(RawBudgetsApi rawBudgetsApi) {
        this.rawBudgetsApi = rawBudgetsApi;
    }
    public void addBudget(Budget budget, Runnable runnable) {
        runnable.run();
    }

    public String getBudgets() {
        Map<Integer, Pair<Integer, Integer>> budgets = new HashMap<>();

        for (int i = 1; i <= 12; i++) {
//            Pair<Integer, Integer> pair = Pair.create(i, i)
        }
        return "";
    }
}
