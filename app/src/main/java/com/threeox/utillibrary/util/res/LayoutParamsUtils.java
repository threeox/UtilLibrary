package com.threeox.utillibrary.util.res;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

/**
 *
 * @ClassName: LayoutParamsUtils
 *
 * @Description: ()
 *
 * @author 赵屈犇
 *
 * @date 创建时间:2016/12/19 18:24
 *
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class LayoutParamsUtils {

	private Context mContext = null;

	private LayoutParamsUtils(){}

	private LayoutParamsUtils(Context context){
		this.mContext = context;
	}

	public static LayoutParamsUtils newInstance(Context context){
		return new LayoutParamsUtils(context);
	}

	public LayoutParams getLinearLayoutParams() {
		return getLinearLayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
	}

	/**
	 * 得到LinearLayout的Params
	 *
	 * @param width
	 * @param height
	 * @return
	 */
	public LayoutParams getLinearLayoutParams(int width, int height) {
		width = getDip(width);
		height = getDip(height);
		return new LayoutParams(width, height);
	}

	/**
	 *  得到RelativeLayout.LayoutParams的Params
	 *
	 * @param width
	 * @param height
	 * @return
	 */
	public RelativeLayout.LayoutParams getRelativeLayoutParams(int width, int height) {
		width = getDip(width);
		height = getDip(height);
		return new RelativeLayout.LayoutParams(width, height);
	}

	public int getDip(int val) {
		if (val != android.view.ViewGroup.LayoutParams.WRAP_CONTENT
				&& val != android.view.ViewGroup.LayoutParams.MATCH_PARENT) {
			val = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					val, mContext.getResources().getDisplayMetrics());
		}
		return val;
	}

	/**
	 *  得到WrapContent的LayoutParams
	 *
	 * @param v
	 * @param <T>
	 * @return
	 */
	public <T extends Object> T getLayoutParamsWrap(View v) {
		return getLayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, v);
	}

	/**
	 * 根据View得到LayoutParmams
	 *
	 * @param width
	 * @param height
	 * @param v
	 * @param <T>
	 * @return
	 */
	public <T extends Object> T getLayoutParams(int width, int height,
			View v) {
		width = getDip( width);
		height = getDip(height);
		if (v instanceof FrameLayout) {
			return (T) new FrameLayout.LayoutParams(width, height);
		}
		return (T) new LayoutParams(width, height);
	}
}
