package com.mitbbs.hellowgame.cclayer;

import com.mitbbs.hellowgame.base.BaseLayer;
import com.mitbbs.hellowgame.util.Utils;

import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;

/**
 * Created by jc on 2017/3/30.
 */
public class MenuLayer extends BaseLayer {

    public MenuLayer() {
        //初始化背景
        CCSprite sprite = CCSprite.sprite("image/menu/main_menu_bg.jpg");
        sprite.setAnchorPoint(0,0);
        this.addChild(sprite);

        CCMenu menu  = CCMenu.menu();//初始化menu
        CCSprite nomalSprite = CCSprite.
                sprite("image/menu/start_adventure_default.png");//默认图片
        CCSprite selectSprite = CCSprite.
                sprite("image/menu/start_adventure_press.png");//选中图片

        CCMenuItemSprite item = CCMenuItemSprite.item(nomalSprite,selectSprite,this,"click");
        menu.addChild(item);
        menu.setScale(0.5f);
        menu.setPosition(winSize.width / 2 - 25, winSize.height / 2 - 110);
        menu.setRotation(4.5f);  //旋转
        this.addChild(menu);
    }

    /**
     * 菜单按钮被点击 必须带有object参数,这样才能反射到该方法里
     */
    public void click(Object obj){
        Utils.changeLayer(new FightLayer());
    }
}
