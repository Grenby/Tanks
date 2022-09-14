package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;

public class Basis {

    public final Vector2 e1 = new Vector2();
    public final Vector2 e2 = new Vector2();
    private final Vector2 tmp = new Vector2();



    public Vector2 getPos(int x, int y){
        return tmp.set(e1).scl(x).mulAdd(e2,y);
    }

    public Vector2 getPos(float x, float y){
        return tmp.set(e1).scl(x).mulAdd(e2,y);
    }



}
