package edu.hitsz.aircraft;

public class AdvancedEnemyCreator implements  EnemyCreator{
    @Override
    public AbstractAircraft  createEnemy (int LocationX , int LocationY , int speedX , int speedY ,int hp){

        return new AdvancedEnemy(LocationX , LocationY ,speedX, speedY ,hp);
    }
}
