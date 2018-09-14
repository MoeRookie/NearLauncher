package com.ghsoft.nearlauncher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NearLauncherFragment extends Fragment{

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
        return view;
    }
}
