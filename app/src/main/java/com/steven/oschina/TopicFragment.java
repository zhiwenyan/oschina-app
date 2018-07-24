package com.steven.oschina;


import android.app.Fragment;
import android.os.Bundle;

import com.steven.oschina.base.BaseRecyclerFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopicFragment extends BaseRecyclerFragment {

    public static TopicFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TopicFragment fragment = new TopicFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
