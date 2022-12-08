package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Logger;
import com.kotcrab.vis.ui.VisUI;
import com.mygdx.game.mods.FirstLoaderMode;
import com.mygdx.game.mods.GameMode;
import com.mygdx.game.mods.Test;
import com.mygdx.game.utils.MyLogger;

import java.util.HashSet;

public class Start extends Game {

    @Override
    public void render() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            dispose();
            Gdx.app.exit();
        }else {
            super.render();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        VisUI.dispose();
    }



    @Override
    public void create() {
//        Logger l = new Logger("LOGGER",Logger.INFO);
//        l.info("1");
//        Gdx.app.log("1","12");
        setScreen(new GameMode());
    }
}
