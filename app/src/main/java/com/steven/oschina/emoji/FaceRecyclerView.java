package com.steven.oschina.emoji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.oschina.client.recyclerview.adapter.OnItemClickListener;
import com.steven.oschina.R;
import com.steven.oschina.utils.TDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：7/19/2018-10:53 AM
 *
 * @author yanzhiwen
 */
@SuppressLint("ViewConstructor")
public class FaceRecyclerView extends RecyclerView implements OnItemClickListener {
    private FaceAdapter mAdapter;
    private final List<Emojicon> mData = new ArrayList<>();
    private OnFaceClickListener mListener;


    public FaceRecyclerView(Context context, OnFaceClickListener listener) {
        super(context);
        this.mListener = listener;
        // 自动计算显示的列数
        int autoCount = ( int ) (TDevice.getScreenWidth() / TDevice.dipToPx(getResources(), 48));
        setLayoutManager(new GridLayoutManager(context, autoCount));
        mAdapter = new FaceAdapter(getContext(), mData, R.layout.lay_face_icon);
        setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }

    public void setData(List<Emojicon> icons) {
        mData.addAll(icons);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        OnFaceClickListener listener = mListener;
        if (listener != null && position != -1) {
            Emojicon emojicon = mData.get(position);
            listener.onFaceClick(emojicon);
        }
    }

    public interface OnFaceClickListener {
        void onFaceClick(Emojicon v);
    }
}

