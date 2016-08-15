package com.tianyeguang.loadmorerecyclerviewadapter;

import android.content.Context;
import com.tianyeguang.lib.BaseViewHolder;
import com.tianyeguang.lib.LoadMoreAdapter;

/**
 * 自定义的adapter
 *
 * Created by wangyeming on 2016/8/15.
 */
public class CustomAdapter extends LoadMoreAdapter<Integer> {

    public CustomAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }


    @Override
    protected void convert(BaseViewHolder holder, Integer item, int indexOfData) {
        holder.setText(android.R.id.text1, String.valueOf(item));
    }
}
