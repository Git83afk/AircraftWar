package edu.hitsz.aircraft;

public class EliteEnemyCreator implements  EnemyCreator{
   @Override
    public AbstractAircraft createEnemy(int LocationX ,int LocationY , int speedX ,int speedY ,int hp){
       return new EliteEnemy(LocationX , LocationY , speedX ,speedY ,hp);
   }
}
