package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;

public class SteerableComponent implements Component {

    public Steerable<Vector2> steerable;

    public SteerableComponent(Steerable<Vector2> steerable) {
        this.steerable = steerable;
    }
}
