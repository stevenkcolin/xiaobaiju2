package com.stevenkcolin.xiaobaiju.util;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import java.io.IOException;
import java.util.List;

/**
 * Created by Pengfei on 2015/12/28.
 */
public class GeoUtil {

    private static Geocoder geocoder;

    /**
     * 获取所在城市
     * @param context
     * @return String - city name
     * @throws IOException
     */
    public static String getCity(Context context) throws IOException {
        String cityName = "";

        Double[] coordinate = getCoordinate(context);
        double lat = 0;
        double lng = 0;
        List<Address> addList = null;
        if (coordinate != null) {
            lat = coordinate[0];
            lng = coordinate[1];
        }
        try {
            addList = geocoder.getFromLocation(lat, lng, 1);    //解析经纬度
        } catch (IOException e) {
            Log.e("GeoUtil.class", e.getMessage(), e);
            throw e;
        }
        if (addList != null && addList.size() > 0) {
            for (int i = 0; i < addList.size(); i++) {
                Address add = addList.get(i);
                cityName += add.getLocality();
            }
        }
        if(cityName.length() != 0){
            return cityName.substring(0, (cityName.length() - 1));
        } else {
            return cityName;
        }
    }

    /**
     * 获取经纬度
     * @return array of string. {latitude, longitude}
     */
    public static Double[] getCoordinate(Context context){
        Location location = getLocationObj(context);
        if (location == null) {
            Log.e("GeoUtil.class", "Cannot get location");
            return null;
        }
        return new Double[]{location.getLatitude(), location.getLongitude()};
    }

    private static Location getLocationObj(Context context) throws SecurityException{

        try {
            geocoder = new Geocoder(context);
            //用于获取Location对象，以及其他
            LocationManager locationManager;
            String serviceName = Context.LOCATION_SERVICE;
            //实例化一个LocationManager对象
            locationManager = (LocationManager) context.getSystemService(serviceName);
            //provider的类型
            String provider = LocationManager.NETWORK_PROVIDER;

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);   //高精度
            criteria.setAltitudeRequired(false);    //不要求海拔
            criteria.setBearingRequired(false); //不要求方位
            criteria.setCostAllowed(false); //不允许有话费
            criteria.setPowerRequirement(Criteria.POWER_LOW);   //低功耗

            //通过最后一次的地理位置来获得Location对象
            Location location = locationManager.getLastKnownLocation(provider);
            return location;
        } catch (SecurityException e) {
            Log.e("GeoUtil.class", e.getMessage(), e);
            throw e;
        }
    }
}
