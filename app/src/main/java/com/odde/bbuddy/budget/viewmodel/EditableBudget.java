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


    public int getSum(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            return 0;
        }

        String startString = start.getYear() + "-" + start.getMonthOfYear();
        String endString = end.getYear() + "-" + end.getMonthOfYear();

        List<Budget> budgets = new ArrayList<>();

        for (Budget budget : budgetList) {
            String month = budget.getMonth();
            String[] yearAndMonth = month.split("-");
            if(yearAndMonth.length != 2) {
                break;
            }
            int y = Integer.parseInt(yearAndMonth[0]);
            int m = Integer.parseInt(yearAndMonth[1]);
            if (!isBefore(y, m, start.getYear(), start.getMonthOfYear())
                    && !isBefore(end.getYear(), end.getMonthOfYear(), y, m)) {
                budgets.add(budget);
            }
        }

        int sum = 0;
        for (Budget budget : budgets) {
            int amount = budget.getAmount();
            sum += amount;
            if (budget.getMonth().equals(startString)) {
                sum -= beforeAmount(start, amount);
            }
            if (budget.getMonth().equals(endString)) {
                sum -= lastAmount(end, amount);
            }
        }

        return sum;
    }

    private boolean isBefore(int y1, int m1, int y2, int m2) {
        if (y1 < y2) {
            return true;
        }

        if (m1 < m2) {
            return true;
        }

        return false;
    }

    private int beforeAmount(LocalDate start, int amount) {
        int between = start.getDayOfMonth() - 1;
        int daysOfMonth = start.getDayOfMonth();
        return between * amount / daysOfMonth;
    }

    private int lastAmount(LocalDate end, int amount) {
        int daysOfMonth = end.getDayOfMonth();
        int between = daysOfMonth - end.getDayOfMonth();
        return between * amount / daysOfMonth;
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
