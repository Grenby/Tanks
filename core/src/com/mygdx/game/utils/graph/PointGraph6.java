package com.mygdx.game.utils.graph;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.Graph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.utils.maze.GraphBuilder;

public class PointGraph6 implements Graph<ConnectGridPoint>, GraphBuilder<ConnectGridPoint> {

    protected final ObjectSet<ConnectGridPoint> nodes = new ObjectSet<>();
    protected final Array<Connection<ConnectGridPoint>> connections = new Array<>(6);
    protected final Pool<DefConnection<ConnectGridPoint>> freeConnection = new Pool<DefConnection<ConnectGridPoint>>() {
        @Override
        protected DefConnection<ConnectGridPoint> newObject() {
            return new DefConnection<>();
        }
    };

    protected final ConnectGridPoint point2 = new ConnectGridPoint();

    public PointGraph6() {

    }

    public PointGraph6(ObjectSet<ConnectGridPoint> points){
        nodes.addAll(points);
    }



    @Override
    public Array<Connection<ConnectGridPoint>> getConnections(ConnectGridPoint point) {
        for (Connection<ConnectGridPoint> c: connections) {
            freeConnection.free((DefConnection<ConnectGridPoint>) c);
        }
        connections.clear();
        byte e = 1;
        for (int i = 0; i <6; i++) {
            if (point.hasConnection(e)) {
                DefConnection<ConnectGridPoint> c = freeConnection.obtain();
                c.setFrom(point);
                point2.set(point);
                switch (e){
                    case 1: point2.add(0,1); break;
                    case 2: point2.add(1,0); break;
                    case 4: point2.add(1,-1); break;
                    case 8: point2.add(0,-1); break;
                    case 16: point2.add(-1,0); break;
                    case 32: point2.add(-1,1); break;
                }
                c.setTo(nodes.get(point2));
                if (c.getToNode()!=null)
                    connections.add(c);
                else
                    freeConnection.free(c);
            }
            e<<=1;
        }
        return connections;
    }

    @Override
    public void addConnection(ConnectGridPoint n1, ConnectGridPoint n2) {
        addDoubleConnection(n1,n2);
    }

    @Override
    public void addDoubleConnection(ConnectGridPoint n1, ConnectGridPoint n2) {
       if (n1.y == n2.y){
            if (n1.x < n2.x){
                n1.addConnection(2);
                n2.addConnection(16);
            }else{
                n1.addConnection(16);
                n2.addConnection(2);
            }
       }else if (n1.y < n2.y){
           if (n1.x == n2.x){
               n1.addConnection(1);
               n2.addConnection(8);
           }else{
               n1.addConnection(32);
               n2.addConnection(4);
           }
       }else{
           if (n1.x == n2.x){
               n1.addConnection(8);
               n2.addConnection(1);
           }else{
               n1.addConnection(4);
               n2.addConnection(32);
           }
       }
    }

    public ObjectSet<ConnectGridPoint> getNodes() {
        return nodes;
    }
}
