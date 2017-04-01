package com.mitbbs.hellowgame.base;

/**
 *  植物的基类
 * Created by jc on 2017/4/1.
 */
public abstract class Plant extends BaseSpirte{
    protected int life = 100; //生命值
    protected int line; //行号
    protected int colum; //列号
    public Plant(String filepath) {
        super(filepath);
        setScale(0.65);
        setAnchorPoint(0.5f,0);
    }

    /**
     * 被攻击
     * @param attack
     */
    public void attacked(int attack){
        life -= attack;
        if (life <= 0){
            destory();
        }
    }

    public int getLife() {
        return life;
    }

    public int getLine() {
        return line;
    }

    public int getColum() {
        return colum;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setColum(int colum) {
        this.colum = colum;
    }
}
