package com.kangning.myapplication

import android.content.Context
import android.content.Intent


object NavigationUtils {


    data class MapPackageModel(val map: String, val packageName: String)

    const val NAVIGATION_AMAP = "高德地图"
    const val NAVIGATION_TECENT = "腾讯地图"
    const val NAVIGATION_BAIDU = "百度地图"

    const val PACKAGE_AMAP = "com.autonavi.minimap"
    const val PACKAGE_TECENT = "com.tencent.map"
    const val PACKAGE_BAIDU = "com.baidu.BaiduMap"

    val mapTypeList = listOf(NAVIGATION_AMAP, NAVIGATION_BAIDU, NAVIGATION_TECENT)
    val mapPackageList = listOf(
            MapPackageModel(NAVIGATION_AMAP, PACKAGE_AMAP),
            MapPackageModel(NAVIGATION_TECENT, PACKAGE_TECENT),
            MapPackageModel(NAVIGATION_BAIDU, PACKAGE_BAIDU)
    )


    fun goToNaviActivity(mapType: String,
                         lat: Double,
                         lon: Double,
                         context: Context): Boolean {
        //启动路径规划页面

        if (!checkMapIsInstalled(context, mapType)) return false

        val naviIntent: Intent
        when (mapType) {
            NAVIGATION_AMAP -> {
                val uri = "androidamap://route/plan/?dlat=$lat&dlon=$lon"
                naviIntent = Intent("android.intent.action.VIEW", android.net.Uri.parse(uri))
                naviIntent.`package` = "com.autonavi.minimap"
                context.startActivity(naviIntent)
            }

            NAVIGATION_TECENT -> {
                naviIntent = Intent("android.intent.action.VIEW",
                        android.net.Uri.parse("qqmap://map/routeplan?type=drive&tocoord=" + lat + "," + lon + "&policy=0&referer=" + context.packageName))

                context.startActivity(naviIntent)
            }

            NAVIGATION_BAIDU -> {
                val coords = gcj02_To_Bd09(lat, lon)
                //启动路径规划页面
                naviIntent = Intent("android.intent.action.VIEW", android.net.Uri.parse("baidumap://map/navi?location=${coords[0]},${coords[1]}"))
                context.startActivity(naviIntent)
            }
            else -> {
                return false
            }
        }
        return true

    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     *
     * @param lat
     * @param lon
     */
    private fun gcj02_To_Bd09(lat: Double, lon: Double): DoubleArray {

        val pi = 3.141592653589793 * 3000.0 / 180.0
        val z = Math.sqrt(lon * lon + lat * lat) + 0.00002 * Math.sin(lat * pi)
        val theta = Math.atan2(lat, lon) + 0.000003 * Math.cos(lon * pi)
        val tempLat = z * Math.sin(theta) + 0.006
        val tempLon = z * Math.cos(theta) + 0.0065
        return doubleArrayOf(tempLat, tempLon)
    }

    fun installedMap(context: Context): List<String> {
        return mapTypeList.filter {
            checkMapIsInstalled(context, it)
        }
    }

    private fun checkMapIsInstalled(context: Context, mapType: String): Boolean {
        mapPackageList.filter { mapType == it.map }.forEach {
            if (checkPackageInstalled(it.packageName, context))
                return true
        }
        return false
    }

    private fun checkPackageInstalled(packageName: String, context: Context): Boolean {
        val packageManager = context.packageManager
        val packageInfo = packageManager.getInstalledPackages(0)
        if (packageInfo != null) {
            for (info in packageInfo!!) {
                if (info.packageName == packageName) {
                    return true
                }
            }
        }
        return false
    }

}
