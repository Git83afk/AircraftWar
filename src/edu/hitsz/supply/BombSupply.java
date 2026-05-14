package edu.hitsz.supply;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.application.MusicThread;
import java.util.List;
import edu.hitsz.aircraft.Observer;
import edu.hitsz.bullet.EnemyBullet;

public class BombSupply extends AbstractSupply{
    public BombSupply (int locationX ,int locationY){
        super(locationX,locationY);
    }
    @Override
    public void Effect(HeroAircraft hero , List<AbstractAircraft> enemies , List <BaseBullet> bullets) {
        System.out.println("BombSupply active");
        new MusicThread("src/videos/bomb_explosion.wav",false).start();

        //将所有敌机和敌机子弹添加为观察者
        for (AbstractAircraft enemy : enemies){
            this.addObserver((Observer) enemy);
        }

        for (BaseBullet bullet : bullets){
            this.addObserver((Observer) bullet);
        }
        //通知观察者执行效果
        this.notifyObservers();
        //清理观察者列表
        this.observers.clear();
    }




}
