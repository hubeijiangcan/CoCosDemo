package com.mitbbs.hellowgame.zombies;

import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.mitbbs.hellowgame.base.BaseSpirte;
import com.mitbbs.hellowgame.base.Plant;
import com.mitbbs.hellowgame.base.Zombie;
import com.mitbbs.hellowgame.util.Utils;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

/**
 * Created by jc on 2017/3/31.
 */
public class PrimaryZombie extends Zombie{
    private Plant mPlant;// 正在被攻击的植物
    public PrimaryZombie(CGPoint startPoint,CGPoint endPoint) {
        super("image/zombies/zombies_1/walk/z_1_01.png");

        this.startPoint = startPoint;
        this.endPoint = endPoint;
        // 设置僵尸起点坐标
        this.setPosition(startPoint);
        move();

    }

    @Override
    public void move() {
        Log.e("test",startPoint.y +" -- "+endPoint.y);
        CCMoveTo moveTo = CCMoveTo.
                action(CGPointUtil.distance(startPoint,endPoint)/speed,endPoint);
                                                        // 僵尸走到头,要销毁
        CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this,"destory"));
        this.runAction(sequence);
        baseAction();
    }

    @Override
    public void attack(BaseSpirte spirte) {
        if (spirte instanceof Plant) {// 判断是否是植物
             mPlant= (Plant) spirte;
            this.stopAllActions();// 停止僵尸所有动作

            CCAction animate = Utils.animate(
                    "image/zombies/zombies_1/attack/z_1_attack_%02d.png", 10,
                    true);
            this.runAction(animate);// 僵尸咬植物的动画

            CCScheduler scheduler = CCScheduler.sharedScheduler();
            scheduler.schedule("attackPlant", this, 1, false);// 每隔一秒,咬一口植物

        }
    }
    /**
     * 僵尸攻击植物
     *
     * @param f
     */
    public void attackPlant(float f) {
        if (mPlant != null ) {
            mPlant.attacked(attack);// 植物掉血
            if (mPlant.getLife() <= 0) {// 植物挂了
                CCScheduler.sharedScheduler().unschedule("attackPlant", this);// 停止定时器
                this.stopAllActions();// 停止僵尸所有动作
                move();// 僵尸继续前行
                isAttacking = false;// 表示僵尸已经攻击结束
            }
        } else {
            CCScheduler.sharedScheduler().unschedule("attackPlant", this);// 停止攻击植物
        }
    }
    @Override
    public void attacked(int attack) {

    }

    @Override
    public void baseAction() {
        // 僵尸行走
        CCAction animate = Utils.animate(
                "image/zombies/zombies_1/walk/z_1_%02d.png", 7, true);
        this.runAction(animate);
    }
}
