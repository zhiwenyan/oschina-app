package com.steven.oschina.ui.synthetical.sub;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.steven.oschina.R;
import com.steven.oschina.bean.sub.SubBean;
import com.steven.oschina.widget.AutoScrollView;

/**
 * Description:
 * Data：7/26/2018-11:16 AM
 *
 * @author yanzhiwen
 */
public class QuestionDetailFragment extends DetailFragment {


    public static QuestionDetailFragment newInstance(SubBean subBean) {
        QuestionDetailFragment fragment = new QuestionDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", subBean);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    protected View getHeaderView() {
        return new QuestionDetailHeaderView(mContext);
    }

    private static class QuestionDetailHeaderView extends AutoScrollView {
        public QuestionDetailHeaderView(Context context) {
            super(context);
            LayoutInflater.from(context).inflate(R.layout.layout_question_detail_header, this, true);
        }
    }
}
