package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.Logger;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.utils.MyLogger;

public final class Resource {


    private static final Resource res = new Resource();

    private final AssetManager manager = new AssetManager();
    private boolean initialized = false;


    private Resource(){
        manager.load( Gdx.files.local("logg").name(), FileHandle.class);
    }


    public static AssetManager manager(){
        return res.manager;
    }

    public boolean init(){
        if (initialized)
            return true;
        initialized = true;
        return true;
    }


}
