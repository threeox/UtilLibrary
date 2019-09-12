package com.threeox.utillibrary.util.thread;

import android.os.Handler;

/**
 *
 * @ClassName: ThreadUtil
 *
 * @Description: Todo(切换子线程和UI线程的)
 *
 * @author 赵屈犇
 *
 * @date 创建时间:2016/12/21 17:53
 *
 * @version 1.0
 */
public class ThreadUtil {

	/**
	 * @description: 在子线程
	 * @param r
	 */
	public static void runInThread(Runnable r) {
		new Thread(r).start();
	}

	private static Handler handler = new Handler();

	/**
	 * @description: 在UI线程
	 * 
	 * @author zqb
	 */
	public static void runUIThread(Runnable r) {
		handler.post(r);
	}
}
