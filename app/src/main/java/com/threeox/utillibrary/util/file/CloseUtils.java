package com.threeox.utillibrary.util.file;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @ClassName: CloseUtils
 *
 * @Description: (关闭相关工具类)
 *
 * @author 赵屈犇
 *
 * @date 创建时间:2016/12/19 15:47
 *
 * @version 1.0
 */
public class CloseUtils {

    private CloseUtils() {
        throw new UnsupportedOperationException("没有构造函数...");
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIOQuietly(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
