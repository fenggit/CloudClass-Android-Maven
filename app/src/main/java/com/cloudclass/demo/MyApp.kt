package com.cloudclass.demo

import android.app.Application

/**
 * author : felix
 * date : 2023/9/11
 * description :
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        //AgoraSDKInitUtils.initSDK(this)
    }
}