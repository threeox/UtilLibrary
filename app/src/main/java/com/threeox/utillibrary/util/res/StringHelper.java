package com.threeox.utillibrary.util.res;

import android.content.Context;

import com.threeox.utillibrary.util.EmptyUtils;

/**
 * 
 * @ClassName: StringHelper
 * 
 * @Description: TODO(String 文件夹下的帮助类)
 * 
 * @author 赵屈犇
 * 
 * @date 创建时间: 2017/6/28 18:15
 * 
 * @version 1.0
 * 
 */
public class StringHelper {

    public static final String TAG = StringHelper.class.getName();

    private Context mContext = null;
    private IdHelper mIdHelper = null;

    private StringHelper(){
        throw new UnsupportedOperationException("不能初始化StringHelper");
    }

    private StringHelper(Context context){
        this.mContext = context;
        mIdHelper = IdHelper.newInstance(mContext);
    }

    public static StringHelper newInstance(Context context){
        return new StringHelper(context);
    }

    /**
     * 根据 name得到String  如果没有便是返回Name本身
     *
     * @param name
     * @return
     */
    public String getStringText(String name) {
        try {
            Integer resId =  mIdHelper.getIdByName(name, IdHelper.ResType.string);
            if (EmptyUtils.isNotEmpty(resId)) {
                return getString(resId);
            } else {
                return name;
            }
        } catch (Exception e) {
            return name;
        }
    }


    /**
     * 得到 string 根据 name
     *
     * @param name
     * @return
     */
    public String getString(String name) {
        try {
            Integer resId =  mIdHelper.getIdByName(name, IdHelper.ResType.string);
            if (EmptyUtils.isNotEmpty(resId)) {
                return getString(resId);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据 id
     *
     * @param resId
     * @return
     */
    public String getString(int resId) {
        return mContext.getString(resId);
    }


    /**
     * 得到 string 根据 name
     *
     * @param name
     * @return
     */
    public String[] getStringArray(String name) {
        try {
            Integer resId =  mIdHelper.getIdByName(name, IdHelper.ResType.array);
            if (EmptyUtils.isNotEmpty(resId)) {
                return getStringArray(resId);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据 id
     *
     * @param resId
     * @return
     */
    public String[] getStringArray(int resId) {
        return mContext.getResources().getStringArray(resId);
    }

}
