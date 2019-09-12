package com.threeox.utillibrary.util.res;

import android.content.Context;

import com.threeox.utillibrary.util.EmptyUtils;
import com.threeox.utillibrary.util.LogUtils;

/**
 *
 * @ClassName: LayoutHelper
 *
 * @Description: TODO(Layout的帮助类)
 *
 * @author 赵屈犇
 *
 * @date 创建时间: 2017/6/28 18:19
 *
 * @version 1.0
 *
 */
public class LayoutHelper {

    public static final String TAG = LayoutHelper.class.getName();

    private Context mContext = null;
    private IdHelper mIdHelper = null;

    private LayoutHelper(){
        throw new UnsupportedOperationException("不能初始化LayoutHelper");
    }

    private LayoutHelper(Context context){
        this.mContext = context;
        mIdHelper = IdHelper.newInstance(mContext);
    }

    public static LayoutHelper newInstance(Context context){
        return new LayoutHelper(context);
    }

    /**
     * 得到布局Id 根据name
     *
     * @param name
     * @return
     */
    public Integer getLayoutId(String name) {
        try {
            if (EmptyUtils.isEmpty(name)) {
                return null;
            } else{
                return mIdHelper.getIdByName(name, IdHelper.ResType.layout);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "根据布局Id没有拿到布局!");
            return null;
        }
    }

}
