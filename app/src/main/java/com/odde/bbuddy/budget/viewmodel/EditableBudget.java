package com.odde.bbuddy.budget.viewmodel;

import com.odde.bbuddy.budget.api.BudgetsApi;

public class EditableBudget {

    private final BudgetsApi budgetsApi;

    public EditableBudget(BudgetsApi budgetsApi) {
        this.budgetsApi = budgetsApi;
    }

    private String month;
    private int amount;
    private int id;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void add() {
        Budget budget = new Budget();
        budget.setMonth(month);
        budget.setAmount(amount);
        budgetsApi.addBudget(budget, new Runnable(){

            @Override
            public void run() {
                amount = 400;
            }
        });
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
