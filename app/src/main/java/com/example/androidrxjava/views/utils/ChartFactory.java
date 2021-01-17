package com.example.androidrxjava.views.utils;

import android.content.Context;
import android.view.View;

import com.example.androidrxjava.R;
import com.example.androidrxjava.utils.ThemeUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

public class ChartFactory {


    public LineChart getLineChart(Context context) {
        int colorOnSurface = ThemeUtils.getThemeColor(context, R.attr.colorOnSurface);

        LineChart lineChart = new LineChart(context);
        lineChart.setVisibility(View.VISIBLE);
        lineChart.setTouchEnabled(false);
        lineChart.setNoDataTextColor(colorOnSurface);
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineWidth(2f);
        xAxis.setAxisLineColor(colorOnSurface);
        xAxis.setTextColor(colorOnSurface);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setAxisMaximum(0);
        yAxisRight.setAxisMinimum(0);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setAxisMaximum(100);
        yAxisLeft.setAxisMinimum(0);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setAxisLineWidth(2f);
        yAxisLeft.setAxisLineColor(colorOnSurface);
        yAxisLeft.setTextColor(colorOnSurface);

        return lineChart;
    }



}
