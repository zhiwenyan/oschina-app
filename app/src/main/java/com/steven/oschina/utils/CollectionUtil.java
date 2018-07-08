package com.steven.oschina.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Description:
 * Data：7/6/2018-11:02 AM
 *
 * @author yanzhiwen
 */
public class CollectionUtil {
    public CollectionUtil() {
    }

    public static <T> T[] toArray(List<T> items, Class<T> tClass) {
        if (items != null && items.size() != 0) {
            int size = items.size();

            try {
                T[] array = ( T[] ) Array.newInstance(tClass, size);
                return items.toArray(array);
            } catch (Exception var4) {
                var4.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> T[] toArray(Set<T> items, Class<T> tClass) {
        if (items != null && items.size() != 0) {
            int size = items.size();

            try {
                T[] array = ( T[] ) Array.newInstance(tClass, size);
                return items.toArray(array);
            } catch (Exception var4) {
                var4.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> HashSet<T> toHashSet(T[] items) {
        if (items != null && items.length != 0) {
            HashSet<T> set = new HashSet();
            Collections.addAll(set, items);
            return set;
        } else {
            return null;
        }
    }

    public static <T> ArrayList<T> toArrayList(T[] items) {
        if (items != null && items.length != 0) {
            ArrayList<T> list = new ArrayList();
            Collections.addAll(list, items);
            return list;
        } else {
            return null;
        }
    }

    public static <T> Collection<T> move(List<T> collection, int fromPosition, int toPosition) {
        int maxPosition = collection.size() - 1;
        if (fromPosition != toPosition && fromPosition <= maxPosition && toPosition <= maxPosition) {
            Object fromModel;
            if (fromPosition < toPosition) {
                fromModel = collection.get(fromPosition);
                T toModel = collection.get(toPosition);
                collection.remove(fromPosition);
                collection.add(collection.indexOf(toModel) + 1, ( T ) fromModel);
            } else {
                fromModel = collection.get(fromPosition);
                collection.remove(fromPosition);
                collection.add(toPosition, ( T ) fromModel);
            }

            return collection;
        } else {
            return collection;
        }
    }

    public static <T> List<T> filter(List<T> source, CollectionUtil.Checker<T> checker) {
        Iterator iterator = source.iterator();

        while(iterator.hasNext()) {
            if (!checker.check(( T ) iterator.next())) {
                iterator.remove();
            }
        }

        return source;
    }

    public static <T> List<T> filter(T[] source, CollectionUtil.Checker<T> checker) {
        ArrayList<T> list = toArrayList(source);
        return filter((List)list, checker);
    }

    public interface Checker<T> {
        boolean check(T var1);
    }
}
