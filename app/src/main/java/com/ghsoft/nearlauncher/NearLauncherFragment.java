package com.ghsoft.nearlauncher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        mRecyclerView.setAdapter(new ActivityAdapter(activities));
        // 遍历打印activity标签
        for (ResolveInfo resolveInfo : activities) {
            Log.i(TAG, "activityTag = " + resolveInfo.loadLabel(pm).toString());
        }
        Log.i(TAG, "Found " + activities.size() + " activities.");
    }
    private class ActivityHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        private ResolveInfo mResolveInfo;
        private TextView mNameTextView;
        private ImageView mIconImageView;

        public ActivityHolder(@NonNull View itemView) {
            super(itemView);
            mIconImageView = itemView.findViewById(R.id.iv_icon);
            mNameTextView = itemView.findViewById(R.id.tv_app_name);
            itemView.setOnClickListener(this);
        }

        public void bindActivity(ResolveInfo resolveInfo) {
            mResolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            String appName = mResolveInfo.loadLabel(pm).toString();
            mNameTextView.setText(appName);
            Drawable appIcon = mResolveInfo.loadIcon(pm).getCurrent();
            mIconImageView.setImageDrawable(appIcon);
        }

        @Override
        public void onClick(View v) {
            // 获取能够得到activity包名、类名的activityInfo
            ActivityInfo activityInfo = mResolveInfo.activityInfo;
            Intent intent = new Intent(Intent.ACTION_MAIN)
                    .setClassName(activityInfo.packageName,activityInfo.name)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder>{
        private List<ResolveInfo> mActivities;

        public ActivityAdapter(List<ResolveInfo> activities) {
            mActivities = activities;
        }

        @NonNull
        @Override
        public ActivityHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater
                    .inflate(R.layout.activity_item, viewGroup, false);
            return new ActivityHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ActivityHolder activityHolder, int i) {
            ResolveInfo resolveInfo = mActivities.get(i);
            activityHolder.bindActivity(resolveInfo);
        }

        @Override
        public int getItemCount() {
            return mActivities.size();
        }
    }
}
