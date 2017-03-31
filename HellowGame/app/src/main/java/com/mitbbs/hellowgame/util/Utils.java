package com.mitbbs.hellowgame.util;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import java.util.ArrayList;
import java.util.HashMap;

/**工具类
 * Created jc jc on 2017/3/30.
 */
public class Utils {
    /**
     * 动画工具
     * @param format
     * @param size
     * @param repeat
     * @return
     */
    public static CCAction animate(String format, int size, boolean repeat){
        return animate(format,size,0.2f,repeat);
    }

    public static CCAction animate(String format, int size,float time, boolean repeat){
        ArrayList<CCSpriteFrame> frames = new ArrayList<CCSpriteFrame>();
        for (int i = 1; i <= size; i++) {
            frames.add(CCSprite.sprite(String.format(format, i))
                    .displayedFrame());
        }

        CCAnimation anim = CCAnimation.animation("loading",time,frames);

        if (!repeat){
            CCAnimate animate = CCAnimate.action(anim,false);
            return animate;
        }else {
            CCAnimate animate = CCAnimate.action(anim);
            CCRepeatForever forever = CCRepeatForever.action(animate);
            return forever;
        }


    }

    /**
     * 切换图层
     * @param layer
     */
    public static void changeLayer(CCLayer layer){
        CCScene ccScene = CCScene.node();
        ccScene.addChild(layer);
        // CCJumpZoomTransition transition = CCJumpZoomTransition.transition(2,
        // scene);// 切换效果

        CCFadeTransition transition = CCFadeTransition.transition(1,ccScene);//淡入淡出
        CCDirector.sharedDirector().replaceScene(transition);
    }

    /**
     * 加载坐标点
     * @return
     */
    public static ArrayList<CGPoint> loadPonits(CCTMXTiledMap map,String grouNamed){
        ArrayList<CGPoint> points = new ArrayList<>();
        CCTMXObjectGroup objectGroup = map.objectGroupNamed(grouNamed);
        ArrayList<HashMap<String,String>> objects = objectGroup.objects;
        for (HashMap<String,String> hasmap:objects) {
            Integer x = Integer.parseInt(hasmap.get("x"));
            Integer y = Integer.parseInt(hasmap.get("y"));
            points.add(CCNode.ccp(x,y));
        }
        return points;
    }
}
