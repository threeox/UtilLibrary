package com.threeox.utillibrary.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ClassName: Basic2ObjUtil
 *
 * @Description: TODO(基本类型和对象之间转换的工具类)
 *
 * @author 赵屈犇
 *
 * @date 创建时间: 2017/6/28 18:01
 *
 * @version 1.0
 *
 */
public class Basic2ObjUtil {

    private static Basic2ObjUtil mInstance = null;

    private Map<Class, Class> basicMap = new HashMap<Class, Class>();

    private Basic2ObjUtil() {
    }

    public static Basic2ObjUtil getInstance() {
        if (mInstance == null) {
            synchronized (Basic2ObjUtil.class) {
                if (mInstance == null) {
                    mInstance = new Basic2ObjUtil();
                }
            }
        }
        return mInstance;
    }


    /**
     * 获得包装类
     *
     * @param typeClass
     * @return
     */
    public Class<? extends Object> getBasicClass(Class typeClass) {
        Class clazz = getBasicMap().get(typeClass);
        if (clazz == null) {
            clazz = typeClass;
        }
        return clazz;
    }

    /**
     * 得到基本类型的Map
     *
     * @return
     */
    public Map<Class, Class> getBasicMap() {
        if (EmptyUtils.isEmpty(basicMap)) {
            return initBasicMap();
        }
        return basicMap;
    }

    /**
     * 初始化基本类型Map
     *
     * @return
     */
    private Map<Class, Class> initBasicMap() {
        basicMap.put(int.class, Integer.class);
        basicMap.put(long.class, Long.class);
        basicMap.put(float.class, Float.class);
        basicMap.put(double.class, Double.class);
        basicMap.put(boolean.class, Boolean.class);
        basicMap.put(byte.class, Byte.class);
        basicMap.put(short.class, Short.class);
        // basicMap.put(char.class, char.class);
        basicMap.put(Integer.class, int.class);
        basicMap.put(Long.class, long.class);
        basicMap.put(Float.class, float.class);
        basicMap.put(Double.class, double.class);
        basicMap.put(Boolean.class, boolean.class);
        basicMap.put(Byte.class, byte.class);
        basicMap.put(Short.class, short.class);
        // basicMap.put(char.class, char.class);
        return basicMap;
    }

    /**
     * 判断是不是基本类型
     *
     * @param typeClass
     * @return
     */
    public boolean isBasicType(Class typeClass) {
        if (typeClass.equals(Integer.class) || typeClass.equals(Long.class)
                || typeClass.equals(Float.class)
                || typeClass.equals(Double.class)
                || typeClass.equals(Boolean.class)
                || typeClass.equals(Byte.class)
                || typeClass.equals(Short.class)
                || typeClass.equals(String.class)) {
            return true;
        } else {
            return false;
        }
    }
}
