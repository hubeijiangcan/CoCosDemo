package com.mitbbs.hellowgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mitbbs.hellowgame.cclayer.FightLayer;
import com.mitbbs.hellowgame.cclayer.WelcomeLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends AppCompatActivity {

    private CCDirector director;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建一个surfaceview 相当于导演前面的小屏幕
        CCGLSurfaceView view = new CCGLSurfaceView(this);
        setContentView(view);

        director = CCDirector.sharedDirector();
        director.attachInView(view);//开启绘制线程

        director.setDisplayFPS(true);//显示帧率
        // 设置强制横屏显示
        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
        // 用于屏幕适配, 会基于不同大小的屏幕等比例缩放,设置我们开发时候的分辨率
        director.setScreenSize(480,320);

        CCScene ccScene = CCScene.node();//创建一个场景对象
        //创建图层
//        WelcomeLayer layer = new WelcomeLayer();
        FightLayer layer = new FightLayer();
        //给场景添加图层
        ccScene.addChild(layer);
        director.runWithScene(ccScene); // 导演运行场景

    }

    @Override
    protected void onResume() {
        super.onResume();
        director.resume();// 游戏继续
    }

    @Override
    protected void onPause() {
        super.onPause();
        director.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        director.end();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }
}
