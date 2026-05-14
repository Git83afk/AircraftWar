package edu.hitsz.supply;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.aircraft.Observer;
import edu.hitsz.bullet.EnemyBullet;
import java.util.List;

public class FreezeSupply extends AbstractSupply{
    public FreezeSupply (int locationX ,int locationY){
        super(locationX,locationY);
    }

    @Override
    public void Effect(HeroAircraft hero , List<AbstractAircraft> enemies , List <BaseBullet> bullets) {
        System.out.println("FreezeSupply active");

        for (AbstractAircraft enemy : enemies) {
            this.addObserver((Observer) enemy);
        }
        for (BaseBullet bullet : bullets) {
            if (bullet instanceof EnemyBullet) {
                this.addObserver((Observer) bullet);
            }
        }

        this.notifyObservers();
        this.observers.clear();
    }



    }

