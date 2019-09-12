package com.threeox.utillibrary.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;

import com.threeox.utillibrary.R;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @ClassName: ActivityHelper
 * 
 * @Description: TODO(Activity 的帮助类)
 * 
 * @author 赵屈犇
 * 
 * @date 创建时间: 2017/7/20 14:53
 * 
 * @version 1.0
 * 
 */
public class ActivityHelper {

    protected static final String TAG = ActivityHelper.class.getName();

    protected ActivityHelper() {
        throw new UnsupportedOperationException("不能直接构造...");
    }

    protected Context mContext;
    protected Class mClass;
    protected Intent mIntent = null;
    protected Bundle mBundle = null;
    protected Class<? extends Bundle> mBundelCls;
    protected Class<? extends Intent> mInClass;

    protected ActivityHelper(Context context, Class clazz) {
        this.mContext = context;
        this.mClass = clazz;
        this.mIntent = new Intent(mContext, clazz);
        this.mInClass = mIntent.getClass();
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(R.anim.left_start_activity, R.anim.right_start_activity);
        }
    }

    public static ActivityHelper init(Context context, Class cls) {
        return new ActivityHelper(context, cls);
    }

    /**
     * 传入Bundle参数
     *
     * @param key
     * @param value
     * @return
     */
    public ActivityHelper putBundle(String key, Object value) {
        if (mBundle == null) {
            mBundle = new Bundle();
            mBundelCls = mBundle.getClass();
        }
        put(mBundelCls, mBundle, key, value);
        return this;
    }

    /**
     * 直接传入Intent
     *
     * @param intent
     * @return
     */
    public ActivityHelper putIntent(Intent intent) {
        mIntent.putExtras(intent);
        return this;
    }

    /**
     * 键值对传入
     *
     * @param key
     * @param value
     * @return
     */
    public ActivityHelper putIntent(String key, Object value) {
        putExtra(mInClass, mIntent, key, value);
        return this;
    }

    /**
     * 传入Intent根据Map
     *
     * @param data
     * @return
     */
    public ActivityHelper putIntent(Map<String,Object> data) {
        if (data != null) {
            Set<String> set = data.keySet();
            for (String key : set) {
                putExtra(mInClass, mIntent, key, data.get(key));
            }
        }
        return this;
    }

    /**
     * 跳转Activity
     *
     * @return
     */
    public ActivityHelper start() {
        mContext.startActivity(mIntent);
        return this;
    }

    /**
     * 跳转Activity For Result
     *
     * @return
     */
    public ActivityHelper start(int requestCode) {
        ((Activity) mContext).startActivityForResult(mIntent, requestCode);
        return this;
    }

    /**
     * 设置值
     *
     * @param cls
     * @param obj
     * @param key
     * @param value
     */
    public static void putExtra(Class cls, Object obj, String key, Object value) {
        if (obj != null) {
            Class<? extends Object> clazz = null;
            try {
                clazz = Basic2ObjUtil.getInstance().getBasicClass(value.getClass());
                injectMethod(cls, obj, key, value, clazz);
            } catch (Exception e) {
                try {
                    if (value instanceof Serializable) {
                        clazz = Serializable.class;
                    } else if (value instanceof Parcelable) {
                        clazz = Parcelable.class;
                    } else if (value instanceof Parcelable[]) {
                        clazz = Parcelable[].class;
                    }
                    injectMethod(cls, obj, key, value, clazz);
                } catch (Exception e1) {
                    LogUtils.e(TAG, "注入数据失败:" + e1.getMessage());
                }
            }
        } else {
            LogUtils.e(TAG, "请初始化对象!");
        }
    }


    public static void put(Class cls, Object obj, String key, Object value) {
        Class<? extends Object> clazz = null;
        try {
            clazz = Basic2ObjUtil.getInstance().getBasicClass(value.getClass());
            injectMethod(cls, obj, key, value, clazz);
        } catch (Exception e) {
            try {
                if (value instanceof Serializable) {
                    clazz = Serializable.class;
                } else if (value instanceof Parcelable) {
                    clazz = Parcelable.class;
                } else if (value instanceof Parcelable[]) {
                    clazz = Parcelable[].class;
                }
                injectMethod(cls, obj, key, value, clazz);
            } catch (Exception e1) {
                LogUtils.e(TAG, "注入数据失败:" + e1.getMessage());
            }
        }
    }

    private static void injectMethod(Class cls, Object obj, String key, Object value, Class<? extends Object> clazz) throws Exception {
        LogUtils.d(TAG, "转换之后的Cls:" + clazz.getName());
        Method method = cls.getMethod("putExtra", String.class, clazz);
        method.invoke(obj, key, value);
        LogUtils.d(TAG, "注入数据成功:" + clazz.getName() + "-->" + value);
    }

    /**
     * 判断是否存在Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(Context context, String packageName, String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(context.getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(context.getPackageManager()) == null ||
                context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * 打开Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   全类名
     */
    public static void launchActivity(Context context, String packageName, String className) {
        launchActivity(context, packageName, className, null);
    }

    /**
     * 打开Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   全类名
     * @param bundle      bundle
     */
    public static void launchActivity(Context context, String packageName, String className, Bundle bundle) {
        context.startActivity(IntentUtils.getComponentIntent(packageName, className, bundle));
    }

    /**
     * 获取launcher activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @return launcher activity
     */
    public static String getLauncherActivity(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo info : infos) {
            if (info.activityInfo.packageName.equals(packageName)) {
                return info.activityInfo.name;
            }
        }
        return "no " + packageName;
    }


}
