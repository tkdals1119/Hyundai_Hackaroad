package com.sourcey.Hackaroad.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourcey.Hackaroad.R;

/**
 * Created by Userinsight on 2017-10-26.
 */

public class Frag_StatisticsActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {git 
        View view = inflater.inflate(R.layout.activity_frag_statistics, container, false);
        return view;
    }
}