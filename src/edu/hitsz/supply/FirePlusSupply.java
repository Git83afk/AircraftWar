package edu.hitsz.supply;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.RingShoot;

import java.util.List;

public class FirePlusSupply extends AbstractSupply{

    public FirePlusSupply (int locationX ,int locationY){
        super(locationX,locationY);
    }

    @Override
    public void Effect(HeroAircraft hero , List<AbstractAircraft> enemies , List <BaseBullet> bullets) {
        hero.setStrategy(new RingShoot());
        hero.changeShootNum(20);
    }
}
