package com.douzi.dd.utils;

import java.util.List;

public class ListUtils {

    /**
     * 判断list是否为空，即对象是否为空或数量为0
     * 
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 判断list是否不为空，即对象不为空切数量大于0
     * 
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }
}
