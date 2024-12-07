package io.github.nofe1248.gui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;

public class LoginPanel extends ApplicationAdapter {
    private Viewport viewport;
    private Stage stage;
    private Skin skin;

    @Override
    public void create() {
        this.viewport = new FitViewport(16, 9);
        this.stage = new Stage(this.viewport);
        Gdx.input.setInputProcessor(this.stage);
        Gdx.app.getGraphics().setTitle("TheBestSokobanGameEVER");
        Gdx.app.getGraphics().setForegroundFPS(Gdx.graphics.getDisplayMode().refreshRate + 1);
        Gdx.app.getGraphics().setWindowedMode(1920, 1080);
        Gdx.app.getGraphics().setResizable(false);
        this.skin = new Skin(Gdx.files.internal("gui/Login/Login.json"));

        SceneComposerStageBuilder builder = new SceneComposerStageBuilder();
        builder.build(this.stage, this.skin, Gdx.files.internal("gui/Login/Login.json"));
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.skin.dispose();
    }
}
