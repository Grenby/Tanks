package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;

public final class Resource {

    private static final Resource res = new Resource();

    private final AssetManager manager = new AssetManager();
    private boolean initialized = false;


    private Resource(){

    }



    public boolean init(){
        if (initialized)
            return true;
        initialized = true;



        return true;
    }


}
