package com.threeox.utillibrary.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;

import com.threeox.utillibrary.util.file.HandlerMessage;

/**
 *
 * @ClassName: HandlerUtils
 *
 * @Description: Todo(handler 工具类)
 *
 * @author 赵屈犇
 *
 * @date 创建时间:2016/12/22 14:54
 *
 * @version 1.0
 */
public class HandlerUtils {

    private HandlerMessage handlerMessage;
    private OnHandlerListener onHandlerListener;

    private HandlerUtils() {
        handlerMessage = new HandlerMessage();
    }

    public static HandlerUtils getInstance() {
        return new HandlerUtils();
    }

    public HandlerUtils putWhat(int what) {
        handlerMessage.setWhat(what);
        return this;
    }

    public HandlerUtils putArg1(int arg1) {
        handlerMessage.setArg1(arg1);
        return this;
    }

    public HandlerUtils putArg2(int arg2) {
        handlerMessage.setArg2(arg2);
        return this;
    }

    public HandlerUtils put(Object obj) {
        handlerMessage.setObj(obj);
        return this;
    }

    public HandlerUtils put(Messenger replyTo) {
        handlerMessage.setReplyTo(replyTo);
        return this;
    }

    public HandlerUtils put(Bundle bundle) {
        handlerMessage.setBundle(bundle);
        return this;
    }

    /**
     * 发送Handler
     *
     * @return
     */
    public HandlerUtils send() {
        Message message = handler.obtainMessage();
        handler.sendMessage(handlerMessage.initHandlerMessage(message));
        return init();
    }

    /**
     * 延时发送Handler
     *
     * @return
     */
    public HandlerUtils send(long millis) {
        Message message = handler.obtainMessage();
        handler.sendMessageDelayed(handlerMessage.initHandlerMessage(message), millis);
        return init();
    }

    private HandlerUtils init() {
        // 初始化message 对象
        handlerMessage = new HandlerMessage();
        return this;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (onHandlerListener != null) {
                try {
                    onHandlerListener.onHandleMessage(msg);
                } catch (Exception e) {
                    LogUtils.e("HandlerUtils", "onHandleMessage回调里报错了:" + e.getMessage());
                }
            }
        }
    };

    public HandlerUtils setOnHandlerListener(OnHandlerListener onHandlerListener) {
        this.onHandlerListener = onHandlerListener;
        return this;
    }

    public interface OnHandlerListener {

        void onHandleMessage(Message msg) throws Exception;

    }

}
