package com.odde.bbuddy.account.viewmodel;

import com.odde.bbuddy.account.api.AccountsApi;
import com.odde.bbuddy.account.view.AccountsNavigation;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.PresentationModel;

import javax.inject.Inject;

@PresentationModel
@ActivityScope
public class EditableAccount {

    private final AccountsApi accountsApi;
    private final AccountsNavigation accountsNavigation;

    private String name;
    private int balanceBroughtForward;
    private int id;

    @Inject
    public EditableAccount(AccountsApi accountsApi, AccountsNavigation accountsNavigation) {
        this.accountsApi = accountsApi;
        this.accountsNavigation = accountsNavigation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalanceBroughtForwardForView() {
        return String.valueOf(balanceBroughtForward);
    }

    public void setBalanceBroughtForwardForView(String balanceBroughtForward) {
        try {
            this.balanceBroughtForward = Integer.parseInt(balanceBroughtForward);
        } catch (NumberFormatException e) {

        }
    }

    public void add() {
        Account account = new Account();
        account.setName(name);
        account.setBalanceBroughtForward(balanceBroughtForward);
        accountsApi.addAccount(account, new Runnable() {
            @Override
            public void run() {
                accountsNavigation.navigate();
            }
        });
    }

    public void setBalanceBroughtForward(int balanceBroughtForward) {
        this.balanceBroughtForward = balanceBroughtForward;
    }

    public int getBalanceBroughtForward() {
        return balanceBroughtForward;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void update() {
        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setBalanceBroughtForward(balanceBroughtForward);
        accountsApi.editAccount(account, new Runnable() {
            @Override
            public void run() {
                accountsNavigation.navigate();
            }
        });
    }

    public void delete() {
        Account account = new Account();
        account.setId(id);
        accountsApi.deleteAccount(account, new Runnable() {
            @Override
            public void run() {
                accountsNavigation.navigate();
            }
        });
    }

}
