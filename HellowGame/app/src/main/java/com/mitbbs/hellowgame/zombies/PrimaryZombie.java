package com.mitbbs.hellowgame.zombies;

import com.mitbbs.hellowgame.base.BaseSpirte;
import com.mitbbs.hellowgame.base.Zombie;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

/**
 * Created by jc on 2017/3/31.
 */
public class PrimaryZombie extends Zombie{

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
        CCMoveTo moveTo = CCMoveTo.
                action(CGPointUtil.distance(startPoint,endPoint)/speed,endPoint);
                                                        // 僵尸走到头,要销毁
        CCSequence sequence = CCSequence.actions(moveTo, CCCallFunc.action(this,"destory"));
        this.runAction(sequence);
        baseAction();
    }

    @Override
    public void attack(BaseSpirte spirte) {

    }

    @Override
    public void attacked(int attack) {

    }

    @Override
    public void baseAction() {

    }
}
