package com.ghsoft.nearlauncher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class NearLauncherFragment extends Fragment{
    private static final String TAG = "NearLauncherFragment";

    private RecyclerView mRecyclerView;

    public static Fragment newInstance() {
        return new NearLauncherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater
            , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_near_launcher, container, false);
        mRecyclerView = view.findViewById(R.id.fragment_near_launcher_recycler_view);
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        // 打印设备中可启动的应用个数
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
        Log.i(TAG, "Found " + activities.size() + " activities.");
    }
}
