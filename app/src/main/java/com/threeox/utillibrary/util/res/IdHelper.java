package com.threeox.utillibrary.util.res;

import android.content.Context;

import com.threeox.utillibrary.util.EmptyUtils;
import com.threeox.utillibrary.util.LogUtils;

/**
 *
 * @ClassName: IdHelper
 *
 * @Description: TODO(Id的帮助类)
 *
 * @author 赵屈犇
 *
 * @date 创建时间: 2017/6/28 18:20
 *
 * @version 1.0
 *
 */
public class IdHelper {

    public static final String TAG = IdHelper.class.getName();

    private Context mContext = null;

    private IdHelper(){
        throw new UnsupportedOperationException("不能初始化IdHelper");
    }

    private IdHelper(Context context){
        this.mContext = context;
    }

    public static IdHelper newInstance(Context context){
        return new IdHelper(context);
    }

    /**
     * 得到ViewId 根据name
     *
     * @param name
     * @return
     */
    public Integer getViewId(String name) {
        try {
            if (EmptyUtils.isEmpty(name)) {
                return null;
            } else{
                return getIdByName(name, ResType.id);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "根据ViewId没有拿到View的Id!");
            return null;
        }
    }

    /**
     * 根据文件名称得到Id
     *
     * @param name
     * @param resType
     * @return
     */
    public Integer getIdByName(String name, ResType resType) {
        try {
            if (EmptyUtils.isNotEmpty(name)) {
                int identifier = mContext.getResources().getIdentifier(name, resType.toString(), mContext.getPackageName());
                if (0 == identifier) {
                    return null;
                } else {
                    return identifier;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "getIdByName报错:" + e.getMessage());
            return null;
        }
    }

    /**
     * 资源类型
     */
    public enum ResType {
        string, drawable, layout, id, raw, color, dimen, anim, menu, mipmap, bool, integer, array
    }

}
