package com.mygdx.game.mods;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.layout.FloatingGroup;

public class Test implements Screen {
    private Stage stage;

    @Override
    public void show () {
        VisUI.load();

        stage = new Stage(new ScreenViewport());
        final Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);


        FloatingGroup floatingGroup = new FloatingGroup(1000, 600);

        root.debugAll();
        root.left().bottom();
        root.add(floatingGroup).padLeft(100).padBottom(100);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize (int width, int height) {
        if (width == 0 && height == 0) return;
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose () {
        VisUI.dispose();
        stage.dispose();
    }
}
