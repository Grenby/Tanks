package com.mygdx.game.components;

import com.badlogic.ashley.core.ComponentMapper;

public class Components {

    //
    public static ComponentMapper<PhysicsComponent> PHYS = ComponentMapper.getFor(PhysicsComponent.class);
    public static ComponentMapper<SteerableComponent> STEER = ComponentMapper.getFor(SteerableComponent.class);
    public static ComponentMapper<SteerableControlComponent> STEER_CONTROL = ComponentMapper.getFor(SteerableControlComponent.class);
    public static ComponentMapper<AIComponent> AI = ComponentMapper.getFor(AIComponent.class);


}
