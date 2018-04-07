package com.odde.bbuddy;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.odde.bbuddy.account.view.AccountsActivity;
import com.odde.bbuddy.account.view.AddAccountActivity;
import com.odde.bbuddy.dashboard.view.DashboardActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setNavigationItemSelectedListener();
        navigateToFirstTab();
    }

    private void navigateToFirstTab() {
        navigationView().getMenu().getItem(0).setChecked(true);
        setFragment(new DashboardActivity());
    }

    private void setNavigationItemSelectedListener() {
        navigationView().setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_dashboard:
                        setFragment(new DashboardActivity());
                        return true;
                    case R.id.menu_accounts:
                        setFragment(new AccountsActivity());
                        setAdd(AddAccountActivity.class);
                        return true;
                }
                return false;
            }

        });
    }

    private BottomNavigationView navigationView() {
        return (BottomNavigationView) findViewById(R.id.navigation);
    }

    private void setAdd(final Class activityClass) {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);

        View mCustomView = View.inflate(this, R.layout.action_bar, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

        TextView add = (TextView) mCustomView.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), activityClass));
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
