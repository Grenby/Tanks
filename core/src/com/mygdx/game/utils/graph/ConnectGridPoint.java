package com.mygdx.game.utils.graph;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Objects;

public class ConnectGridPoint extends GridPoint2 {

    int connections;

    public ConnectGridPoint(int x, int y, int connections) {
        super(x,y);
        this.connections = connections;
    }

    public ConnectGridPoint(int x, int y) {
        super(x,y);
    }

    public ConnectGridPoint() {
    }

    public ConnectGridPoint addConnection(int connections){
        this.connections|=connections;
        return this;
    }

    public ConnectGridPoint removeConnection(int connections){
        this.connections&=(~connections);
        return this;
    }

    public boolean hasConnection(int e){
        return (connections & e) == e;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectGridPoint that = (ConnectGridPoint) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "ByteGridPoint2{" +
                "x=" + x +
                ", y=" + y +
                ", connections=" + connections +
                '}';
    }



    public ConnectGridPoint clearConnection(){
        connections = 0;
        return this;
    }

    public ConnectGridPoint set(ConnectGridPoint point){
        this.y = point.y;
        this.x = point.x;
        this.connections = point.connections;
        return this;
    }

    @Override
    public ConnectGridPoint add(GridPoint2 other) {
        super.add(other);
        return this;
    }

    @Override
    public ConnectGridPoint add(int x, int y) {
        super.add(x, y);
        return this;
    }

    public int getConnections() {
        return connections;
    }
}
