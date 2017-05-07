package com.odde.bbuddy.account.viewmodel;

import org.junit.Test;
import org.robobinding.itempresentationmodel.ItemContext;

import static com.odde.bbuddy.account.builder.AccountBuilder.anAccount;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class PresentableAccountTest {

    @Test
    public void display_of_account() {
        PresentableAccount presentableAccount = new PresentableAccount();
        ItemContext stubItemContext = mock(ItemContext.class);

        presentableAccount.updateData(anAccount().name("name").balanceBroughtForward(100).build(), stubItemContext);

        assertEquals("name 100", presentableAccount.getDisplayOfAccount());
    }

}