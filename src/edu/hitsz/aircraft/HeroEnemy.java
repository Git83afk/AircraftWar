package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.strategy.ScatteringShoot;

import java.util.LinkedList;
import java.util.List;

public class HeroEnemy extends AbstractAircraft{

    public HeroEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);

        //每次射击发射子弹数量
        this.shootNum = 3;
        //子弹威力
        this.power = 10;
        //子弹射击方向 (向上发射：-1，向下发射：1)
        this.direction = 1;

        this.setStrategy(new ScatteringShoot());
    }




    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

}
