package com.mitbbs.hellowgame.plant;

import com.mitbbs.hellowgame.base.DefancePlant;
import com.mitbbs.hellowgame.util.Utils;

import org.cocos2d.actions.base.CCAction;

/**
 * Created by jc on 2017/4/1.
 */
public class Nut extends DefancePlant{

    public Nut() {
        super("image/plant/nut/p_3_01.png");
        baseAction();
    }

    @Override
    public void baseAction() {
        CCAction action = Utils.animate("image/plant/nut/p_3_%02d.png"
                            ,11,true);
        this.runAction(action);
    }
}
