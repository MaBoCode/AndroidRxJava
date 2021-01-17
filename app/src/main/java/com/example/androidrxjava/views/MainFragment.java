package com.example.androidrxjava.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidrxjava.R;
import com.example.androidrxjava.core.MainFragmentViewModel;
import com.example.androidrxjava.core.MainFragmentViewModel.LoadingStatus;
import com.example.androidrxjava.core.user.User;
import com.example.androidrxjava.databinding.FrgMainBinding;
import com.example.androidrxjava.injects.base.BaseFragment;
import com.example.androidrxjava.utils.ThemeUtils;
import com.example.androidrxjava.views.utils.AnimationUtils;
import com.example.androidrxjava.views.utils.ChartFactory;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.androidannotations.annotations.EFragment;

import java.util.Arrays;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@EFragment
public class MainFragment extends BaseFragment {

    protected FrgMainBinding binding;

    protected MainFragmentViewModel viewModel;

    protected LineChart lineChart;

    protected View.OnClickListener onRefreshClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            refreshChartData();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FrgMainBinding.inflate(inflater, container, false);

        binding.btnRefreshData.setOnClickListener(onRefreshClick);

        viewModel = new ViewModelProvider(requireActivity()).get(MainFragmentViewModel.class);

        initObservers();

        Long userId = 1;
        viewModel.getUser(userId);

        viewModel.getUsers();

        return binding.getRoot();
    }

    public void initObservers() {

        viewModel.userLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.setUser(user);
                startAnimations();
            }
        });

        viewModel.usersLiveData.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                String usersCount = String.format("%s users", users.size());
                binding.usersCountTxtView.setText(usersCount);

                binding.btnRefreshData.setVisibility(View.VISIBLE);

                refreshChartData();
            }
        });

        viewModel.loadingLiveData.observe(this, new Observer<LoadingStatus>() {
            @Override
            public void onChanged(LoadingStatus status) {
                int visiblity = status == LoadingStatus.LOADING ? View.VISIBLE : View.GONE;
                binding.loader.setVisibility(visiblity);
            }
        });
    }

    public void initChart() {
        ChartFactory chartFactory = new ChartFactory();
        lineChart = chartFactory.getLineChart(requireContext());

        ConstraintLayout parentLayout = (ConstraintLayout) binding.getRoot();
        lineChart.setId(parentLayout.getChildCount());

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(0, 0);
        layoutParams.setMargins(0, 0, 0, 32);
        parentLayout.addView(lineChart, parentLayout.getChildCount() - 2, layoutParams);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(parentLayout);

        constraintSet.connect(R.id.usersCountTxtView, ConstraintSet.BOTTOM, lineChart.getId(), ConstraintSet.TOP);

        constraintSet.connect(lineChart.getId(), ConstraintSet.TOP, R.id.usersCountTxtView, ConstraintSet.BOTTOM);
        constraintSet.connect(lineChart.getId(), ConstraintSet.START, R.id.leftGuideline, ConstraintSet.START);
        constraintSet.connect(lineChart.getId(), ConstraintSet.END, R.id.rightGuideline, ConstraintSet.END);
        constraintSet.connect(lineChart.getId(), ConstraintSet.BOTTOM, R.id.btnRefreshData, ConstraintSet.TOP);

        constraintSet.connect(R.id.btnRefreshData, ConstraintSet.TOP, lineChart.getId(), ConstraintSet.BOTTOM);

        constraintSet.applyTo(parentLayout);
    }

    public void refreshChartData() {

        if (lineChart == null) {
            initChart();
        }

        LineData lineData = lineChart.getData();

        if (lineData == null) {
            lineData = new LineData();
            lineData.setDrawValues(false);
            lineChart.setData(lineData);
        }

        ILineDataSet set = lineData.getDataSetByIndex(0);

        if (set == null) {
            set = createSet();
            lineData.addDataSet(set);
        }

        int randomDataSetIndex = (int) (Math.random() * lineData.getDataSetCount());
        ILineDataSet randomSet = lineData.getDataSetByIndex(randomDataSetIndex);
        float value = (float) (Math.random() * 50) + 50f * (randomDataSetIndex + 1);

        lineData.addEntry(new Entry(randomSet.getEntryCount(), value), randomDataSetIndex);
        lineData.notifyDataChanged();

        lineChart.notifyDataSetChanged();

        lineChart.setVisibleXRangeMaximum(6);
        lineChart.moveViewTo(lineData.getEntryCount() - 7, 50f, YAxis.AxisDependency.LEFT);
    }

    public LineDataSet createSet() {
        LineDataSet lineDataSet = new LineDataSet(null, "Dataset 1");
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setColor(ThemeUtils.getThemeColor(requireContext(), R.attr.colorPrimary));
        return lineDataSet;
    }

    public void startAnimations() {
        new AnimationUtils.Builder()
                .setObjects(Arrays.asList(binding.usersCountTxtView, binding.usernameTxtView))
                .setAnimateAlphaIn(true)
                .setTranslationYBegin(-400f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDelay(300)
                .start();
    }

}
