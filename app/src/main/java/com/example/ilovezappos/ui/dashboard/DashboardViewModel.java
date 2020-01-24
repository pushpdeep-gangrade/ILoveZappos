package com.example.ilovezappos.ui.dashboard;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ilovezappos.Transaction;
import com.example.ilovezappos.TransactionHistoryAPI;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardViewModel extends ViewModel {
    final List<Entry> entries = new ArrayList<Entry>();
    private MutableLiveData<String> mText;
    LineChart chart;
    public DashboardViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.bitstamp.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TransactionHistoryAPI transactionHistoryAPI = retrofit.create(TransactionHistoryAPI.class);

        Call<List<Transaction>> call = transactionHistoryAPI.getTransaction();


        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                List<Transaction> transactions = response.body();
                int count = 0;
                for (Transaction t : transactions) {
                    entries.add(new Entry(count, t.getPrice()));
                    count++;
                    Log.d("demo", "date" + t.getDate() + "Price" + t.getPrice());
                }
                setGraph();
            }
            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                t.getMessage();

            }
        });
       // mText = new MutableLiveData<>();
       // mText.setValue("This is dashboard fragment");
    }


    public LiveData<String> getText() {
        return mText;
    }
    public void setGraph() {
        LineDataSet dataSet = new LineDataSet(entries, "Test");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }
}