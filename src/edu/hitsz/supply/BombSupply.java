package edu.hitsz.supply;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class BombSupply extends AbstractSupply{
    public BombSupply (int locationX ,int locationY){
        super(locationX,locationY);
    }
    @Override
    public void Effect(HeroAircraft hero , List<AbstractAircraft> enemies , List <BaseBullet> bullets) {
        System.out.println("BombSupply active");
    }
}
