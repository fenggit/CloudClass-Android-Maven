package com.cloudclass.demo

import android.app.Application
//import io.agora.agoraeducore.core.internal.launch.AgoraSDKInitUtils

/**
 * author : felix
 * date : 2023/9/11
 * description :
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // 2.8.70 开始
        // AgoraSDKInitUtils.initSDK(this)
    }
}