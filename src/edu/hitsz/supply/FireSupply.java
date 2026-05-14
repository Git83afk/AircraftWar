package edu.hitsz.supply;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.ScatteringShoot;
import edu.hitsz.strategy.StraightShoot;

import java.util.List;

public class FireSupply extends  AbstractSupply{

    public FireSupply (int locationX ,int locationY){
        super(locationX,locationY);
    }

    @Override
    public void Effect(HeroAircraft hero ,List<AbstractAircraft> enemies, List <BaseBullet> bullets) {
        //开启一个新线程进行记时
        Runnable task = () ->{
            try{
                hero.setStrategy(new ScatteringShoot());
                hero.changeShootNum(3);
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
