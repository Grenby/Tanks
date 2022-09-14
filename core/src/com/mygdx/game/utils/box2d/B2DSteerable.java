package com.mygdx.game.utils.box2d;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class B2DSteerable extends B2DLocation implements Steerable<Vector2> {

    private float
            zeroLinearSpeed = 0.001f,
            maxLinearSpeed = 2,
            maxLinearAcceleration = 5,
            maxAngularSpeed = 4,
            maxAngularAcceleration = 1;

    private boolean tagged = false;

    public B2DSteerable(Body body) {
        super(body);
        if (body.getType() == BodyDef.BodyType.StaticBody){
            maxLinearSpeed = 0;
            maxAngularAcceleration= 0;
            maxAngularSpeed=0;
            maxLinearAcceleration=0;
        }
    }

    //
    //Linear parameters
    //
    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return zeroLinearSpeed;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        zeroLinearSpeed = value;
    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    //
    //Angular parameters
    //

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public float getBoundingRadius() {
        return body.getFixtureList().get(0).getShape().getRadius();
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }
}
