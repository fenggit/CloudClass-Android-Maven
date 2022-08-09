package com.cloudclass.demo;

import android.content.Context;
import android.util.Log;

import com.cloudclass.demo.test.rtmtoken.RtmTokenBuilder;

import io.agora.agoraeducore.core.internal.framework.proxy.RoomType;
import io.agora.agoraeducore.core.internal.launch.AgoraEduEvent;
import io.agora.agoraeducore.core.internal.launch.AgoraEduLatencyLevel;
import io.agora.agoraeducore.core.internal.launch.AgoraEduLaunchCallback;
import io.agora.agoraeducore.core.internal.launch.AgoraEduLaunchConfig;
import io.agora.agoraeducore.core.internal.launch.AgoraEduRegion;
import io.agora.agoraeducore.core.internal.launch.AgoraEduRoleType;
import io.agora.agoraeducore.core.internal.launch.AgoraEduStreamState;
import io.agora.classroom.sdk.AgoraClassSdkConfig;
import io.agora.classroom.sdk.AgoraClassroomSDK;

/**
 * author : hefeng
 * date : 2022/6/23
 * description : java 调用
 */
public class LaunchForJava {
    String roomName = "myArtRoomClass";

    /**
     * 这里所有的参数需要从自己的服务器获取，这是只是为了测试
     */
    void startClassRoom(Context context) {
        String userUuid = "xiaoming2";
        /*填入你的App ID*/
        String appId = "47b7535dcb9a4bb4aa592115266eae98";
        String appCert = "6d9a4e2ca6ee4a33a2c57364ed712dac";
        String rtmToken = "";
        /*填入你的RTM Token*/
        try {
            rtmToken = new RtmTokenBuilder().buildToken(appId, appCert, userUuid, RtmTokenBuilder.Role.Rtm_User, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // 简单配置参数
        AgoraEduLaunchConfig config = new AgoraEduLaunchConfig(
                "xiaoming",
                userUuid,               // userUuid 最后一位数：2表示学生
                roomName,
                roomName + "4", // roomUuid最后一位数：0 一对一 2大班课 4小班课
                AgoraEduRoleType.AgoraEduRoleTypeStudent.getValue(),   // 角色：1:老师角色 2:学生角色
                RoomType.LARGE_CLASS.getValue(),  // 房间：0 一对一 2大班课 4小班课
                rtmToken,
                null,  // 上课开始时间
                1800L   // 课程时长
        );
        //config.setRegion(AgoraEduRegion.cn);

         // 复杂配置参数
//        AgoraEduLaunchConfig config = new AgoraEduLaunchConfig(
//                "xiaoming",
//                userUuid,               // userUuid 最后一位数：2表示学生
//                roomName,
//                roomName + "4", // roomUuid最后一位数：0 一对一 2大班课 4小班课
//                AgoraEduRoleType.AgoraEduRoleTypeStudent.getValue(),   // 角色：1:老师角色 2:学生角色
//                RoomType.LARGE_CLASS.getValue(),  // 房间：0 一对一 2大班课 4小班课
//                rtmToken,
//                null,     // 上课开始时间
//                1800L,     // 课程时长
//                AgoraEduRegion.cn, // 区域
//                null,
//                null,
//                new AgoraEduStreamState(1, 1), //用户上台默认是否发流 1:是 0:否
//                AgoraEduLatencyLevel.AgoraEduLatencyLevelUltraLow,   //默认延时等级
//                null,
//                null
//        );

        AgoraClassroomSDK.INSTANCE.setConfig(new AgoraClassSdkConfig(appId));
        AgoraClassroomSDK.INSTANCE.launch(context, config, new AgoraEduLaunchCallback() {
            @Override
            public void onCallback(AgoraEduEvent state) {
                Log.e("agora", ":launch-课堂状态:" + state);
            }
        });
    }
}
