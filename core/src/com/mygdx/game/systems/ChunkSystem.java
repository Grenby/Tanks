package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
/**
 * this is system for loading chunks and update enemy units active or sleeping
 */

public class ChunkSystem extends IteratingSystem {

    public ChunkSystem(Family family) {
        super(family);
    }

    public ChunkSystem(Family family, int priority) {
        super(family, priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

}
