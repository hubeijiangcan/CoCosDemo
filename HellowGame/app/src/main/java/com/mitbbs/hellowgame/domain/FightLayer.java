package com.mitbbs.hellowgame.domain;


import org.cocos2d.layers.CCTMXTiledMap;

/**
 * Created by jc on 2017/3/30.
 */
public class FightLayer extends BaseLayer {

    private CCTMXTiledMap map;

    public FightLayer() {
        loadMap();
    }

    private void loadMap() {
        map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
        this.addChild(map);
    }
}
