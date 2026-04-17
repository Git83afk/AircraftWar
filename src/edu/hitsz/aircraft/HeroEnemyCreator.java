package edu.hitsz.aircraft;

public class HeroEnemyCreator implements  EnemyCreator{
    @Override
  public  AbstractAircraft  createEnemy(int LocationX , int LocationY , int speedX , int speedY , int hp){
        return new HeroEnemy(LocationX , LocationY ,speedX , speedY ,hp);
    }
}
