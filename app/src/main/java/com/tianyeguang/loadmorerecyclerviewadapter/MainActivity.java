package com.tianyeguang.loadmorerecyclerviewadapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.tianyeguang.lib.LoadMoreAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wangyeming on 16-8-15.
 */
public class MainActivity extends AppCompatActivity implements
        LoadMoreAdapter.OnLoadMoreListener, LoadMoreAdapter.OnRecyclerViewItemClickListener {

    protected List<Integer> mData = new ArrayList<>();
    private CustomAdapter mAdapter;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        RecyclerView vList = (RecyclerView) findViewById(R.id.loadmore_rv);
        vList.setLayoutManager(new LinearLayoutManager(this));

        View vHeader = LayoutInflater.from(this).inflate(android.R.layout.simple_list_item_1, vList, false);
        TextView vHeaderContent = (TextView) vHeader.findViewById(android.R.id.text1);
        vHeaderContent.setText("I'm a header");

        mAdapter = new CustomAdapter(this);
        mAdapter.setLoadMoreView(new CommonLoadMoreView(this));
        mAdapter.setData(mData);
        mAdapter.setLoadMoreEnable(true);
        mAdapter.setHeaderView(vHeader);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.setHeaderShowInEmpty(true);
        mAdapter.setOnRecyclerViewItemClickListener(this);

        vList.setAdapter(mAdapter);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int number = random.nextInt(10);
                if (number == 7) {  //模拟没有更多的情况
                    mAdapter.showLoadMoreEnd();
                } else {
                    if (number % 2 == 0) {
                        int lastValue = mData.get(mData.size() - 1);
                        for (int i = lastValue; i < lastValue + 30; i++) {
                            mData.add(i);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {        //模拟网络失败的情况
                        mAdapter.showLoadMoreRetry();
                    }
                }

            }

        }, 3000);

    }

    private void initData() {
        mData.clear();
        for (int i = 0; i < 30; i++) {
            mData.add(i);
        }
    }

    @Override
    public void onItemClick(View view, int indexOfData) {
        Toast.makeText(this, "点击" + mData.get(indexOfData), Toast.LENGTH_SHORT).show();
    }
}
