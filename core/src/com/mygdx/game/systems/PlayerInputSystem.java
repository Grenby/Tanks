package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Components;
import com.mygdx.game.utils.SteeringVelocity;

public class PlayerInputSystem extends EntitySystem{

    Entity player = null;
    SteeringVelocity steeringVelocity = new SteeringVelocity();

    public void move(float scale){
        steeringVelocity.linear_velocity = scale;
    }

    public void rotate(float scale){
        steeringVelocity.angular_velocity = scale;
    }

    public void move(Vector2 dir){
        //todo
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    @Override
    public void update(float deltaTime) {
        if (player != null){
            Steerable<Vector2> steerable = Components.STEER.get(player).steerable;
            SteeringVelocity playerVelocity = Components.STEER_CONTROL.get(player).steeringVelocity;

            playerVelocity.linear_velocity = steeringVelocity.linear_velocity * steerable.getMaxLinearSpeed();
            playerVelocity.angular_velocity = steeringVelocity.angular_velocity * steerable.getMaxAngularSpeed();
        }
    }
}
