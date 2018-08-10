package com.oschina.client.base_library.bottom.iterator;


import com.oschina.client.base_library.bottom.BottomTabItem;

/**
 * Created by yanzhiwen on 2017/10/22.
 */

public interface TabIterator {
    BottomTabItem next();

    boolean hashNext();
}
