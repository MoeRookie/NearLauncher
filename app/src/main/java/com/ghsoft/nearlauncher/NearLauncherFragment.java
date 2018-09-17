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
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
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
        // 以按activity标签首字母排序的方式对resolveInfo进行排序,不会生成新的resolveInfo列表
        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo o1, ResolveInfo o2) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(
                        // 获取到activity的标签名,不分大小写顺序的比较
                        o1.loadLabel(pm).toString(),
                        o2.loadLabel(pm).toString()
                ) ;
            }
        });
        Log.i(TAG, "Found " + activities.size() + " activities.");
    }
    private class ActivityHolder extends RecyclerView.ViewHolder{

        private TextView mNameTextView;
        private ResolveInfo mResolveInfo;

        public ActivityHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView;
        }

        public void bindActivity(ResolveInfo resolveInfo) {
            mResolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            String appName = resolveInfo.loadLabel(pm).toString();
            mNameTextView.setText(appName);
        }
    }
}
