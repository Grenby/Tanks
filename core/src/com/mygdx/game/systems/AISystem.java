package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.AIComponent;
import com.mygdx.game.components.Components;

public class AISystem extends IteratingSystem {

    public AISystem() {
        super(Family.all(AIComponent.class).get());
    }

    public AISystem(int priority) {
        super(Family.all(AIComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AIComponent component = Components.AI.get(entity);
        if (component.active){

        }
    }
}
