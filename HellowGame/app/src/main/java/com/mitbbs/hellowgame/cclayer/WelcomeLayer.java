package com.mitbbs.hellowgame.cclayer;

import android.os.AsyncTask;
import android.view.MotionEvent;

import com.mitbbs.hellowgame.base.BaseLayer;
import com.mitbbs.hellowgame.util.Utils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.instant.CCShow;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

/**
 * Created by jc on 2017/3/30.
 */
public class WelcomeLayer extends BaseLayer {

    private  CCSprite logo;
    private CCSprite welcome;
    private CCSprite star;

    public WelcomeLayer() {
        logo = CCSprite.sprite("image/popcap_logo.png");
        logo.setPosition(winSize.width / 2, winSize.height / 2);
        this.addChild(logo);
        CCHide hide = CCHide.action();
        CCDelayTime delayTime = CCDelayTime.action(1);//延时一秒
        CCShow show = CCShow.action();
        CCSequence sequence = CCSequence.actions(hide,delayTime,show,delayTime,hide,
                delayTime, CCCallFunc.action(this,"showWelcome"));
        logo.runAction(sequence);

        //初始化开始按钮
        star = CCSprite.sprite("image/loading/loading_start.png");
        star.setPosition(winSize.width/2,45);
        star.setVisible(false);//隐藏按钮
        this.addChild(star,1);

        // 异步在后台初始化数据
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                star.setVisible(true);// 显示点击开始的按钮
                setIsTouchEnabled(true);// 打开点击事件
            }
        }.execute();
    }

    public void showWelcome(){
        logo.removeSelf();

        welcome = CCSprite.sprite("image/welcome.jpg");
        welcome.setAnchorPoint(0,0);
        this.addChild(welcome);

        //初始化加载中的图片
        CCSprite loading = CCSprite.sprite("image/loading/loading_01.png");
        loading.setPosition(winSize.width/2,45);
        this.addChild(loading);


        CCAction animate = Utils.animate(
                "image/loading/loading_%02d.png", 9, false);
        loading.runAction(animate);
    }

    //点击开始按钮，切换图层
    @Override
    public boolean ccTouchesBegan(MotionEvent event) {
        CGPoint convertTouchToNodeSpace = convertTouchToNodeSpace(event);
        if (CGRect.containsPoint(star.getBoundingBox(),convertTouchToNodeSpace)){
            Utils.changeLayer(new MenuLayer());
        }
        return super.ccTouchesBegan(event);
    }
}
