package com.sourcey.Hackaroad.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.FileUtils;

import java.util.ArrayList;

/**
 * Created by BSM on 2017-10-29.
 */

public class SimpleFragment extends Fragment {

    protected Typeface mTfRegular;
    protected Typeface mTfLight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
    }

    public LineData generateLineData(ArrayList<Integer> y) {

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < y.size(); i++) {
            entries.add(new Entry(i, y.get(i)));
        }

        LineDataSet d = new LineDataSet(entries, "");
        d.setLineWidth(2.5f);
        d.setCircleRadius(4.5f);
        d.setHighLightColor(Color.rgb(244, 117, 117));
        d.setDrawValues(false);

        LineData cd = new LineData(d);
        return cd;
    }

    public BarData generateBarData(ArrayList<Double> y) {

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < y.size(); i++) {
            entries.add(new BarEntry(i, y.get(i).intValue()));
        }

        BarDataSet d = new BarDataSet(entries, "");
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        return cd;
    }

    public PieData generatePieData(ArrayList<Integer> values, String[] marker) {

        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < marker.length; i++) {
            entries.add(new PieEntry(values.get(i), marker[i]));
        }

        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(10f);
        d.setColors(ColorTemplate.COLORFUL_COLORS);

        d.setValueLinePart1OffsetPercentage(80.f);
        d.setValueLinePart1Length(0.2f);
        d.setValueLinePart2Length(0.4f);
        d.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData cd = new PieData(d);
        return cd;
    }


    public BubbleData generateBubbleata(ArrayList<Integer> values) {

        ArrayList<BubbleEntry> entries = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            float val = values.get(i);
            float size = values.get(i);

            entries.add(new BubbleEntry(i, val, size));
        }

        BubbleDataSet d = new BubbleDataSet(entries, "Bubble DataSet");

        d.setColors(ColorTemplate.COLORFUL_COLORS);
        d.setValueTextSize(10f);
        d.setValueTextColor(Color.WHITE);
        d.setHighlightCircleWidth(1.5f);
        d.setDrawValues(true);

        BubbleData bd = new BubbleData(d);
        return bd;
    }

    public CombinedData generateCombinedData(LineData lineData, BarData barData) {

        CombinedData d = new CombinedData();

        d.setData(lineData);
        d.setData(barData);
        d.setValueTypeface(mTfLight);

        return d;
    }

}