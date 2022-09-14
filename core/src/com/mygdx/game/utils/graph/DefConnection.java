package com.mygdx.game.utils.graph;

import com.badlogic.gdx.ai.pfa.Connection;

public class DefConnection <N> implements Connection<N> {

    N from;
    N to;

    public DefConnection() {
    }

    public DefConnection(N from, N to) {
        this.from = from;
        this.to = to;
    }

    public DefConnection<N> setFrom(N from) {
        this.from = from;
        return this;
    }

    public DefConnection<N> setTo(N to) {
        this.to = to;
        return this;
    }

    @Override
    public float getCost() {
        return 1;
    }

    @Override
    public N getFromNode() {
        return from;
    }

    @Override
    public N getToNode() {
        return to;
    }
}
