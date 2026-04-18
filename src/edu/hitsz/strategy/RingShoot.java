package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class RingShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft) {

        int shootNum = aircraft.getShootNum();
        int power = aircraft.getPower();
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();

        // 2. 设定子弹扩散的总速度模长（可根据手感调整）
        double magnitude = 6.0;

        // 3. 循环产生 20 颗子弹，每颗子弹的角度均匀分布
        for (int i = 0; i < shootNum; i++) {
            // 计算当前子弹的角度 (弧度制)：2*PI / 20 * i
            double angle = i * (2 * Math.PI / shootNum);

            // 计算分速度
            int speedX = (int) (magnitude * Math.cos(angle));
            int speedY = (int) (magnitude * Math.sin(angle));

            // 4. 根据飞机类型创建对应的子弹
            BaseBullet bullet;
            if (aircraft instanceof HeroAircraft) {
                bullet = new HeroBullet(x, y, speedX, speedY, power);
            } else {
                bullet = new EnemyBullet(x, y, speedX, speedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}
