package com.odde.bbuddy.account.view;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.odde.bbuddy.R;
import com.odde.bbuddy.budget.view.BudgetView;
import com.odde.bbuddy.budget.viewmodel.EditableBudget;
import com.odde.bbuddy.common.validation.Violation;

import org.robobinding.ViewBinder;

import javax.inject.Inject;

import static com.odde.bbuddy.di.component.ActivityComponentFactory.createActivityComponentBy;

public class AddBudgetActivity extends AppCompatActivity implements BudgetView{
    @Inject
    EditableBudget budget;

    @Inject
    ViewBinder viewBinder;
    private TextInputLayout name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createActivityComponentBy(this).inject(this);

        setContentView(viewBinder.inflateAndBind(R.layout.activity_add_budget, budget));

        name = (TextInputLayout) findViewById(R.id.nameError);
    }

    @Override
    public void showError(String violation) {
        name.setError(violation);
        finish();
    }
}
