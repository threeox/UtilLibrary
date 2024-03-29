package com.threeox.utillibrary.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

/** 
 *
 * @ClassName: DateUtil
 *
 * @Description: Todo()
 *
 * @author 赵屈犇
 *
 * @date 创建时间:2017/7/29 下午8:19
 * 
 * @version 1.0
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

	public final static String FORMAT_YEAR = "yyyy";
	public final static String FORMAT_MONTH_DAY = "MM月dd日";

	public final static String FORMAT_DATE = "yyyy-MM-dd";
	public final static String FORMAT_TIME = "HH:mm";
	public final static String FORMAT_TIME_SECOND = "HH:mm:ss";
	public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日  hh:mm";

	public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
	public final static String FORMAT_DATE1_TIME = "yyyy/MM/dd HH:mm";
	public final static String FORMAT_DATE_TIME_SECOND = "yyyy-MM-dd HH:mm:ss";
	public final static String FORMAT_DATE_TIME_SECOND_MS = "yyyy-MM-dd HH:mm:ss:SSS";

	private static SimpleDateFormat sdf = new SimpleDateFormat();
	/**
	 * 年
	 */
	private static final int YEAR = 365 * 24 * 60 * 60;
	/**
	 * 月
	 */
	private static final int MONTH = 30 * 24 * 60 * 60;
	/**
	 * 天
	 */
	private static final int DAY = 24 * 60 * 60;
	/**
	 * 小时
	 */
	private static final int HOUR = 60 * 60;
	/**
	 * 分钟
	 */
	private static final int MINUTE = 60;

	/**
	 * 根据时间戳获取描述性时间，如3分钟前，1天前
	 * 
	 * @param timestamp
	 *            时间戳 单位为毫秒
	 * @return 时间字符串
	 */
	public static String getDescriptionTimeFromTimestamp(long timestamp) {
		long currentTime = System.currentTimeMillis();
		// 与现在时间相差秒数
		long timeGap = (currentTime - timestamp) / 1000;
		System.out.println("timeGap: " + timeGap);
		String timeStr = null;
		if (timeGap > YEAR) {
			timeStr = timeGap / YEAR + "年前";
		} else if (timeGap > MONTH) {
			timeStr = timeGap / MONTH + "个月前";
			// 1天以上
		} else if (timeGap > DAY) {
			timeStr = timeGap / DAY + "天前";
			// 1小时-24小时
		} else if (timeGap > HOUR) {
			timeStr = timeGap / HOUR + "小时前";
			// 1分钟-59分钟
		} else if (timeGap > MINUTE) {
			timeStr = timeGap / MINUTE + "分钟前";
			// 1秒钟-59秒钟
		} else {
			timeStr = "刚刚";
		}
		return timeStr;
	}

	public static long dateToLong(String date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
			return c.getTimeInMillis();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 1L;
	}

	/**
	 * 毫秒转化时分秒毫秒
	 *
	 * @param ms
	 * @return
	 */
	public static String formatTime(Long ms) {
		Integer ss = 1000;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;
		Long day = ms / dd;
		Long hour = (ms - day * dd) / hh;
		Long minute = (ms - day * dd - hour * hh) / mi;
		StringBuffer sb = new StringBuffer();
		sb.append(day + "天" + hour + "时" + minute + "分");
		return sb.toString();
	}

	/**
	 * 获取当前日期的指定格式的字符串
	 * 
	 * @param format
	 *            指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
	 * @return
	 */
	public static String getCurrentTime(String format) {
		if (format == null || format.trim().equals("")) {
			sdf.applyPattern(FORMAT_DATE_TIME);
		} else {
			sdf.applyPattern(format);
		}
		return sdf.format(new Date());
	}

    /**
     * date类型转换为String类型
     * formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     * data Date类型的时间
     *
     * @param data
     * @param formatType
     * @return
     */
	public static String dateToString(Date data, String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}

    /**
     * long类型转换为String类型
     * currentTime要转换的long类型的时间
     * formatType要转换的string类型的时间格式
     *
     * @param currentTime
     * @param formatType
     * @return
     */
	public static String longToString(long currentTime, String formatType) {
		String strTime = "";
		// long类型转成Date类型
		Date date = longToDate(currentTime, formatType);
		// date类型转成String
		strTime = dateToString(date, formatType);
		return strTime;
	}

    /**
     * string类型转换为date类型
     * strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
     * HH时mm分ss秒,
     * strTime的时间格式必须要与formatType的时间格式相同
     *
     * @param strTime
     * @param formatType
     * @return
     */
	public static Date stringToDate(String strTime, String formatType) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		try {
			date = formatter.parse(strTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

    /**
     * long转换为Date类型
     * currentTime要转换的long类型的时间
     * formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     *
     * @param currentTime
     * @param formatType
     * @return
     */
	public static Date longToDate(long currentTime, String formatType) {
	    // 根据long类型的毫秒数生命一个date类型的时间
		Date dateOld = new Date(currentTime);
		// 把date类型的时间转换为string
		String sDateTime = dateToString(dateOld, formatType);
		// 把String类型转换为Date类型
		Date date = stringToDate(sDateTime, formatType);
		return date;
	}

    /**
     * string类型转换为long类型
     * strTime要转换的String类型的时间
     * formatType时间格式
     * strTime的时间格式和formatType的时间格式必须相同
     *
     * @param strTime
     * @param formatType
     * @return
     */
	public static long stringToLong(String strTime, String formatType) {
	    // String类型转成date类型
		Date date = stringToDate(strTime, formatType);
		if (date == null) {
			return 0;
		} else {
		    // date类型转成long类型
			long currentTime = dateToLong(date);
			return currentTime;
		}
	}

    /**
     * date类型转换为long类型
     * date要转换的date类型的时间
     * @param date
     * @return
     */
	public static long dateToLong(Date date) {
		return date.getTime();
	}

	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
		return format.format(new Date(time));
	}

	public static String getHourAndMin(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(new Date(time));
	}

	public static String dayToLong(String days) {
		String[] day = days.split("/");
		int d = Integer.parseInt(day[0]) * 24;
		int h = d + Integer.parseInt(day[1]) * 60;
		int m = h + Integer.parseInt(day[2]) * 60;
		return String.valueOf(m * 1000);
	}

    /**
     * 获取聊天时间：因为sdk的时间默认到秒故应该乘1000
     *
     * @param timesamp
     * @param isSdk
     * @return
     */
	public static String getChatTime(long timesamp, boolean isSdk) {
		long clearTime = timesamp;
		if (isSdk) {
			clearTime = timesamp * 1000;
		}
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date today = new Date(System.currentTimeMillis());
		Date otherDay = new Date(clearTime);
		int temp = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));
		switch (temp) {
		case 0:
			result = "今天 " + getHourAndMin(clearTime);
			break;
		case 1:
			result = "昨天 " + getHourAndMin(clearTime);
			break;
		case 2:
			result = "前天 " + getHourAndMin(clearTime);
			break;
		default:
			result = getTime(clearTime);
			break;
		}

		return result;
	}

	/**
	 * 格式化时间
	 * 
	 * @param format
	 * @return
	 */
	public static String format(String format) {
		return format(new Date(), format);
	}

	/**
	 * 格式化时间
	 * 
	 * @param format
	 * @return
	 */
	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
}