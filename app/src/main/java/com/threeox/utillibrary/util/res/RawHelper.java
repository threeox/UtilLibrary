package com.threeox.utillibrary.util.res;

import android.content.Context;
import android.content.res.Resources;

import com.threeox.utillibrary.util.EmptyUtils;
import com.threeox.utillibrary.util.LogUtils;
import com.threeox.utillibrary.util.file.CloseUtils;

import java.io.InputStream;

/**
 *
 * @ClassName: RawHelper
 *
 * @Description: TODO(Raw下的资源帮助类)
 *
 * @author 赵屈犇
 *
 * @date 创建时间: 2017/6/28 18:10
 *
 * @version 1.0
 *
 */
public class RawHelper {

    public static final String TAG = RawHelper.class.getName();

    private Context mContext = null;
    private IdHelper mIdHelper = null;

    private RawHelper(){
        throw new UnsupportedOperationException("不能初始化RawHelper");
    }

    private RawHelper(Context context){
        this.mContext = context;
        mIdHelper = IdHelper.newInstance(mContext);
    }

    public static RawHelper newInstance(Context context){
        return new RawHelper(context);
    }

    /**
     * 读取文本文件
     *
     * @param rawName
     * @return
     */
    public String readText(String rawName) {
        try {
            if (EmptyUtils.isNotEmpty(rawName)) {
                Integer rawId = mIdHelper.getIdByName(rawName, IdHelper.ResType.raw);
                return readText(rawId);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 读取文本文件
     *
     * @param rawId
     * @return
     */
    public String readText(int rawId) {
        Resources resources = mContext.getResources();
        InputStream is = null;
        try {
            is = resources.openRawResource(rawId);
            byte buffer[] = new byte[is.available()];
            is.read(buffer);
            String content = new String(buffer);
            LogUtils.d(TAG, "read:" + content);
            return content;
        } catch (Exception e) {
            LogUtils.e(TAG, "write file" + e.getMessage());
        } finally {
            CloseUtils.closeIO(is);
        }
        return "";
    }
}
