package com.mitbbs.hellowgame.domain;

import com.mitbbs.hellowgame.util.Utils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;

/**
 * 展示的僵尸
 * Created by jc on 2017/3/31.
 */
public class ShowZombie extends CCSprite{
    public ShowZombie() {
        super("image/zombies/zombies_1/shake/z_1_01.png");
        setScale(0.5);
        setAnchorPoint(0.5f,0);
        CCAction ccAnimate = Utils.animate("image/zombies/zombies_1/shake/z_1_%02d.png",2,true);
        runAction(ccAnimate);
    }
}
