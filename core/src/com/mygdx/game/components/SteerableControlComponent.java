package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.utils.SteeringVelocity;

public class SteerableControlComponent implements Component {
    public SteeringVelocity steeringVelocity = new SteeringVelocity();
}
