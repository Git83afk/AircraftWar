package supply;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public abstract class AbstractSupply extends AbstractFlyingObject {
    public AbstractSupply(int locationX, int locationY) {
        super(locationX, locationY, 0, 9);

    }
@Override
public void forward() {
    super.forward();
    // 判定 y 轴向下飞行出界
    if (locationY >= Main.WINDOW_HEIGHT ) {
        vanish();
    }
}

    public void Effect(HeroAircraft hero, List<AbstractAircraft> enemies, List<BaseBullet> bullets) {

    }
}


