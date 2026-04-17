package edu.hitsz.aircraft;

public interface EnemyCreator {
   public AbstractAircraft createEnemy(int LocationX , int LocationY , int speedX , int speedY ,int hp);
}
