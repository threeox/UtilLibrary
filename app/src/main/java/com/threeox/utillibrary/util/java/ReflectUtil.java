package com.threeox.utillibrary.util.java;

import com.threeox.utillibrary.util.Basic2ObjUtil;
import com.threeox.utillibrary.util.EmptyUtils;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @ClassName: ReflectUtil
 *
 * @Description: Todo(反射的工具类)
 *
 * @author 赵屈犇
 *
 * @date 创建时间:2017/8/7 下午9:57
 * 
 * @version 1.0
 */
public class ReflectUtil {

    /**
     * 得到对象的Field   一直找直到找完为止
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getObjectField(Class clazz, String fieldName) throws Exception {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field;
        } catch (NoSuchFieldException e) {
            return getObjectField(clazz.getSuperclass(), fieldName);
        }
    }

    /**
     * 根据对象得到值
     *
     * @param object
     * @param key
     * @return
     */
    public static Object getValue(Object object, String key) {
        try {
            if (EmptyUtils.isNotEmpty(key)) {
                String[] keys = key.split("\\.");
                Object result = null;
                if (EmptyUtils.isNotEmpty(keys) && keys.length > 1) {
                    Object subObject = object;
                    for (int i = 0; i < keys.length - 1; i++) {
                        if (subObject != null) {
                            subObject = getFieldValue(subObject, keys[i]);
                        } else {
                            return result;
                        }
                    }
                    if (subObject != null) {
                        result = getFieldValue(subObject, keys[keys.length - 1]);
                    }
                    return result;
                } else {
                    return getFieldValue(object, key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param object
     * @param fieldName
     * @return
     */
    private static Object getFieldValue(Object object, String fieldName) {
        try {
            Class clazz = object.getClass();
            Field field = getObjectField(clazz, fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将变量名变为参数
     *
     * @param cls
     * @param lists
     * @return
     */
    public static Object addBean2List(Class cls, List<Map<String, Object>> lists) {
        try {
            // 实例化对象
            Object obj = cls.newInstance();
            // 获得不包含父类的方法
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                for (Map<String, Object> map : lists) {
                    Set set = map.keySet();
                    if (set != null) {
                        Iterator it = set.iterator();
                        while (it.hasNext()) {
                            String name = it.next().toString();
                            String value = map.get(name).toString();
                            if (("set" + name).equalsIgnoreCase(method.getName())) {
                                method.invoke(obj, value);
                            }
                        }
                    }
                }
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到一个集合 根据结果集
     *
     * @param result
     * @param t
     * @return
     * @throws Exception
     */
    public static <T> List<T> getListResultSet(ResultSet result, T t) throws Exception {
        List<T> datas = new ArrayList<T>();
        T data = null;
        Class<? extends Object> cls = t.getClass();
        while (result.next()) {
            data = getObjResultSet(result, cls);
            datas.add(data);
        }
        return datas;
    }

    /**
     * 得到一个对象 根据结果集
     *
     * @param result
     * @param t
     * @return
     * @throws Exception
     */
    public static <T> T getObjResultSet(ResultSet result, T t) throws Exception {
        T data = null;
        Class<? extends Object> cls = t.getClass();
        while (result.next()) {
            data = getObjResultSet(result, cls);
        }
        return data;
    }



    /**
     * 得到单个对象
     *
     * @param result
     * @param cls
     * @return
     * @throws Exception
     */
    private static <T> T getObjResultSet(ResultSet result, Class<? extends Object> cls) throws Exception {
        T data = (T) cls.newInstance();
        if (data instanceof Map) {
            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1 ; i <= columnCount; i++) {
                try {
                    String name = metaData.getColumnName(i);
                    ((Map) data).put(name, result.getObject(name));
                } catch (Exception e) {
                    // logger.info("<<" + cls.getSimpleName() + ">>类中的,注入失败:<<" + e.getMessage() + ">>");
                }
            }
        } else {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                try {
                    Class<?> typeClzz = field.getType();
                    if (!Basic2ObjUtil.getInstance().isBasicType(typeClzz)) {
                        typeClzz = Basic2ObjUtil.getInstance().getBasicClass(typeClzz);
                    }
                    field.setAccessible(true);
                    if (typeClzz.equals(String.class)) {
                        field.set(data, result.getString(field.getName()));
                    } else if (typeClzz.equals(Integer.class)) {
                        field.set(data, result.getInt(field.getName()));
                    } else if (typeClzz.equals(Long.class)) {
                        field.set(data, result.getLong(field.getName()));
                    } else if (typeClzz.equals(Float.class)) {
                        field.set(data, result.getFloat(field.getName()));
                    } else if (typeClzz.equals(Double.class)) {
                        field.set(data, result.getDouble(field.getName()));
                    } else if (typeClzz.equals(Boolean.class)) {
                        field.set(data, result.getBoolean(field.getName()));
                    } else if (typeClzz.equals(Byte.class)) {
                        field.set(data, result.getByte(field.getName()));
                    } else if (typeClzz.equals(Short.class)) {
                        field.set(data, result.getShort(field.getName()));
                    } else if (typeClzz.equals(Date.class)) {
                        field.set(data, result.getDate(field.getName()));
                    } else if (typeClzz.equals(Array.class)) {
                        field.set(data, result.getArray(field.getName()));
                    } else if (typeClzz.equals(InputStream.class)) {
                        field.set(data, result.getBinaryStream(field.getName()));
                    } else if (typeClzz.equals(Blob.class)) {
                        field.set(data, result.getBlob(field.getName()));
                    } else if (typeClzz.equals(Clob.class)) {
                        field.set(data, result.getClob(field.getName()));
                    } else {
                        field.set(data, result.getObject(field.getName()));
                    }
                } catch (Exception e) {
                    // logger.info("<<"+ cls.getSimpleName() + ">>类中的<<" + field.getName() + ">>属性,注入失败:" + e.getMessage());
                }
            }
        }
        return data;
    }

    /**
     * 设置对象值
     *
     * @param data
     * @param key
     * @param value
     */
    public static void setObjectValue(Object data, String key, Object value) throws Exception {
        Class<?> clazz = data.getClass();
        Field field = clazz.getDeclaredField(key);
        field.setAccessible(true);
        field.set(data, value);
    }

}
