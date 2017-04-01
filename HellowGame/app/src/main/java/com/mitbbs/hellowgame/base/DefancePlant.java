package com.mitbbs.hellowgame.base;

/**
 * 防御型植物
 * Created by jc on 2017/4/1.
 */
public abstract  class DefancePlant extends Plant{
    public DefancePlant(String filepath) {
        super(filepath);
        life = 200;
    }
}
