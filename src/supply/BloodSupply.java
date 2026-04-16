package supply;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class BloodSupply extends AbstractSupply{

    public BloodSupply (int locationX , int locationY){
        super(locationX ,locationY);
    }

    //效果：为英雄机增加血量
    @Override
    public void Effect (HeroAircraft hero , List<AbstractAircraft> enemies , List <BaseBullet> bullets){
        hero.increaseHp(15);
    }
}
