package com.odde.bbuddy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.odde.bbuddy.common.Backend;
import com.odde.bbuddy.common.Consumer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TabAccountsActivity extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showAllAccounts();
    }

    private void showAllAccounts() {
        new Backend(getActivity()).processAllAccounts(new Consumer<JSONArray>() {
            @Override
            public void accept(JSONArray response) {
                setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, allAccounts(response)));
            }
        });
    }

    @NonNull
    private String[] allAccounts(JSONArray allAccounts) {
        final List<String> result = new ArrayList<>();

        for (int i = 0; i < allAccounts.length(); i++) {
            try {
                JSONObject account = allAccounts.getJSONObject(i);
                result.add(account.getString("name") + " " + account.getString("balance"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result.toArray(new String[]{});
    }

}
