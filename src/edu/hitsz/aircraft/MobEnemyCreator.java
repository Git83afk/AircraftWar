package edu.hitsz.aircraft;

public class MobEnemyCreator implements  EnemyCreator{
    @Override
    public AbstractAircraft createEnemy (int LocationX ,int LocationY ,int speedX , int speedY ,int hp){
        return new MobEnemy(LocationX ,LocationY ,speedX ,speedY ,hp);
    }
}
