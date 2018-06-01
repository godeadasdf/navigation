package com.kangning.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kangning.myapplication.NavigationUtils.NAVIGATION_AMAP
import com.kangning.myapplication.NavigationUtils.NAVIGATION_TECENT
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUtils.installedMap(applicationContext).forEach {
            Log.d("installedMap", it)
        }

        btn_click1.setOnClickListener {
            NavigationUtils.goToNaviActivity(NAVIGATION_AMAP,
                    "天安门",
                    39.9088600000, 116.3973900000, this)
        }

        btn_click2.setOnClickListener {
            NavigationUtils.goToNaviActivity(NAVIGATION_AMAP,
                    "天安门",
                    39.9088600000, 116.3973900000, this)
        }

        btn_click3.setOnClickListener {
            NavigationUtils.goToNaviActivity(NAVIGATION_AMAP,
                    "天安门",
                    39.9088600000, 116.3973900000, this)
        }


    }


}
