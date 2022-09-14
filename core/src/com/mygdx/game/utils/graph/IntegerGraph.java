package com.mygdx.game.utils.graph;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utils.maze.GraphBuilder;

public class IntegerGraph implements Graph<Integer>, GraphBuilder<Integer> {



    @Override
    public Array<Connection<Integer>> getConnections(Integer integer) {
        return null;
    }

    @Override
    public void addConnection(Integer n1, Integer n2) {

    }

    @Override
    public void addDoubleConnection(Integer n1, Integer n2) {

    }
}
