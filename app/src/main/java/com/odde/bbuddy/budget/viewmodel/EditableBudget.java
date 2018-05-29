package com.odde.bbuddy.budget.viewmodel;

import android.util.Log;

import com.odde.bbuddy.budget.api.BudgetsApi;
import com.odde.bbuddy.budget.view.BudgetView;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.robobinding.annotation.PresentationModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PresentationModel
@ActivityScope
public class EditableBudget {

    private BudgetsApi budgetsApi;
    private BudgetView view;

    @Inject
    public EditableBudget(BudgetsApi budgetsApi, BudgetView view) {
        this.budgetsApi = budgetsApi;
        this.view = view;

        for (int i = 1; i <= 12; i++) {
            Budget budget = new Budget();
            budget.setMonth(i + "");
            budget.setAmount(i * 100);
            budgetList.add(budget);
        }
    }

    public EditableBudget(List<Budget> budgets) {
        budgetList = budgets;
    }

    private String month;
    private String amount;
    private String startDay;
    private String endDay;
    private List<Budget> budgetList = new ArrayList<>();
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

    public double getSum(int startDay, int endDay) {
        DateTime startDateTime = new DateTime(startDay);

        DateTime endDateTime = new DateTime(endDay);
        return 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }
}
