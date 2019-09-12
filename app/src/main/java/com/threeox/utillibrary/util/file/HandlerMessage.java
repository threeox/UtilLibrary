package com.threeox.utillibrary.util.file;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;

import java.io.Serializable;

/**
 *
 * @ClassName: HandlerMessage
 *
 * @Description: Todo()
 *
 * @author 赵屈犇
 *
 * @date 创建时间:2018/11/30 14:21
 *
 * @version 1.0
 */
public class HandlerMessage implements Serializable {

    private int what;
    private int arg1;
    private int arg2;
    private Object obj;
    private Bundle bundle;
    private Messenger replyTo;

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public int getArg1() {
        return arg1;
    }

    public void setArg1(int arg1) {
        this.arg1 = arg1;
    }

    public int getArg2() {
        return arg2;
    }

    public void setArg2(int arg2) {
        this.arg2 = arg2;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Messenger getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Messenger replyTo) {
        this.replyTo = replyTo;
    }

    /**
     * 初始化获取HanderlMessage
     *
     * @param message
     * @return
     */
    public Message initHandlerMessage(Message message) {
        message.what = getWhat();
        message.arg1 = arg1;
        message.arg2 = arg2;
        message.obj = getObj();
        message.setData(getBundle());
        message.replyTo = getReplyTo();
        return message;
    }

}
