package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.kotcrab.vis.ui.VisUI;
import com.mygdx.game.mods.FirstLoaderMode;
import com.mygdx.game.mods.GameMode;

public class Start extends Game {

    @Override
    public void dispose() {
        super.dispose();
        VisUI.dispose();
    }

    @Override
    public void create() {
        VisUI.load();
        setScreen(new GameMode());
    }
}
