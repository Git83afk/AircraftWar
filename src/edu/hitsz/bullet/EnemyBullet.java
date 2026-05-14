package edu.hitsz.bullet;
import edu.hitsz.supply.AbstractSupply;
/**
 * 敌机子弹
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void update(AbstractSupply supply) {
        if (supply instanceof edu.hitsz.supply.BombSupply) {
            this.vanish();
        } else if (supply instanceof edu.hitsz.supply.FreezeSupply) {
            int originalSpeedX = this.speedX;
            int originalSpeedY = this.speedY;
            this.speedX = 0;
            this.speedY = 0;
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    speedX = originalSpeedX;
                    speedY = originalSpeedY;
                }
            }, 5000);
        }
    }
}
