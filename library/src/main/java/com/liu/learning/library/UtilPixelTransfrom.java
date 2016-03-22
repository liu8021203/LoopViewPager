package com.liu.learning.library;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * 像素转化 px , dp , sp
 */
public class UtilPixelTransfrom {

	private static WindowManager windowManager = null;
	/**
	 * 屏幕密度
	 */
	private static float scale_px = 0f;
	private static float scale_dp = 0f;
	private static float scale_sp = 0f;

	private static DisplayMetrics metrics = null;

	/**
	 * 得到屏幕的宽
	 * 
	 * @param activity
	 * @return 屏幕的宽
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Activity activity) {
		if (windowManager == null) {
			windowManager = activity.getWindowManager();
		}
		return windowManager.getDefaultDisplay().getWidth();
	}

	/**
	 * 得到屏幕的高
	 * 
	 * @param activity
	 * @return 屏幕的高
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Activity activity) {
		if (windowManager == null) {
			windowManager = activity.getWindowManager();
		}
		return windowManager.getDefaultDisplay().getHeight();
	}

	/**
	 * 得到指定视图的宽和高
	 * 
	 * @param view
	 *            指定的view
	 * @param widHei
	 *            存放宽高的数组
	 */
	public static void getViewWidthHeight(View view, int[] widHei) {
		view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		widHei[0] = view.getMeasuredHeight();
		widHei[1] = view.getMeasuredWidth();
	}

	/**
	 * 得到指定视图的宽
	 * 
	 * @param view
	 *            指定的view
	 */
	public static int getViewWidt(View view) {
		view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		return view.getMeasuredWidth();
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 * @param dpValue
	 *            设置的dp值
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		if (scale_px == 0) {
			scale_px = context.getResources().getDisplayMetrics().density;
		}
		return (int) (dpValue * scale_px + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param context
	 * @param pxValue
	 *            设置的px值
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		if (null == metrics) {
			metrics = context.getResources().getDisplayMetrics();
		}
		scale_dp = metrics.density;
		return (int) (pxValue / scale_dp + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param context
	 * @param spValue
	 *            文字大小
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		float scale_sp = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * scale_sp + 0.5f);
	}

	/**
	 * 计算view的实际高
	 * 
	 * @param picheight
	 *            作图的高
	 * @param screenheight
	 *            屏幕的高
	 * @return
	 */
	public static int getViewheight(int picheight, int screenheight) {
		int viewWIdth = picheight * screenheight / 1280;
		return viewWIdth;
	}

	/**
	 * 计算view的实际宽
	 * 
	 * @param picwidth
	 *            作图的宽
	 * @param screenwidth
	 *            屏幕的宽
	 * @return
	 */
	public static int getViewWidth(int picwidth, int screenwidth) {
		int viewWIdth = picwidth * screenwidth / 720;
		return viewWIdth;
	}

	/**
	 * 得到屏幕的height
	 * 
	 */
	public static int getWindowHeight(Activity activities) {
		if (null == metrics) {
			metrics = new DisplayMetrics();
		}
		activities.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}

}
