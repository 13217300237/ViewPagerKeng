package study.hank.com.myapplication2.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;

/**
 * 与硬件设备相关的一些实用方法
 */
public class DeviceUtil {

    private static DeviceUtil singleton = null;

    private DeviceUtil() {
    }

    /**
     * 设备管理类对象
     *
     * @return
     */
    public synchronized static DeviceUtil instance() {
        if (null == singleton) {
            singleton = new DeviceUtil();
        }
        return singleton;
    }

    /**
     * 获取手机型号
     *
     * @return String
     */
    public String getDeviceModel() {
        if (null == Build.MODEL) {
            return "";
        }

        return Build.MODEL;
    }

    /**
     * 获取设备生产商
     *
     * @return String
     */
    public String getDeviceManufacturer() {
        if (null == Build.MANUFACTURER) {
            return "";
        }

        return Build.MANUFACTURER;
    }

    /**
     * 获取设备操作系统主版本号
     *
     * @return String
     */
    public String getOSMainVersion() {
        int version = VERSION.SDK_INT;

        switch (version) {
            case 1:
                return "1.0";
            case 2:
                return "1.1";
            case 3:
                return "1.5";
            case 4:
                return "1.6";
            case 5:
                return "2.0";
            case 6:
                return "2.0.1";
            case 7:
                return "2.1";
            case 8:
                return "2.2";
            case 9:
                return "2.3";
        }

        return VERSION.RELEASE;
    }

    /**
     * 屏幕dip值转换为像素值
     *
     * @param dipValue 屏幕dip值
     * @return int 屏幕像素值
     */
    public int dip2px(float dipValue, Context activity) {
        return (int) (dipValue * getScreenDensity(activity) + 0.5f);
    }

    /**
     * 屏幕像素值转换为dip值
     *
     * @param pxValue 屏幕像素值
     * @return int 屏幕dip值
     */
    public int px2dip(float pxValue, Context activity) {
        return (int) (pxValue / getScreenDensity(activity) + 0.5f);
    }

    /**
     * 获取屏幕宽度的像素值
     *
     * @return int
     */
    public int getScreenPixelsWidth(Context activity) {
        return activity.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度的像素值
     *
     * @return int
     */
    public int getScreenPixelsHeight(Context activity) {
        return activity.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕高度的设备独立像素值 Density-independent pixel (dp)
     *
     * @return int
     */
    public int getScreenDpHeight(Context activity) {
        float density = activity.getResources().getDisplayMetrics().density;
        int height = activity.getResources().getDisplayMetrics().heightPixels;
        return (int) Math.ceil((float) height / density);
    }

    /**
     * 获取屏幕宽度的设备独立像素值 Density-independent pixel (dp)
     *
     * @return int
     */
    public int getScreenDpWidth(Context activity) {
        float density = activity.getResources().getDisplayMetrics().density;
        int width = activity.getResources().getDisplayMetrics().widthPixels;
        return (int) Math.ceil((float) width / density);
    }

    /**
     * 获取通知栏像素值
     *
     * @return int
     */
    public int getDimenHeight(Activity activity) {
        Class<?> c;
        Object obj;
        Field field;
        int x = 0, sbar = 0;

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = activity.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
        }
        return sbar;
    }

    /**
     * 获取设备dip
     * <p>
     * 设备的独立像素，一个独立像素可能对应不同数量的实际像素值 这个值可能是浮点类型的
     *
     * @return float
     */
    public float getScreenDensity(Context activity) {
        try {
            return activity.getResources().getDisplayMetrics().density;
        } catch (Exception e) {
            return 1;
        }
    }

    public int getPixel(Resources res, int unit, float size) {
        DisplayMetrics metrics = res.getDisplayMetrics();
        return (int) TypedValue.applyDimension(unit, size, metrics);
    }

    /**
     * sp转成px
     *
     * @param res
     * @param spValue
     * @return
     */
    public int getPixelFromSP(Resources res, float spValue) {
        return getPixel(res, TypedValue.COMPLEX_UNIT_SP, spValue);
    }

    /**
     * 获取当前客户端的整数版本号
     *
     * @return int 当前客户端的整数版本号
     */
    public int appVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
        }

        return 0;
    }

    /**
     * 获取当前客户端的名称
     *
     * @return String
     */
    public String appName(Activity activity) {
        String name = null;
        try {
            name = activity.getString(activity.getApplicationInfo().labelRes);
        } catch (Exception e) {
        }

        if (null == name) {
            name = "";
        }

        return name;
    }

    /**
     * 获取当前应用包名
     *
     * @return String
     */
    public String packageName(Activity activity) {
        String name = null;
        try {
            name = activity.getPackageName();
        } catch (Exception e) {
        }

        if (null == name) {
            name = "";
        }

        return name;
    }

    /**
     * 获取当前客户端的字符串版本号
     *
     * @return String
     */
    public String appVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
        }

        return "";
    }

    /**
     * 判断是否有虚拟 NavigationBar
     *
     * @param activity
     * @return
     */
    public boolean checkDeviceHasNavigationBar(Context activity) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        } else {
        }
        return false;
    }

    /***
     * 获取底部导航栏的高度
     */
    public int getNavigationBarHeight(Activity context) {
        if (!isNavigationBarShow(context)) {
            return 0;
        }
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    private boolean isNavigationBarShow(Activity context) {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = context.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            return !menu && !back;
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 获取mac地址
     *
     * @return
     */
    public String getMacAddr(Context context) {
        String MacAddr = "";
        StringBuffer sb = new StringBuffer();
        try {
            NetworkInterface networkInterface = NetworkInterface.getByName("wlan0");
            if (null != networkInterface) {
                byte[] addres = networkInterface.getHardwareAddress();

                if (addres != null && addres.length > 1) {
                    for (int i = 0; i < addres.length; i++) {
                        if (i == addres.length - 1) {
                            sb.append(parseByte(addres[i]));
                        } else {
                            sb.append(parseByte(addres[i])).append(":");
                        }
                    }
                    MacAddr = sb.toString();
                }
            } else {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                MacAddr = wifiInfo.getMacAddress();
            }

        } catch (SocketException e) {
            e.printStackTrace();
            MacAddr = "";
        }
        return MacAddr == null ? "" : MacAddr;
    }

    private static String parseByte(byte b) {
        String s = "00" + Integer.toHexString(b);
        return s.substring(s.length() - 2);
    }

    public String GetNetIp() {
        URL infoUrl = null;
        InputStream inStream = null;
        String line = "";
//		try {
//			infoUrl = new URL("http://pv.sohu.com/cityjson?ie=utf-8 ");
//			URLConnection connection = infoUrl.openConnection();
//			HttpURLConnection httpConnection = (HttpURLConnection) connection;
//			int responseCode = httpConnection.getResponseCode();
//			if (responseCode == HttpURLConnection.HTTP_OK) {
//				inStream = httpConnection.getInputStream();
//				BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
//				StringBuilder strber = new StringBuilder();
//				while ((line = reader.readLine()) != null)
//					strber.append(line + "\n");
//				inStream.close();
//				// 从反馈的结果中提取出IP地址
//				Logger.e("GetNetIp strber = " + strber);
//				int start = strber.indexOf("{");
//				int end = strber.indexOf("}");
//				if(start >= 0 && end > 0){
//					String json = strber.substring(start, end + 1);
//					if (json != null && JsonUtil.isJsonData(json)) {
//						try {
//							JSONObject jsonObject = new JSONObject(json);
//							line = jsonObject.optString("cip");
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//
//					}
//				}
//				return line;
//			}
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        return line;
    }

    /**
     * 判断是否安装了某个应用程序
     *
     * @param context
     * @param packagename
     * @return
     */
    public boolean isAppInstalled(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            //System.out.println("没有安装");
            return false;
        } else {
            //System.out.println("已经安装");
            return true;
        }
    }

    /**
     * Description:从路径中获取文件名
     *
     * @param url
     * @return
     */
    public String getFileNameFromUrl(String url) {
        if (!TextUtils.isEmpty(url) && url.contains("/")) {
            return url.substring(url.lastIndexOf("/") + 1);
        }
        return null;
    }

    /**
     * 获取手机IMEI号
     */
    public String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();

        return imei;
    }

    /**
     * 判断电话号码是否符合格式.为了以防万一，1开头并是11位的都算手机号码
     *
     * @param inputText the input text
     * @return true, if is phone
     */
    public boolean isPhone(String inputText) {

        if (inputText.startsWith("1") && inputText.length() == 11) {
            return true;
        } else {
            return false;
        }
//		Pattern p = Pattern.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
//		Matcher m = p.matcher(inputText);
//		return m.matches();

    }
}
