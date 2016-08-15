package com.tianyeguang.lib;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * 加载更多view接口
 * <p/>
 * Created by wangyeming on 2016/8/15.
 */
public abstract class LoadMoreView extends FrameLayout {

    public LoadMoreView(Context context) {
        super(context);
    }

    public abstract void showLoading();

    public abstract void showRetry();

    public abstract void showEnd();

    public abstract boolean isLoadMoreEnable();

    public abstract void setOnRetryClickListener(OnRetryClickListener listener);
}
