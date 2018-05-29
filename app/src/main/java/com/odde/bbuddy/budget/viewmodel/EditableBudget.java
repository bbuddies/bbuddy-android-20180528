package com.odde.bbuddy.budget.viewmodel;

import android.app.Activity;

import com.odde.bbuddy.budget.api.BudgetsApi;
import com.odde.bbuddy.budget.view.BudgetView;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.PresentationModel;

import javax.inject.Inject;

@PresentationModel
@ActivityScope
public class EditableBudget {

    private final BudgetsApi budgetsApi;
    private final BudgetView view;

    @Inject
    public EditableBudget(BudgetsApi budgetsApi, BudgetView view) {
        this.budgetsApi = budgetsApi;
        this.view = view;
    }

    private String month;
    private String amount;
    private int id;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void add() {
        Budget budget = new Budget();
        budget.setMonth(month);
        budget.setAmount(Integer.parseInt(amount));
        budgetsApi.addBudget(budget, new Runnable(){

            @Override
            public void run() {
                view.showError("add budget finish!");
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
