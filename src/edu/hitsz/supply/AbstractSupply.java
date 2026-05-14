package edu.hitsz.supply;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.Observer;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSupply extends AbstractFlyingObject {
   protected List<Observer> observers = new ArrayList<>();

    public AbstractSupply(int locationX, int locationY) {
        super(locationX, locationY, 0, 9);

    }

    //增加观察者
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    // 移除观察者
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    //通知所有观察者
    public void notifyObservers(){
        for(Observer observer : observers){
            observer.update(this);
        }
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


