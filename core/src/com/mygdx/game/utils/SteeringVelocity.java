package com.mygdx.game.utils;

public class SteeringVelocity {

    public float linear_velocity = 0;
    public float angular_velocity = 0;


    public void setZero(){
        linear_velocity = 0;
        angular_velocity = 0;
    }

    public void set(SteeringVelocity steeringVelocity){
        this.linear_velocity = steeringVelocity.linear_velocity;
        this.angular_velocity = steeringVelocity.angular_velocity;
    }


}
