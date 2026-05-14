package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.RingShoot;
import edu.hitsz.supply.AbstractSupply;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends AbstractAircraft implements Observer{

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setStrategy(new RingShoot());

        this.shootNum = 20;

        this.power = 10;

        this.direction = 1 ;
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
    public void update(AbstractSupply supply){

    }
}
