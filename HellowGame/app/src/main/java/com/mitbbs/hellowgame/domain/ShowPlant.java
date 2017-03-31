package com.mitbbs.hellowgame.domain;

import org.cocos2d.nodes.CCSprite;

/**选择的图片
 * Created by jc on 2017/3/31.
 */
public class ShowPlant{
    String format = "image/fight/chose/choose_default%02d.png";
    private  CCSprite bgsprite;
    private  CCSprite showplant;

    public ShowPlant(int i) {
        //c初始化背景图片
        bgsprite = CCSprite.sprite(String.format(format, i));
        bgsprite.setAnchorPoint(0, 0);
        float x = (i - 1) % 4 * 54 + 20;// 计算x坐标
        float y = 175 - (i - 1) / 4 * 59;// 计算y坐标
        bgsprite.setOpacity(100);
        bgsprite.setPosition(x, y);

        showplant = CCSprite.sprite(String.format(format, i));
        showplant.setAnchorPoint(0, 0);
        showplant.setPosition(bgsprite.getPosition());
    }

    public CCSprite getBgsprite() {
        return bgsprite;
    }

    public CCSprite getShowplant() {
        return showplant;
    }

    public void setBgsprite(CCSprite bgsprite) {
        this.bgsprite = bgsprite;
    }

    public void setShowplant(CCSprite showplant) {
        this.showplant = showplant;
    }
}
