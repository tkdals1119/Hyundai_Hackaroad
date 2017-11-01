package com.sourcey.Hackaroad.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sourcey.Hackaroad.R;
import com.sourcey.Hackaroad.adapter.ChartDataAdapter;
import com.sourcey.Hackaroad.model.ChartItem.BarChartItem;
import com.sourcey.Hackaroad.model.ChartItem.ChartItem;
import com.sourcey.Hackaroad.model.ChartItem.PieChartItem;
import com.sourcey.Hackaroad.utils.SimpleFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by BSM on 2017-10-26.
 */

public class Frag_StatisticsActivity extends SimpleFragment {

    @BindView(R.id.lvStats) ListView lvStats;

    private ArrayList<ChartItem> chartItems;
    ArrayList<Integer> myhabbit = new ArrayList<>();
    ArrayList<Double> myhabbit2 = new ArrayList<>();

    String[] marker = {"속도위반","정지선침범","보행자미준수"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_frag_statistics, container, false);
        ButterKnife.bind(this, view);


        initModel();
        setView();
        return view;
    }

    private void initModel() {
        myhabbit.clear();

        for(int i=0; i<3; i++)
        {
            myhabbit.add(i, 0);
        }

        for(int j=0; j<11; j++)
        {
            myhabbit2.add(j, 0.0);
            myhabbit2.set(j, j*1.5);
        }

        myhabbit.set(0, 10);
        myhabbit.set(1, 20);
        myhabbit.set(2, 30);
    }

    private void setView() {
        chartItems = new ArrayList<>();
        chartItems.add(new PieChartItem(generatePieData(myhabbit, marker), getActivity()));
        chartItems.add(new BarChartItem(generateBarData(myhabbit2), getActivity()));

        ChartDataAdapter cda = new ChartDataAdapter(getActivity(), chartItems);
        lvStats.setAdapter(cda);
    }

}