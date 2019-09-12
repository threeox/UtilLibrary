package com.threeox.utillibrary.util.res;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * @ClassName: LayoutUtils
 *
 * @Description: (布局的工具类)
 *
 * @author 赵屈犇
 *
 * @date 创建时间:2016/12/19 17:37
 *
 * @version 1.0
 */
public class LayoutUtils {

    public static final String TAG = LayoutUtils.class.getName();

    private LayoutUtils() { throw new UnsupportedOperationException("不能初始化LayoutUtils");}

    /**
     * inflat xml
     *
     * @param context
     * @param layoutId
     * @return
     */
    public static View inflate(Context context, int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(layoutId, null);
    }

    /**
     * inflat xml
     *
     * @param context
     * @param layoutId
     * @return
     */
    public static View inflate(Context context, int layoutId, ViewGroup view) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(layoutId, view);
    }

}