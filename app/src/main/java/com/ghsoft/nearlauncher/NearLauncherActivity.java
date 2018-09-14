package com.ghsoft.nearlauncher;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NearLauncherActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return NearLauncherFragment.newInstance();
    }
}
