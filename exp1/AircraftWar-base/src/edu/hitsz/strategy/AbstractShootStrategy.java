package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface AbstractShootStrategy {
    public abstract List<BaseBullet> shoot();
}
