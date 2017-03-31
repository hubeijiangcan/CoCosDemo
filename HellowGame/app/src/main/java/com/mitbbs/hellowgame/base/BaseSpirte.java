package com.mitbbs.hellowgame.base;

import org.cocos2d.nodes.CCSprite;

/**
 * 对战元素的共性
 * Created by jc on 2017/3/31.
 */
public abstract class BaseSpirte extends CCSprite{
    public interface DieLisenter{
        void die();
    }

    private DieLisenter dieLisenter;

    public BaseSpirte(String filepath) {
        super(filepath);
    }

    public void setDieLisenter(DieLisenter dieLisenter){
        this.dieLisenter = dieLisenter;
    }
    /**
     * 原地不动的基本动作
     */
    public abstract void baseAction();

    public void destory(){
        if (dieLisenter != null){
            dieLisenter.die();
        }
        removeSelf();
    }
}

