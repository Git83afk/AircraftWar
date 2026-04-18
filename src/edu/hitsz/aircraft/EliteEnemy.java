package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.strategy.StraightShoot;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractAircraft{


 public EliteEnemy (int locationX, int locationY, int speedX, int speedY, int hp){
     super(locationX, locationY, speedX, speedY, hp);
     //每次射击发射子弹数量
     this.shootNum = 1;
     //子弹射击方向 (向上发射：-1，向下发射：1)
     this.direction = 1;
     //子弹威力
     this.power=10;

     this.setStrategy(new StraightShoot());
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
