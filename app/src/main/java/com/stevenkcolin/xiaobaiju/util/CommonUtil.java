package com.stevenkcolin.xiaobaiju.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by linchen on 1/19/16.
 */
public class CommonUtil {
    /**
     * 获取版本名称
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 设备信息工具类
     */
    public static final class DeviceInfoUtility {
        /**
         * method desc：获取设备网卡地址
         *
         * @param context
         * @return
         */
        public static String getMac(Context context) {
            WifiManager wifi = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        }

        /**
         * 获取手机IMEI
         *
         * @param context
         * @return
         */
        public static String getIMEI(Context context) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            return imei;
        }


        /**
         * 获取Pseudo-Unique ID
         *
         * @return
         */
        public static String getPUID() {
            return formatString("35", Build.BOARD.length() % 10, Build.BRAND.length() % 10,
                    Build.CPU_ABI.length() % 10,
                    Build.DEVICE.length() % 10,
                    Build.DISPLAY.length() % 10,
                    Build.HOST.length() % 10,
                    Build.ID.length() % 10,
                    Build.MANUFACTURER.length() % 10,
                    Build.MODEL.length() % 10,
                    Build.PRODUCT.length() % 10,
                    Build.TAGS.length() % 10,
                    Build.TYPE.length() % 10,
                    Build.USER.length() % 10);
        }

        /**
         * 获取Android id
         *
         * @param context
         * @return
         */
        public static String getAndroidId(Context context) {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        /**
         * 获取蓝牙ID
         *
         * @return
         */
//        public static String getBTMac() {
//            BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();// Local Bluetooth adapter
//            if (m_BluetoothAdapter != null) {
//                return m_BluetoothAdapter.getAddress();
//            }
//            return "";
//        }

        /**
         * 获取唯一ID
         *
         * @param context
         * @return
         */
        public static String getOnlyID(Context context) {
            String UUID = SharedPreferencesUtility.getString(context, "UUID", null);
            if (UUID != null) {
                return UUID;
            }
            String ONLYID = SharedPreferencesUtility.getString(context, "ONLYID", null);
            if (ONLYID == null) {
                ONLYID = formatString(getIMEI(context), getPUID(), getAndroidId(context), getMac(context)
//                        , getBTMac()
                );
                SharedPreferencesUtility.put(context, "ONLYID", ONLYID);
            }
            return getMD5Str(ONLYID);
        }


        /**
         * method desc：获取设备ip地址
         *
         * @return
         */
        public static String getLocalHostIp() {

            try {
                Enumeration<NetworkInterface> en = NetworkInterface
                        .getNetworkInterfaces();
                // 遍历所用的网络接口
                while (en.hasMoreElements()) {
                    NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
                    Enumeration<InetAddress> inet = nif.getInetAddresses();
                    // 遍历每一个接口绑定的所有ip
                    while (inet.hasMoreElements()) {
                        InetAddress ip = inet.nextElement();
                        if (!ip.isLoopbackAddress()
                                && ip instanceof Inet4Address
                              ) {
                            return ip.getHostAddress();
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }
            return "";
        }

        public static String getDeviceId(Context context) {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        }

        /**
         * 判断计步器是否能使用4.4的计步器API
         *
         * @param ctx
         * @return
         */
        public static final boolean isCanUseStepSensor(Context ctx) {
//            int sdkInt = Build.VERSION.SDK_INT;
//            PackageManager packageManager = ctx.getPackageManager();
//            boolean isStepCounter = packageManager.hasSystemFeature("android.hardware.sensor.stepcounter");
//            boolean isStepDetector = packageManager.hasSystemFeature("android.hardware.sensor.stepdetector");
//            DebugLog.e("step", "isStepCounter:" + isStepCounter + ";isStepDetector:" + isStepDetector);
//            if (sdkInt >= 19 && isStepCounter && isStepDetector) {
//                return true;
//            }
            return false;
        }

        /**
         * 判断是否挂在sdcard
         *
         * @return
         */
        public static final boolean isMountSdcard() {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }
    }


    /**
     * 将多个对象拼接成字符串
     *
     * @param object
     * @return
     */
    public static String formatString(Object... object) {
        StringBuilder builder = new StringBuilder();
        for (Object o : object) {
            if (!Utility.isNull(o)) {
                builder.append(o);
            }
        }
        return builder.toString();
    }

    /**
     * MD5加密
     *
     * @param
     * @return
     */
    private static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        //32位加密
//        return md5StrBuff.toString().toLowerCase();
        //16位加密，从第9位到25位
        return md5StrBuff.substring(8, 24).toString().toLowerCase();
    }


    public static class SharedPreferencesUtility {

        private static SharedPreferences mSharedPreferences = null;

        public static SharedPreferences getSharedPreferences(Context context) {
            if (Utility.isNull(mSharedPreferences)) {
                if (isPatientClient(context)) {
                    mSharedPreferences = context.getSharedPreferences(GeneralConstant.SHARE_PREF, 0);
                } else {
                    mSharedPreferences = context.getSharedPreferences(GeneralConstant.SHARE_PREF_DOCTOR, 0);
                }
            }
            return mSharedPreferences;
        }

        public static SharedPreferences getSharedPreferences(Context context, String prefName) {
            return context.getSharedPreferences(prefName, 0);
        }

        /**
         * 通过PrefName放置相关的数据
         *
         * @param context
         * @param prefName
         * @param key
         * @param value
         */
        public static void put(Context context, String prefName, String key, Object value) {
            if (value != null) {
                if (value instanceof String) {
                    getSharedPreferences(context, prefName).edit().putString(key, value.toString())
                            .commit();
                } else if (value instanceof Integer) {
                    getSharedPreferences(context, prefName).edit()
                            .putInt(key, Integer.parseInt(value.toString()))
                            .commit();
                } else if (value instanceof Long) {
                    getSharedPreferences(context, prefName).edit()
                            .putLong(key, Long.parseLong(value.toString()))
                            .commit();
                } else if (value instanceof Boolean) {
                    getSharedPreferences(context, prefName).edit().putBoolean(key, Boolean.parseBoolean(value.toString())).commit();
                }
            } else {
                getSharedPreferences(context, prefName).edit().putString(key, null).commit();
            }
        }

        /**
         * @param key
         * @param value
         */
        public static void put(Context context, String key, Object value) {

            if (value != null) {
                if (value instanceof String) {
                    getSharedPreferences(context).edit().putString(key, value.toString())
                            .commit();
                } else if (value instanceof Integer) {
                    getSharedPreferences(context).edit()
                            .putInt(key, Integer.parseInt(value.toString()))
                            .commit();
                } else if (value instanceof Long) {
                    getSharedPreferences(context).edit()
                            .putLong(key, Long.parseLong(value.toString()))
                            .commit();
                } else if (value instanceof Boolean) {
                    getSharedPreferences(context).edit().putBoolean(key, Boolean.parseBoolean(value.toString())).commit();
                } else {
                    getSharedPreferences(context).edit().putString(key, value.toString())
                            .commit();
                }
            } else {
                getSharedPreferences(context).edit().putString(key, null).commit();
            }
        }

        public static String getString(Context context, String prefName, String key, String defaultValue) {
            return getSharedPreferences(context, prefName).getString(key, defaultValue);
        }

        public static int getInt(Context context, String prefName, String key, int defaultValue) {
            return getSharedPreferences(context, prefName).getInt(key, defaultValue);
        }

        public static long getLong(Context context, String prefName, String key, long defaultValue) {
            return getSharedPreferences(context, prefName).getLong(key, defaultValue);
        }

        public static boolean getBoolean(Context context, String prefName, String key, boolean defaultValue) {
            return getSharedPreferences(context, prefName).getBoolean(key, defaultValue);
        }

        public static boolean contains(Context context, String prefName, String key) {
            return getSharedPreferences(context, prefName).contains(key);
        }

        public static void remove(Context context, String prefName, String key) {
            getSharedPreferences(context, prefName).edit().remove(key).commit();
        }

        public static String getString(Context context, String key, String defaultValue) {
            return getSharedPreferences(context).getString(key, defaultValue);
        }

        public static int getInt(Context context, String key, int defaultValue) {
            return getSharedPreferences(context).getInt(key, defaultValue);
        }

        public static long getLong(Context context, String key, long defaultValue) {
            return getSharedPreferences(context).getLong(key, defaultValue);
        }

        public static boolean getBoolean(Context context, String key, boolean defaultValue) {

            return getSharedPreferences(context).getBoolean(key, defaultValue);
        }

        public static boolean contains(Context context, String key) {
            return getSharedPreferences(context).contains(key);
        }

        public static void remove(Context context, String key) {
            getSharedPreferences(context).edit().remove(key).commit();
        }

        public static void clear(Context context) {
            getSharedPreferences(context).edit().clear().commit();
        }

        public static void clear(Context context, String prefName) {
            getSharedPreferences(context, prefName).edit().clear().commit();
        }

        public SharedPreferences getSharedPreference() {
            return mSharedPreferences;
        }
    }


    /**
     * 普通操作工具类
     */
    public static final class Utility {


        /**
         * method desc：判断参数值是否为空 null，空字符串，或者全部空格字符串或者"null"字符串都视为空
         *
         * @param o
         * @return
         */
        public static boolean isNull(Object o) {
            try {
                return null == o || "".equals(o.toString().replaceAll(" ", ""))
                        || "null".equals(o.toString());
            } catch (Exception e) {
            }
            return true;
        }

        public static void putAll(List<JSONObject> list, JSONArray src) {
            try {
                JSONObject object = null;
                for (int i = 0; i < src.length(); i++) {
                    object = src.optJSONObject(i);
                    list.add(object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 验证邮箱地址是否正确
         *
         * @param email
         * @return
         */
        public static boolean checkEmail(String email) {
            boolean flag = false;
            try {
                String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            } catch (Exception e) {
                flag = false;
            }

            return flag;
        }

        /**
         * 验证手机号码
         *
         * @param mobiles
         * @return [0-9]{5,9}
         */
        public static boolean isMobileNO(String mobiles) {
            boolean flag = false;
            try {
                Pattern p = Pattern
                        .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
                Matcher m = p.matcher(mobiles);
                flag = m.matches();
            } catch (Exception e) {
                flag = false;
            }
            return flag;
        }

        /**
         * 判断是否是手机号码格式（只判断是否是11位纯数字）
         *
         * @param mobiles
         * @return
         */
        public static boolean isMobile(String mobiles) {

            Pattern p = Pattern.compile("^(([1-9][0-9][0-9]))\\d{8}$");

            Matcher m = p.matcher(mobiles);

            System.out.println(m.matches() + "---");

            return m.matches();

        }

        /**
         * method desc：验证是否为数字
         *
         * @param number
         * @return
         */
        public static boolean isNum(String number) {
            boolean flag = false;
            try {
                Pattern p = Pattern.compile("^[0-9]{5}$");
                Matcher m = p.matcher(number);
                flag = m.matches();
            } catch (Exception e) {
                flag = false;
            }
            return flag;
        }

        /**
         * method desc： 判断密码是否有至少一位数字和字母
         *
         * @param password
         * @return
         */
        public static boolean isAlphanumerics(String password) {
            boolean flag = false;
            try {
                Pattern p = Pattern
                        .compile(".*[A-Za-z].*[0-9]|.*[0-9].*[A-Za-z]");
                Matcher m = p.matcher(password);
                flag = m.matches();
            } catch (Exception e) {
                flag = false;
            }
            return flag;
        }

        /**
         * 对double进行处理
         *
         * @param f      需要处理的数据
         * @param length 保留的小数位数
         * @return
         */
        public static double formatDouble(double f, int length) {
            BigDecimal bigDecimal = new BigDecimal(f);
            return bigDecimal.setScale(length, BigDecimal.ROUND_HALF_UP)
                    .doubleValue();
        }

        public static double formatStr2Double(String str, int length) {
            return formatDouble(Double.parseDouble(str), length);
        }

        public static String formatStr2Num(String str, int length) {
            if (!Utility.isNull(str)) {
                return formatDouble(Double.parseDouble(str), length) + "";
            } else {
                return null;
            }
        }

        public static String formatDouble2String(double f, int length) {
            return formatDouble(f, length) + "";
        }

        public static String formatDouble2String(float f, int length) {
            return formatDouble((double) f, length) + "";
        }

        public static String formatDouble2String(double f) {
            if (f % 1.00 == 0) {
                return CommonUtil.formatString((int) f);
            }
            if (f % 0.1 == 0) {
                return formatDouble2String(f, 1);
            }
            return formatDouble2String(f, 2);
        }


        public static int getRandomNum() {
            return (int) (Math.random() * (10000 - 100) + 1);
        }


        public static String encode(String param) {
            try {
                return URLEncoder.encode(param, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public static String decode(String param) {
            try {
                return URLEncoder.encode(param, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public static Type getType(Class clazz, Class targetClass) {
            Class superClass = clazz.getSuperclass();
            if (superClass.getSimpleName().equals(targetClass.getSimpleName())) {
                return clazz.getGenericSuperclass();
            }
            return getType(superClass, targetClass);
        }

        public static Field[] getAllField(Class<?> clazz) {
            List<Field> fieldList = new ArrayList<Field>();
            Field[] dFields = clazz.getDeclaredFields();
            if (null != dFields && dFields.length > 0) {
                fieldList.addAll(Arrays.asList(dFields));
            }

            Class<?> superClass = clazz.getSuperclass();
            if (superClass != Object.class) {
                Field[] superFields = getAllField(superClass);
                if (null != superFields && superFields.length > 0) {
                    for (Field field : superFields) {
                        if (!isContain(fieldList, field)) {
                            fieldList.add(field);
                        }
                    }
                }
            }
            Field[] result = new Field[fieldList.size()];
            fieldList.toArray(result);
            return result;
        }

        /**
         * 检测Field List中是否已经包含了目标field
         *
         * @param fieldList
         * @param field     带检测field
         * @return
         */
        public static boolean isContain(List<Field> fieldList, Field field) {
            for (Field temp : fieldList) {
                if (temp.getName().equals(field.getName())) {
                    return true;
                }
            }
            return false;
        }
    }


    /**
     * 判断是否是患者端
     *
     * @param context
     * @return
     */
    public static boolean isPatientClient(Context context) {
        if (context.getPackageName().equals("com.byb.patient")) {
            return true;
        }
        return false;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取系统版本
     *
     * @return
     */
    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 网络类型
     */
    public static final class NetTypeUtility {

        /**
         * 获取网络类型
         *
         * @param context
         * @return
         */

        public static String getNetType(Context context) {
            ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {//判断网络是否是WiFi
                return "wifi";
            } else if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {//判断是否是手机网络
                switch (networkInfo.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return "2G";
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return "3G";
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return "4G";
                    default:
                        return "UN_NETWORK";
                }
            }
            return null;
        }

        /**
         * 获取网络服务商
         *
         * @param context
         * @return
         */
        public static String getCarrier(Context context) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String iNumeric = telephonyManager.getSimOperator();
            if (iNumeric.length() == 0) {
                return null;
            }
            if (iNumeric.equals("46000") || iNumeric.equals("46002")) {
                return "CMCC";
            } else if (iNumeric.equals("46001")) {
                return "CUCC";
            } else if (iNumeric.equals("46003")) {
                return "CTCC";
            }
            return null;
        }

        /**
         * 判断是否是WiFi
         *
         * @param context
         * @return
         */
        public static boolean isHasWiFi(Context context) {
            ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {//判断网络是否是WiFi
                return true;
            }
            return false;
        }
    }


    /**
     * 获取app渠道id
     *
     * @param context
     * @return
     */
    public static String getChannelId(Context context) {
        ApplicationInfo info;
        try {
            info = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            String channelId = info.metaData.get("UMENG_CHANNEL") + "";
            return channelId;
        } catch (Exception e) {
            return null;
        }
    }

}
