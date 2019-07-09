package com.zju.myrecycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mrv;
    private RecyclerView.LayoutManager mlm;
    private RecyclerView.Adapter mad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitObj();
        InitView();
        mrv.addItemDecoration(new DividerItemDecoration(this, LinearLayout.HORIZONTAL));
    }

    private void InitView()
    {
        mrv = findViewById(R.id.rv_list);
        mrv.setLayoutManager(mlm);
        mrv.setAdapter(mad);
    }

    private void InitObj()
    {
        mlm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mad = new MyAdapter(InitData());
    }

    private ArrayList<String> InitData()
    {
        ArrayList<String> data = new ArrayList<>();

        for(int i = 0; i < 50; ++ i)
        {
            data.add(i +".item" + i);
        }

        return data;
    }
}
