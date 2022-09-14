package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.components.Components;
import com.mygdx.game.components.PhysicsComponent;
import com.mygdx.game.components.SteerableControlComponent;
import com.mygdx.game.components.SteerableComponent;
import com.mygdx.game.utils.SteeringVelocity;

public class B2DSystem extends IteratingSystem implements ContactListener {

    private final World world = new World(new Vector2(0,0),false);

    private final Vector2 linear_v = new Vector2();

    public B2DSystem(){
        super(Family.all(PhysicsComponent.class, SteerableComponent.class, SteerableControlComponent.class).get());
        world.setContactListener(this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        world.step(deltaTime,4,4);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        Body body = Components.PHYS.get(entity).body;
        Steerable<Vector2> steerable = Components.STEER.get(entity).steerable;
        SteeringVelocity velocity = Components.STEER_CONTROL.get(entity).steeringVelocity;

        float newV = Components.STEER_CONTROL.get(entity).steeringVelocity.linear_velocity;
        float oldV = steerable.getLinearVelocity().rotateRad(-steerable.getOrientation()).x;

        float delta = steerable.getMaxLinearAcceleration() * deltaTime;
        if (Math.abs(oldV - newV) > delta){
            newV = oldV + delta * Math.signum(newV - oldV);
        }
        if (Math.abs(newV) > steerable.getMaxLinearSpeed())
            newV = steerable.getMaxLinearSpeed() * Math.signum(newV);
        if (newV < 0 && Math.abs(newV) > steerable.getMaxLinearSpeed()/4)
            newV = steerable.getMaxLinearSpeed() * Math.signum(newV)/4;
        linear_v.set(newV,0).rotateRad(steerable.getOrientation());

        body.setLinearVelocity(linear_v);
        body.setAngularVelocity(velocity.angular_velocity);

    }

    public World getWorld() {
        return world;
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //System.out.println(oldManifold.getPoints()[0].localPoint);
        //System.out.println(oldManifold.getPoints()[w].localPoint);
        //contact.ge
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
