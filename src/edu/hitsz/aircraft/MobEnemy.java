package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.supply.AbstractSupply;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击、不掉落道具
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft implements Observer{

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public void update (AbstractSupply supply){
        if (supply instanceof edu.hitsz.supply.BombSupply) {
            this.vanish();
        } else if (supply instanceof edu.hitsz.supply.FreezeSupply) {
            this.speedX = 0;
            this.speedY = 0;
        }
    }
}
