package com.mitbbs.hellowgame.engine;

import android.util.Log;
import android.view.MotionEvent;

import com.mitbbs.hellowgame.base.Plant;
import com.mitbbs.hellowgame.cclayer.FightLayer;
import com.mitbbs.hellowgame.domain.ShowPlant;
import com.mitbbs.hellowgame.plant.Nut;
import com.mitbbs.hellowgame.util.Utils;
import com.mitbbs.hellowgame.zombies.PrimaryZombie;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by jc on 2017/3/31.
 */
public class GameEngine {
    private static GameEngine instant;
    private CCTMXTiledMap map;
    private CopyOnWriteArrayList<ShowPlant> mShowPlants;
    private boolean isStart;//标记游戏是否正式启动
    private ArrayList<CGPoint> mZombiePnints;
    private ShowPlant mShowPlant;// 当前被点击的已选植物
    private Plant mPlant;// 当前要安放的植物
    private CGPoint[][] mPlantPoints = new CGPoint[5][9];
    private static ArrayList<FightLine> mFightLines;
    //初始化五条战线
    static {
        mFightLines = new ArrayList<>();
        for (int i =0;i<5;i++){
            FightLine fightLine = new FightLine(i);
            mFightLines.add(fightLine);
        }
    }

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
        Log.e("test","gameStart");
        isStart = true;
        this.map = map;
        this.mShowPlants = selectPlants;
        //加载僵尸移动路径
        mZombiePnints = Utils.loadPonits(map,"road");
        //定时器
        CCScheduler scheduler = CCScheduler.sharedScheduler();
        scheduler.schedule("loadZombie",this,3,false);
        loadPlantPoints();
    }

    private void loadPlantPoints() {
        String format = "tower%02d";
        for (int i = 1;i<=5;i++){
            ArrayList<CGPoint> points = Utils.loadPonits(map,String.format(format, i));
            for (int j =0;j<points.size();j++){
                mPlantPoints[i-1][j] = points.get(j);
            }
        }
    }

    /**
     * 加载僵尸 float参数必须有,否则CCScheduler无法通过反射调用
     * @param f
     */
    public void loadZombie(float f){
        Random random = new Random();
        int line = random.nextInt(5);
        CGPoint startPoint = mZombiePnints.get(line*2); // 起点坐标，制作地图是顺序制作
        CGPoint endPoint = mZombiePnints.get(line*2+1); //终点坐标
        PrimaryZombie primaryZombie = new PrimaryZombie(startPoint,endPoint);
        mFightLines.get(line).addZombie(primaryZombie);
        map.addChild(primaryZombie,1);
    }

    public boolean isStart(){
        return isStart;
    }

    public void handleTouch(MotionEvent event) {
        CGPoint cgpoint = map.convertTouchToNodeSpace(event);

        CCSprite selectBox = (CCSprite) map.getParent().
                getChildByTag(FightLayer.SELECT_BOX);

        if (CGRect.containsPoint(selectBox.getBoundingBox(),cgpoint)){
            for (ShowPlant showPlant:mShowPlants) {
                if (CGRect.containsPoint(showPlant.getShowplant().getBoundingBox(),cgpoint)){
                    if (mShowPlant != null){
                        mShowPlant.getShowplant().setOpacity(255); //如果之前有选择的植物，设为不透明
                    }
                    mShowPlant = showPlant;
                    mShowPlant.getShowplant().setOpacity(100);
                    switch (mShowPlant.getId()){
                        case 4:   //选择的是土豆
                            mPlant = new Nut();
                            break;
                    }
                }
            }
        }else {//点击落点在草坪上
            if (isInGrass(cgpoint)){ //判断是是否落在草坪的格子里
                if (mPlant != null && mShowPlant != null) {
                    map.addChild(mPlant);// 安放植物
                    mShowPlant.getShowplant().setOpacity(255);// 设置不透明

                    // 给战线添加植物
                    mFightLines.get(mPlant.getLine()).addPlant(mPlant);

                    mPlant = null;
                    mShowPlant = null;
                }
            }
        }
    }

    private boolean isInGrass(CGPoint cgpoint) {
        int column = (int) (cgpoint.x/46);
        int line = (int) ((CCDirector.sharedDirector().winSize().height - cgpoint.y)/54);

        if (column >= 1 && column <= 9 && line >= 1 && line <= 5){
            if (mPlant != null){
                mPlant.setLine(line-1);
                mPlant.setColum(column-1);
                mPlant.setPosition(mPlantPoints[line - 1][column - 1]);// 设置植物的位置

                if (mFightLines.get(line - 1).contaionsPlant(mPlant)) {// 判断战线是否包含植物
                    return false;
                }

                return true;

            }
        }

        return false;
    }
}
