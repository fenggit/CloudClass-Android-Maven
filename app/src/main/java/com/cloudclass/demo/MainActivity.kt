package com.cloudclass.demo

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cloudclass.demo.test.rtmtoken.RtmTokenBuilder
import com.cloudclass.demo.utils.AppUtil
import com.cloudclass.demo.utils.HashUtil
import io.agora.agoraeducore.core.context.EduContextVideoEncoderConfig
import io.agora.agoraeducore.core.internal.framework.proxy.RoomType
import io.agora.agoraeducore.core.internal.launch.*
import io.agora.classroom.helper.FcrStreamParameters
import io.agora.classroom.sdk.AgoraClassSdkConfig
import io.agora.classroom.sdk.AgoraClassroomSDK


class MainActivity : AppCompatActivity() {
    var REQUEST_CODE_RTC = 101
    var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_start_class_room).setOnClickListener {
            showLoadingDialog()
            start()
        }
    }

    fun start() {
        if (AppUtil.checkAndRequestAppPermission(this, arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA), REQUEST_CODE_RTC)) {
            startClassRoom()
        } else {
            Toast.makeText(this, "No enough permissions (Camera & RECORD_AUDIO)", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * TODO 这里所有的参数需要从自己的服务器获取，这是只是为了测试
     */
    fun startClassRoom() {
        val userName = "zhangsanXXQQQ"
        val roomName = "MyRoom124"
        val roomType = RoomType.SMALL_CLASS.value // 班型： 0 一对一 2大班课 4小班课
        val roleType = AgoraEduRoleType.AgoraEduRoleTypeStudent.value // 角色：1:老师角色 2:学生角色
        val roomUuid = HashUtil.md5(roomName).plus(roomType).lowercase()
        val userUuid = HashUtil.md5(userName).plus(roleType).lowercase()
        val roomRegion = AgoraEduRegion.cn  // 区域
        val duration = 1800L // 课程时长

        /*填入你的App ID，TODO 这里是测试的ID*/
        val appId = "47b7535dcb9a4bb4aa592115266eae98"
        val appCert = "6d9a4e2ca6ee4a33a2c57364ed712dac"
        /*填入你的RTM Token, TODO 这里是测试的Token*/
        val rtmToken = RtmTokenBuilder().buildToken(appId, appCert, userUuid, RtmTokenBuilder.Role.Rtm_User, 0)

        val config = AgoraEduLaunchConfig(
            userName,
            userUuid,
            roomName,
            roomUuid,
            roleType,
            roomType,
            rtmToken,
            null,
            duration
        )

        config.appId = appId
        config.region = roomRegion
        config.videoEncoderConfig = EduContextVideoEncoderConfig(
            FcrStreamParameters.HeightStream.width,
            FcrStreamParameters.HeightStream.height,
            FcrStreamParameters.HeightStream.frameRate,
            FcrStreamParameters.HeightStream.bitRate
        )

        AgoraClassroomSDK.setConfig(AgoraClassSdkConfig(appId))
        AgoraClassroomSDK.launch(this, config, AgoraEduLaunchCallback { event ->
            Log.e("agora", ":launch-课堂状态:" + event.name)
            dismissLoadingDialog()
        })

        // 通用场景
//        AgoraOnlineClassroomSDK.setConfig(AgoraOnlineClassSdkConfig(appId))
//        AgoraOnlineClassroomSDK.launch(this, config, AgoraEduLaunchCallback { event ->
//            Log.e("agora", ":launch-课堂状态:" + event.name)
//            dismissLoadingDialog()
//        })
    }

    fun showLoadingDialog() {
        alertDialog = AlertDialog.Builder(this).create()
        alertDialog?.window?.setBackgroundDrawable(ColorDrawable())
        alertDialog?.setCancelable(false)
        alertDialog?.setOnKeyListener { dialog, keyCode, event ->
            keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK
        }
        alertDialog?.show()
        alertDialog?.setContentView(R.layout.loading_alert)
        alertDialog?.setCanceledOnTouchOutside(false)
    }

    fun dismissLoadingDialog() {
        runOnUiThread {
            if (null != alertDialog && alertDialog?.isShowing == true) {
                alertDialog?.dismiss()
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "No enough permissions (Camera & RECORD_AUDIO)", Toast.LENGTH_SHORT).show()
                return
            }
        }
        when (requestCode) {
            REQUEST_CODE_RTC -> startClassRoom()
            else -> {
            }
        }
    }
}