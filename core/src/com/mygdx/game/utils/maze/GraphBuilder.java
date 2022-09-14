package com.mygdx.game.utils.maze;

public interface GraphBuilder<N> {

    void addConnection(N n1, N n2);
    void addDoubleConnection(N n1, N n2);
}
