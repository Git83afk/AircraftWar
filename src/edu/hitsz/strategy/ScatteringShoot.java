package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatteringShoot implements ShootStrategy{


    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {
        List<BaseBullet> res = new LinkedList<>();
        int shootNum = aircraft.getShootNum();
        int direction = aircraft.getDirection();
        int power = aircraft.getPower();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + direction*2;
        int speedY = aircraft.getSpeedY() + direction*5;
        int speedX = 0;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            // 不同敌机发射不同子弹
            if (aircraft instanceof HeroAircraft) {
                bullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX + ( i * 2 -shootNum + 1 ), speedY, power);
            } else {
                bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX + ( i * 2 -shootNum + 1 ), speedY, power);
            }
            res.add(bullet);
        }

        return res;
    }
}
