package com.mitbbs.hellowgame.base;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

/**
 * Created by jc on 2017/3/30.
 */
public class BaseLayer extends CCLayer {
    public CGSize winSize = CCDirector.sharedDirector().winSize();//屏幕宽高

    public BaseLayer(){

    }
}
