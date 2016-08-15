package com.tianyeguang.loadmorerecyclerviewadapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.tianyeguang.lib.LoadMoreView;
import com.tianyeguang.lib.OnRetryClickListener;

/**
 * 自定义样式加载更多
 *
 * Created by wangyeming on 16-7-30.
 */
public class CommonLoadMoreView extends LoadMoreView implements View.OnClickListener {

    private ViewFlipper vPages;
    private ProgressBar vLoading;
    private TextView vRetry;
    private TextView vEnd;

    private OnRetryClickListener mListener;

    public CommonLoadMoreView(Context context) {
        super(context);
        init();
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.default_load_more, this, true);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        setLayoutParams(layoutParams);
        vPages = (ViewFlipper) findViewById(R.id.default_loadmore_flipper);
        vLoading = (ProgressBar) findViewById(R.id.default_loadmore_loading);
        vRetry = (TextView) findViewById(R.id.default_loadmore_retry);
        vEnd = (TextView) findViewById(R.id.default_loadmore_end);
        vRetry.setOnClickListener(this);

        setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.default_loadmore_retry) {
            if(mListener != null) {
                showLoading();
                mListener.OnRetryClick();
            }
        }
    }

    public void showLoading() {
        vPages.setDisplayedChild(0);
    }

    public void showRetry() {
        vPages.setDisplayedChild(1);
    }

    public void showEnd() {
        vPages.setDisplayedChild(2);
    }

    public boolean isLoadMoreEnable() {
        return vPages.getDisplayedChild() == 0;
    }

    @Override
    public void setOnRetryClickListener(OnRetryClickListener listener) {
        mListener = listener;

    }

}
