package com.mitbbs.hellowgame.engine;

import android.view.MotionEvent;

import com.mitbbs.hellowgame.domain.ShowPlant;

import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.types.CGPoint;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by jc on 2017/3/31.
 */
public class GameEngine {
    private static GameEngine instant;
    private CCTMXTiledMap map;
    private CopyOnWriteArrayList<ShowPlant> mShowPlants;
    private boolean isStart;//标记游戏是否正式启动
    private GameEngine(){

    }

    public static GameEngine getInstant(){
        if (instant == null){
            instant = new GameEngine();
        }
        return instant;
    }

    public void startGame(CCTMXTiledMap map,
                          CopyOnWriteArrayList<ShowPlant> selectPlants){
        isStart = true;
        this.map = map;
        this.mShowPlants = selectPlants;
    }

    public boolean isStart(){
        return isStart;
    }

    public void handleTouch(MotionEvent event) {
        CGPoint cgpoint = map.convertTouchToNodeSpace(event);
    }
}
