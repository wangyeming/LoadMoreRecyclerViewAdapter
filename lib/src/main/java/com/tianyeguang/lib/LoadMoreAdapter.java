package com.tianyeguang.lib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持load more的Adapter
 *
 * 支持setHeader()
 * 支持setEmpty()
 * 支持header和empty同时显示
 * <p>
 * Created by wangyeming on 16-8-3.
 */
public abstract class LoadMoreAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_BASE = 1;
    private static final int TYPE_EMPTY = 2;
    private static final int TYPE_LOADMORE = 3;

    protected Context mContext;
    protected int mLayoutResId = -1;
    protected View vHeader;
    protected View vEmpty;
    protected LoadMoreView vLoadMore;
    protected boolean mLoadMoreEnable;                    //标识是否启用加载更多
    protected boolean mIsHeaderShowInEmpty;               //标识header是否在empty时显示
    protected OnLoadMoreListener mOnLoadMoreListener;     //加载更多执行的回调
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;
    protected List<T> mData = new ArrayList<>();

    public LoadMoreAdapter(Context context, int layoutResId) {
        mContext = context;
        mLayoutResId = layoutResId;
    }

    public void setLoadMoreView(LoadMoreView vLoadMore) {
        this.vLoadMore = vLoadMore;
    }

    public void setData(List<T> data) {
        mData = data;
    }

    public T getItem(int indexOfData) {
        return mData.get(indexOfData);
    }

    protected abstract void convert(BaseViewHolder holder, T item, int indexOfData);

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = null;
        switch (viewType) {
            case TYPE_HEADER: {
                holder = new BaseViewHolder(vHeader);
                break;
            }
            case TYPE_BASE:
                View view = LayoutInflater.from(mContext).inflate(mLayoutResId, parent, false);
                holder = new BaseViewHolder(view);
                initItemClick(holder);
                break;
            case TYPE_EMPTY:
                holder = new BaseViewHolder(vEmpty);
                break;
            case TYPE_LOADMORE:
                holder = new BaseViewHolder(vLoadMore);
                break;
        }
        return holder;
    }

    private void initItemClick(final BaseViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnRecyclerViewItemClickListener != null) {
                    mOnRecyclerViewItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition() - getHeaderCount());
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_BASE:
                convert(holder, mData.get(position - getHeaderCount()), position - getHeaderCount());
                break;
            case TYPE_EMPTY:
                break;
            case TYPE_LOADMORE:
                if(mOnLoadMoreListener != null && vLoadMore.isLoadMoreEnable()) {
                    mOnLoadMoreListener.onLoadMore();
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.isEmpty()) {
            int headerCount = getHeaderCount();
            int emptyCount = isEmptyEnable() ? 1 : 0;
            return headerCount + emptyCount;
        } else {
            int headerCount = getHeaderCount();
            int loadMoreCount = mLoadMoreEnable ? 1 : 0;
            return headerCount + mData.size() + loadMoreCount;
        }
    }

    protected int getHeaderCount() {
        if (mData == null || mData.isEmpty()) {
            if (isHeaderEnable() && mIsHeaderShowInEmpty) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return isHeaderEnable() ? 1 : 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData == null || mData.isEmpty()) {
            if (position < getHeaderCount()) {
                return TYPE_HEADER;
            } else {
                return TYPE_EMPTY;
            }
        } else {
            if (position < getHeaderCount()) {
                return TYPE_HEADER;
            } else {
                int index = position - getHeaderCount();
                if (index < mData.size()) {
                    return TYPE_BASE;
                } else {
                    return TYPE_LOADMORE;
                }
            }
        }
    }

    private boolean isHeaderEnable() {
        return vHeader != null;
    }

    private boolean isEmptyEnable() {
        return vEmpty != null;
    }

    public void setHeaderView(View vHeader) {
        this.vHeader = vHeader;
    }

    public View getHeaderView() {
        return vHeader;
    }

    public void setEmptyView(View vEmpty) {
        this.vEmpty = vEmpty;
    }

    public View getEmptyView() {
        return vEmpty;
    }

    /**
     * 设置是否启用加载更多
     */
    public void setLoadMoreEnable(boolean loadMoreEnable) {
        mLoadMoreEnable = loadMoreEnable;
    }

    /**
     * 设置显示empty时，是否显示header
     */
    public void setHeaderShowInEmpty(boolean headerShowInEmpty) {
        mIsHeaderShowInEmpty = headerShowInEmpty;
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    /**
     * 设置加载更多时触发的操作
     */
    public void setOnLoadMoreListener(final OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
        vLoadMore.setOnRetryClickListener(new OnRetryClickListener() {
            @Override
            public void OnRetryClick() {
                if(onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
            }
        });
    }

    public void showLoadMoreLoading() {
        vLoadMore.showLoading();
    }

    public void showLoadMoreRetry() {
        vLoadMore.showRetry();
    }

    public void showLoadMoreEnd() {
        vLoadMore.showEnd();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, int indexOfData);
    }

    public interface OnLoadMoreListener {

        void onLoadMore();
    }
}
