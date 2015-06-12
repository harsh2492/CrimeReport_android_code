package com.google.android.gms.location.sample.locationaddress;

import android.support.v4.app.Fragment;


public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

}
