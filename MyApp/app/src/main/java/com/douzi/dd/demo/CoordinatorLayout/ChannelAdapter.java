package com.douzi.dd.demo.CoordinatorLayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.douzi.dd.R;
import com.douzi.dd.utils.DeviceUtil;

import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mList;

    public ChannelAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mInflater.inflate(R.layout.listitem_search_result_channel,
                parent, false);
        float displayCount;
        final float width = DeviceUtil.getScreenWidthInPixels();
        final float itemWidth = mContext.getResources().getDimension(R.dimen.search_result_channel_item_width);
        displayCount = width / itemWidth;
        displayCount = Math.round(displayCount) - 0.5f;
        convertView.getLayoutParams().width = (int) (DeviceUtil.getScreenWidthInPixels() / displayCount);
        return new ChannelViewHolder(convertView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(ChannelViewHolder holder, final int position) {
        final String channel = mList.get(position);
        holder.textView.setText(channel);
    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {

        final TextView textView;

        public ChannelViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_search_result_channel_item);
        }
    }
}
