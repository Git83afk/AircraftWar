package edu.hitsz.aircraft;

public class BossEnemyCreator implements  EnemyCreator{
    public AbstractAircraft createEnemy (int LocationX , int LocationY , int speedX ,  int speedY , int hp){
        return new BossEnemy(LocationX , LocationY ,speedX , speedY ,hp);
    }
}
