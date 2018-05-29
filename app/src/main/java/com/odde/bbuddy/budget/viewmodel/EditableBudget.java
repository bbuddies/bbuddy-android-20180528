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

//    public void getSum() {
//        DateTime startDateTime = new DateTime(startDay);
//        int startMonthOfYear = startDateTime.getMonthOfYear();
//        int startDayOfYear = startDateTime.getDayOfYear();
//
//        DateTime endDateTime = new DateTime(endDay);
//        int endMonthOfYear = endDateTime.getMonthOfYear();
//        int endDayOfYear = endDateTime.getDayOfYear();
//
//        Interval interval = new Interval(startDateTime, endDateTime.plusDays(1));
//
//        int sum = 0;
//        for (int i = 1; i <= 12; i++) {
//            for(int j = 1; j <= 28; j++) {
//                if (i == 2 && j > 28) {
//                    continue;
//                }
//                DateTime dateTime = new DateTime(2018, i, j,
//                        0, 0, 0);
//                boolean contains = interval.contains(dateTime);
//
//                if (contains) {
//                    Log.e("TAG", "i is " + i + " j is " + j);
//
//                    int monthOfYear = dateTime.getMonthOfYear();
//
//                    int average = budgetList.get(i - 1).getAmount() / 28;
//
//                    sum += average;
//                }
//            }
//        }
//
//        Log.e("TAG", "sum is " + sum);
//
//        int startMonth = LocalDate.parse(startDay).getMonthOfYear();
//        int endMonth = LocalDate.parse(endDay).getMonthOfYear();
//
//        Period p = new Period(startDateTime, endDateTime, PeriodType.days());
//        int days = p.getDays(); // 天数
//        //int days1 = Days.daysBetween(startDateTime, endDateTime).getDays();// 相差天数
//
//        boolean contained = interval.contains(new DateTime("2014-08-03"));// 验证某一天是否在该区间内
//
//        DateTime dateTime = new DateTime();
//        int dayOfYear = dateTime.getDayOfYear();
//        for (int i = 1; i <= dayOfYear; i++) {
////            boolean contains = interval.contains(new DateTime(budget.getMonth()));
////            if (contains) {
////                int month = LocalDate.parse(new DateTime(budget.getMonth()).toString()).getMonthOfYear();
////                sum += budget.getAmount() / month;
////            }
//        }
//
//        view.showError(sum + "");
//
//        return;
//    }

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
