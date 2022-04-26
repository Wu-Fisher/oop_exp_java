package edu.hitsz.props;

import edu.hitsz.application.Game;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.ArrayList;
import java.util.List;

public class BombProps extends AbstractProps {
    Game mg;
    List<AbstractFlyingObject> subList=  new ArrayList<>();
    public BombProps(int locationX, int locationY, int speedX, int speedY, Game game) {
        super(locationX, locationY, speedX, speedY);
        this.mg=game;
    }

    @Override
    public void effectCrash() {
        System.out.println("BombSupply active!");
        addList();
        notifyList();

    }
    public void addList()
    {
        subList.addAll(mg.enemyAircrafts);
        subList.addAll(mg.enemyBullets);
    }
    public void notifyList()
    {
        for(AbstractFlyingObject F:subList)
        {
            F.update(mg);
        }
    }

}
