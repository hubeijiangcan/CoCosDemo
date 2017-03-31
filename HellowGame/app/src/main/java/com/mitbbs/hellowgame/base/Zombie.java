package com.mitbbs.hellowgame.base;

import org.cocos2d.types.CGPoint;

/**僵尸的基类
 * Created by jc on 2017/3/31.
 */
public abstract  class Zombie extends BaseSpirte{
    protected int life = 50;//生命
    protected int attack = 10;//攻击力
    protected int speed = 20;//移动速度

    protected boolean isAttacking;//标记僵尸是否正在攻击

    protected CGPoint startPoint;// 起点
    protected CGPoint endPoint;// 终点

    public Zombie(String filepath) {
        super(filepath);
        setScale(0.5);
        setAnchorPoint(0.5f, 0);// 将解析的点位放在两腿之间
    }

    /**
     * 移动
     */
    public abstract void move();

    /**
     * 攻击
     * @param spirte 僵尸能攻击植物，也能攻击僵尸
     */
    public abstract void attack(BaseSpirte spirte);

    /**
     * 被攻击
     * @param attack
     */
    public abstract void attacked(int attack);

    public boolean isAttacking(){
        return isAttacking;
    }

    public void setIsAttacking(boolean isAttacking){
        this.isAttacking = isAttacking;
    }
}
