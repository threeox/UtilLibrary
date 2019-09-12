package com.threeox.utillibrary.util.file;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 *
 * @ClassName: SPUtils
 *
 * @Description: (SPUtils)
 *
 * @author 赵屈犇
 *
 * @date 创建时间:2016/12/19 15:57 
 * 
 * @version 1.0
 */
@SuppressLint({ "CommitPrefEdits", "NewApi" })
@SuppressWarnings("unchecked")
public class SharedPreferencesUtil {

    private Context mContext = null;
    public SharedPreferences mSharedPreferences = null;

    private static final String SPFFILENAME = SharedPreferencesUtil.class.getName();

    private SharedPreferencesUtil(Context context) {
        this(context, SPFFILENAME);
    }

    private SharedPreferencesUtil(Context context, String key) {
        this(context, key, Context.MODE_PRIVATE);
    }

    private SharedPreferencesUtil(Context context, String key, int mode) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(key, mode);
    }

    public static SharedPreferencesUtil newInstance(Context context) {
        return new SharedPreferencesUtil(context);
    }

    public static SharedPreferencesUtil newInstance(Context context, String key) {
        return new SharedPreferencesUtil(context, key);
    }

    public static SharedPreferencesUtil newInstance(Context context, int mode) {
        return new SharedPreferencesUtil(context, SPFFILENAME, mode);
    }

    public static SharedPreferencesUtil newInstance(Context context, String key, int mode) {
        return new SharedPreferencesUtil(context, key, mode);
    }

    /**
     * 添加一个数据
     *
     * @param key
     * @param value
     * @return
     */
    public SharedPreferencesUtil put(String key, Object value) {
        Editor edit = mSharedPreferences.edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else if (value instanceof Set) {
            edit.putStringSet(key, (Set<String>) value);
        }
        SharedPreferencesCompat.apply(edit);
        return this;
    }

    /**
     * 得到一个数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public <T> T get(String key, Object defValue) {
        Object value = null;
        if (defValue instanceof String || defValue == null) {
            value = mSharedPreferences.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            value = mSharedPreferences.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            value = mSharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Long) {
            value = mSharedPreferences.getLong(key, (Long) defValue);
        } else if (defValue instanceof Float) {
            value = mSharedPreferences.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Set) {
            value = mSharedPreferences.getStringSet(key, (Set<String>) defValue);
        }
        return (T) value;
    }

    /**
     * 根据key移除
     *
     * @param key
     * @return
     */
    public SharedPreferencesUtil remove(String key) {
        try {
            SharedPreferencesCompat
                    .apply(mSharedPreferences.edit().remove(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 清空数据
     *
     * @return
     */
    public SharedPreferencesUtil clear() {
        SharedPreferencesCompat.apply(mSharedPreferences.edit().clear());
        return this;
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return mSharedPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     *
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod() {
            try {
                Class clz = Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
