package com.mitbbs.hellowgame.cclayer;


import android.view.MotionEvent;

import com.mitbbs.hellowgame.base.BaseLayer;
import com.mitbbs.hellowgame.domain.ShowPlant;
import com.mitbbs.hellowgame.domain.ShowZombie;
import com.mitbbs.hellowgame.engine.GameEngine;
import com.mitbbs.hellowgame.util.Utils;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by jc on 2017/3/30.
 */
public class FightLayer extends BaseLayer {

    private CCTMXTiledMap map;
    private ArrayList<CGPoint> mZombiePoints;
    private CCSprite chooseBox;
    private CopyOnWriteArrayList<ShowPlant> mShowPlants;
    private CopyOnWriteArrayList<ShowPlant> mSelectPlants = new CopyOnWriteArrayList();
    private ArrayList<ShowZombie> mShowZombies;
    private boolean isMoving = false; //标记是否有植物在移动
    private CCSprite selectBox;
    private CCSprite btStart;
    private CCSprite startLabel;
    public static int SELECT_BOX = 0x01;

    public FightLayer() {
        loadMap();
        loadZombies();
    }

    private void loadMap() {
        map = CCTMXTiledMap.tiledMap("image/fight/map_day.tmx");
        this.addChild(map);

        mZombiePoints = Utils.loadPonits(map,"zombies");
        moveMap();

    }

    private void moveMap(){
        float offset = winSize.width - map.getContentSize().width;
        CCDelayTime delayTime  = CCDelayTime.action(1);
        CCMoveBy moveBy = CCMoveBy.action(2,ccp(offset,0));
        CCSequence sequence = CCSequence.actions(delayTime,moveBy,delayTime,CCCallFunc.action(this,"showPlantBox"));
        map.runAction(sequence);
    }

    private void loadZombies(){
        mShowZombies = new ArrayList<>();
        for (CGPoint point:mZombiePoints) {
            ShowZombie showZombie = new ShowZombie();
            showZombie.setPosition(point);
            mShowZombies.add(showZombie);
            map.addChild(showZombie);
        }
    }

    /**
     * 展示植物框
     */
    public void showPlantBox(){
        setIsTouchEnabled(true);
        showSelecteBox();
        showChooseBox();
    }


    /**
     * 展示已选植物框
     */
    private void showSelecteBox(){
        selectBox = CCSprite.sprite("image/fight/chose/fight_chose.png");
        selectBox.setAnchorPoint(0,1);
        selectBox.setPosition(0,winSize.height);
        this.addChild(selectBox,0,SELECT_BOX);
    }

    /**
     * 展示选择植物框
     */
    private void showChooseBox() {
        chooseBox = CCSprite.sprite("image/fight/chose/fight_choose.png");
        chooseBox.setAnchorPoint(0,0);
        this.addChild(chooseBox);
        mShowPlants = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 9; i++) {
            ShowPlant showPlant = new ShowPlant(i);

            chooseBox.addChild(showPlant.getBgsprite());
            chooseBox.addChild(showPlant.getShowplant());
            mShowPlants.add(showPlant);
        }

        //开始战斗

        btStart = CCSprite.sprite("image/fight/chose/fight_start.png");
        btStart.setPosition(ccp(chooseBox.getContentSize().width/2,30));
        chooseBox.addChild(btStart);
    }


    @Override
    public boolean ccTouchesBegan(MotionEvent event) {

        if (GameEngine.getInstant().isStart()){
            GameEngine.getInstant().handleTouch(event);
            return true;
        }
        CGPoint cgPoint = convertTouchToNodeSpace(event);
        if (CGRect.containsPoint(chooseBox.getBoundingBox(),cgPoint)){

            // 开始战斗
            if (CGRect.containsPoint(btStart.getBoundingBox(),cgPoint)){
                if (!mSelectPlants.isEmpty()){
                    gamePrepare();
                }
            }
            for (ShowPlant showPlant : mShowPlants) {
                if (CGRect.containsPoint(showPlant.getShowplant()
                        .getBoundingBox(), cgPoint)) {// 植物被选择了
                    if (mSelectPlants.size()<5 && !isMoving){
                        isMoving = true;
                        mSelectPlants.add(showPlant);
                        CCMoveTo moveTo = CCMoveTo.action(0.5f,
                                ccp(75+(mSelectPlants.size()-1)*53,winSize.height-65));
                        CCSequence sequence = CCSequence.
                                actions(moveTo,CCCallFunc.action(this,"unlock"));
                        showPlant.getShowplant().runAction(sequence);
                    }
                    break;
                }

            }
        }else if (CGRect.containsPoint(selectBox.getBoundingBox(),cgPoint)){
            boolean isSelect = false;
            for (ShowPlant showPlant:mSelectPlants){
                if (!isMoving && CGRect.containsPoint(showPlant.getShowplant().getBoundingBox(),cgPoint)){
                    isMoving = true;
                    CCMoveTo ccMoveTo = CCMoveTo.action(0.5f,showPlant.getBgsprite().getPosition());
                    CCSequence sequence = CCSequence.actions(ccMoveTo,CCCallFunc.action(this,"unlock"));
                    showPlant.getShowplant().runAction(sequence);
                    mSelectPlants.remove(showPlant);
                    isSelect = true;
                    continue;
                }
                if (isSelect){//有植物被点击
                    CCMoveBy ccMoveBy = CCMoveBy.action(0.5f,ccp(-53,0));
                    showPlant.getShowplant().runAction(ccMoveBy);
                }
            }
        }
        return super.ccTouchesBegan(event);
    }

    private void gamePrepare() {
        setIsTouchEnabled(false);
        //隐藏植物框
        chooseBox.removeSelf();
        // 地图移动回去
        moveMapBack();
        // 缩放已选框
        selectBox.setScale(0.65);
        // 重新添加已选的植物
        for (ShowPlant plant : mSelectPlants) {
            plant.getShowplant().setScale(0.65f);// 因为父容器缩小了 孩子一起缩小
            plant.getShowplant().setPosition(
                    plant.getShowplant().getPosition().x * 0.65f,
                    plant.getShowplant().getPosition().y
                            + (winSize.height - plant.getShowplant()
                            .getPosition().y) * 0.35f);// 设置坐标

            this.addChild(plant.getShowplant());
        }

    }

    public void unlock(){
        isMoving = false;
    }

    private void moveMapBack() {
        float offset = map.getContentSize().width - winSize.width;
        CCMoveBy moveBy = CCMoveBy.action(2,ccp(offset,0));
        CCDelayTime delay = CCDelayTime.action(1);
        CCSequence ccsequence = CCSequence.actions(delay,
                moveBy,delay,CCCallFunc.action(this,"showLable"));
        map.runAction(ccsequence);
    }

    public void showLable(){
        // 回收僵尸, 节省内存
        for (ShowZombie zombie:mShowZombies){
            zombie.removeSelf();
        }
        mShowZombies.clear();

        // 显示准备开始战斗的文字
        startLabel = CCSprite.sprite("image/fight/startready_01.png");
        startLabel.setPosition(winSize.width/2,winSize.height/2);
        this.addChild(startLabel);

        CCAnimate ccAnimate = (CCAnimate) Utils.animate
                ("image/fight/startready_%02d.png",3,0.5f,false);

        CCSequence sequence = CCSequence.actions(ccAnimate,
                CCCallFunc.action(this,"gameBegin"));
        startLabel.runAction(sequence);
    }

    /**
     * 游戏开始
     */
    public void gameBegin() {
        startLabel.removeSelf();
        setIsTouchEnabled(true);
        GameEngine.getInstant().startGame(map,mSelectPlants);

    }
}
