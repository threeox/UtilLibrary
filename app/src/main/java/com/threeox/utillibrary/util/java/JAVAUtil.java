package com.threeox.utillibrary.util.java;

import android.content.Context;

import com.threeox.utillibrary.util.EmptyUtils;
import com.threeox.utillibrary.util.LogUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 *
 * @ClassName: JAVAUtil
 *
 * @Description: TODO(JAVA 相关的工具类)
 *
 * @author 赵屈犇
 *
 * @date 创建时间: 2017/7/7 12:35
 *
 * @version 1.0
 *
 */
public class JAVAUtil {

    private final String TAG = JAVAUtil.class.getName();

    private static JAVAUtil mInstance = null;

    private Context mContext;
    /**
     * 全部的类名称集合
     */
    private List<String> mAllClassNames = null;

    private JAVAUtil(){
        throw new UnsupportedOperationException("不能初始化JAVAUtil");
    }

    private JAVAUtil(Context context) {
        this.mContext = context;
    }

    public static JAVAUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (JAVAUtil.class) {
                if (mInstance == null) {
                    mInstance = new JAVAUtil(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 得到本程序的所有类全名称
     *
     * @return
     */
    public List<String> getAllClassName() {
        if (EmptyUtils.isNotEmpty(mAllClassNames)){
            return mAllClassNames;
        }
        mAllClassNames = new ArrayList<String>();
        try {
            String appPath = mContext.getPackageResourcePath();
            File file = new File(appPath).getParentFile();
            if (file.getName().equals("app")) {
                mAllClassNames.addAll(getClassName2App(new File((appPath))));
            } else {
                String[] list = file.list();
                for (String data : list) {
                    if (data.endsWith(".apk")) {
                        mAllClassNames.addAll(getClassName2App(new File(file, data)));
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return mAllClassNames;
    }

    /**
     * 根据APP地址得到 类全名
     *
     * @param appFile
     * @return
     */
    public List<String> getClassName2App(File appFile) {
        List<String> classNames = new ArrayList<String>();
        try {
            DexFile df = new DexFile(appFile);
            Enumeration entries = df.entries();
            for (Enumeration iter = entries; iter.hasMoreElements(); ) {
                String className = (String) iter.nextElement();
                classNames.add(className);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classNames;
    }

    /**
     * 根据多个包名的到多个Class对象
     *
     * @param anntations
     * @param packNames
     * @return
     */
    public List<Class> getClazzByPackage(List<Class<? extends Annotation>> anntations, String... packNames) {
        List<Class> classNames = new ArrayList<Class>();
        if (EmptyUtils.isEmpty(packNames)) {
            LogUtils.d(TAG, "根据包名取得必须传入包名");
            return classNames;
        }
        if (EmptyUtils.isEmpty(anntations)) {
            LogUtils.d(TAG, "必须传入注解类Class");
            return classNames;
        }
        List<String> mAllClassNames = getAllClassName();
        if (EmptyUtils.isNotEmpty(mAllClassNames)){
            for (String className: mAllClassNames) {
                for (String packName: packNames) {
                    if (className.contains(packName) && EmptyUtils.isNotEmpty(packName)) {
                        try {
                            Class<?> clazz = Class.forName(className);
                            for (Class anntationClazz :anntations) {
                                if (clazz.isAnnotationPresent(anntationClazz)) {
                                    classNames.add(clazz);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return classNames;
    }

    /**
     * 根据多个包名得到Class对象
     *
     * @param anntation
     * @param packNames
     * @return
     */
    public List<Class> getClazzByPackage(Class<? extends Annotation> anntation, String... packNames) {
        List<Class> classNames = new ArrayList<Class>();
        if (EmptyUtils.isEmpty(packNames)) {
            LogUtils.d(TAG, "根据包名取得必须传入包名");
            return classNames;
        }
        List<String> mAllClassNames = getAllClassName();
        if (EmptyUtils.isNotEmpty(mAllClassNames)){
            for (String className: mAllClassNames) {
                for (String packName: packNames) {
                    if (className.contains(packName)) {
                        try {
                            Class<?> cl = Class.forName(className);
                            if (cl.isAnnotationPresent(anntation)) {
                                classNames.add(cl);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return classNames;
    }

    /**
     * 根据多个包名得到对象全包名
     *
     * @param anntation
     * @param packNames
     * @return
     */
    public List<String> getClazzByPackNames(Class<? extends Annotation> anntation, String... packNames) {
        List<String> classNames = new ArrayList<String>();
        if (EmptyUtils.isEmpty(packNames)) {
            LogUtils.d(TAG, "根据包名取得必须传入包名");
            return classNames;
        }
        List<String> mAllClassNames = getAllClassName();
        if (EmptyUtils.isNotEmpty(mAllClassNames)){
            for (String className: mAllClassNames) {
                for (String packName: packNames) {
                    if (className.contains(packName)) {
                        try {
                            classNames.add(packName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return classNames;
    }

}
