package com.example.androidrxjava.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidrxjava.R;
import com.example.androidrxjava.core.MainFragmentViewModel;
import com.example.androidrxjava.core.MainFragmentViewModel.LoadingStatus;
import com.example.androidrxjava.core.user.User;
import com.example.androidrxjava.databinding.FrgMainBinding;
import com.example.androidrxjava.injects.base.BaseFragment;
import com.example.androidrxjava.utils.ThemeUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.androidannotations.annotations.EFragment;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
@EFragment
public class MainFragment extends BaseFragment {

    protected FrgMainBinding binding;

    protected MainFragmentViewModel viewModel;

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

        Long userId = new Long(1);
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

                setupLineChart();
                refreshChartData();

                binding.btnRefreshData.setVisibility(View.VISIBLE);
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

    public void setupLineChart() {
        LineChart lineChart = binding.lineChart;
        lineChart.setVisibility(View.VISIBLE);
        lineChart.setTouchEnabled(false);
        lineChart.setNoDataTextColor(ThemeUtils.getThemeColor(requireContext(), R.attr.colorOnSurface));
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineWidth(2f);
        xAxis.setAxisLineColor(ThemeUtils.getThemeColor(requireContext(), R.attr.colorOnSurface));
        xAxis.setTextColor(ThemeUtils.getThemeColor(requireContext(), R.attr.colorOnSurface));

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setAxisMaximum(0);
        yAxisRight.setAxisMinimum(0);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setAxisMaximum(100);
        yAxisLeft.setAxisMinimum(0);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setAxisLineWidth(2f);
        yAxisLeft.setAxisLineColor(ThemeUtils.getThemeColor(requireContext(), R.attr.colorOnSurface));
        yAxisLeft.setTextColor(ThemeUtils.getThemeColor(requireContext(), R.attr.colorOnSurface));
    }

    public void refreshChartData() {
        LineChart lineChart = binding.lineChart;
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
        ObjectAnimator usernameTxtViewAnimator = ObjectAnimator.ofFloat(binding.usernameTxtView, "alpha", 0f, 1f);
        ObjectAnimator userCountTxtViewAnimator = ObjectAnimator.ofFloat(binding.usersCountTxtView, "alpha", 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(usernameTxtViewAnimator, userCountTxtViewAnimator);
        animatorSet.setDuration(300L);
        animatorSet.setStartDelay(100L);

        animatorSet.start();
    }

}
