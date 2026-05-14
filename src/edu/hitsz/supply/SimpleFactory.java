package edu.hitsz.supply;

public class SimpleFactory {
    public static AbstractSupply createSupplies(String type,int LocationX , int LocationY){
        switch (type){
            case "BloodSupply":
                return new BloodSupply(LocationX, LocationY);
            case "BombSupply":
                return  new BombSupply(LocationX,LocationY);
            case "FirePlusSupply":
                return  new FirePlusSupply(LocationX , LocationY);
            case "FireSupply":
                return  new FireSupply(LocationX, LocationY);
            case "FreezeSupply":
                return  new FreezeSupply(LocationX , LocationY);
            default:
                throw new IllegalArgumentException("Unkown edu.hitsz.supply type!");
        }
    }
}
