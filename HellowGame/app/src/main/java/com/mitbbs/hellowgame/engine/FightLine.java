package com.mitbbs.hellowgame.engine;

import com.mitbbs.hellowgame.base.BaseSpirte;
import com.mitbbs.hellowgame.base.Plant;
import com.mitbbs.hellowgame.base.Zombie;

import org.cocos2d.actions.CCScheduler;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 战线
 * Created by jc on 2017/4/1.
 */
public class FightLine {
    private HashMap<Integer, Plant> mPlants = new HashMap<Integer, Plant>();// key表示植物在第几列
    private CopyOnWriteArrayList<Zombie> mZombies = new CopyOnWriteArrayList<Zombie>();// 僵尸集合
    public FightLine(int i) {
        CCScheduler ccScheduler = CCScheduler.sharedScheduler();
        ccScheduler.schedule("attackPlant", this, 0.2f, false);// 每隔0.2秒检测僵尸是否可以攻击植物
    }



    /**
     * 添加植物
     *
     * @param plant
     */
    public void addPlant(final Plant plant) {
        plant.setDieLisenter(new BaseSpirte.DieLisenter() {

            @Override
            public void die() {
                mPlants.remove(plant.getColum());// 移除植物
            }
        });
        mPlants.put(plant.getColum(), plant);
    }

    /**
     * 添加僵尸
     *
     * @param zombie
     */
    public void addZombie(final Zombie zombie) {
        // 僵尸的死亡回调
        zombie.setDieLisenter(new BaseSpirte.DieLisenter() {

            @Override
            public void die() {
                mZombies.remove(zombie);// 僵尸死亡后,从集合中移除
            }
        });
        mZombies.add(zombie);
    }
    /**
     * 僵尸攻击植物
     * @param f
     */
    public void attackPlant(float f){
        if (!mPlants.isEmpty() && !mZombies.isEmpty()) {
            for (Zombie zombie : mZombies) {
                int column = (int) (zombie.getPosition().x / 46 - 1);
                if (mPlants.keySet().contains(column)) {// 僵尸当前所在的列上,有植物存在
                    if (!zombie.isAttacking()) {
                        zombie.attack(mPlants.get(column));// 僵尸开始攻击该列的植物
                        zombie.setIsAttacking(true);// 标记正在攻击

                    }
                }
            }
        }
    }
    /**
     * 判断战线的列,有的话就不能再安放
     * @param plant
     * @return
     */
    public boolean contaionsPlant(Plant plant) {
        // 1, 5, 8 , 5
        return mPlants.keySet().contains(plant.getColum());
    }
}
