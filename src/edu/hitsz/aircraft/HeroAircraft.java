package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.strategy.StraightShoot;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {



    private static HeroAircraft instance = new HeroAircraft(Main.WINDOW_WIDTH / 2 ,Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),0,0,200);

    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        //每次射击发射子弹数量
        this.shootNum = 1;

        //子弹威力
        this.power = 30;

        //子弹射击方向 (向上发射：-1，向下发射：1)
        this.direction = -1;

        this.setStrategy(new StraightShoot());
    }

    public static HeroAircraft getInstance(){
        return instance;
    }
    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }


    public void increaseHp (int increase){
        hp += increase;
        if (hp > maxHp){
            hp = maxHp;
        }

    }

    public void changeShootNum (int shootNum){
        this.shootNum=shootNum;
    }
}
