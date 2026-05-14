package edu.hitsz.supply;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.RingShoot;
import edu.hitsz.strategy.StraightShoot;

import java.util.List;

public class FirePlusSupply extends AbstractSupply{

    public FirePlusSupply (int locationX ,int locationY){
        super(locationX,locationY);
    }

    @Override
    public void Effect(HeroAircraft hero , List<AbstractAircraft> enemies , List <BaseBullet> bullets) {
       Runnable task =() ->{
           try {
               hero.setStrategy(new RingShoot());
               hero.changeShootNum(20);
               Thread.sleep(5000);
               hero.setStrategy(new StraightShoot());
               hero.changeShootNum(1);
           }catch (InterruptedException e){
               e.printStackTrace();
           }
       };

        new Thread(task).start();
    }
}
