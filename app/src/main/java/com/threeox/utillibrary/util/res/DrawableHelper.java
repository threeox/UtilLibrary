package com.threeox.utillibrary.util.res;

import android.content.Context;

import com.threeox.utillibrary.util.EmptyUtils;
import com.threeox.utillibrary.util.LogUtils;

/**
 *
 * @ClassName: DrawableHelper
 *
 * @Description: TODO(Drawable的帮助类))
 *
 * @author 赵屈犇
 *
 * @date 创建时间: 2017/6/28 18:16
 *
 * @version 1.0
 *
 */
public class DrawableHelper {

    public static final String TAG = DrawableHelper.class.getName();

    private Context mContext = null;
    private IdHelper mIdHelper;

    private DrawableHelper(){
        throw new UnsupportedOperationException("不能初始化DrawableHelper");
    }

    private DrawableHelper(Context context){
        this.mContext = context;
        mIdHelper = IdHelper.newInstance(mContext);
    }

    public static DrawableHelper newInstance(Context context){
        return new DrawableHelper(context);
    }

    /**
     * 得到DrawId 根据name
     *
     * @param name
     * @return
     */
    public Integer getDrawId(String name) {
        try {
            if (EmptyUtils.isEmpty(name)) {
                return null;
            } else{
                return mIdHelper.getIdByName(name, IdHelper.ResType.drawable);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "根据图片Id没有拿到图片!");
            return null;
        }
    }
}
