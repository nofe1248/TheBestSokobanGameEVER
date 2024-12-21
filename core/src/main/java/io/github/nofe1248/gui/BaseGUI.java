package io.github.nofe1248.gui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;
import io.github.nofe1248.map.MapManager;

public abstract class BaseGUI extends ApplicationAdapter {
    protected String skinAssetPath;
    protected String layoutAssetPath;
    protected Viewport viewport;
    protected Stage stage;
    protected Skin skin;

    public BaseGUI(String skinAssetPath, String layoutAssetPath) {
        this.skinAssetPath = skinAssetPath;
        this.layoutAssetPath = layoutAssetPath;
    }

    @Override
    public void create() {
        assert this.skinAssetPath != null && !this.skinAssetPath.isEmpty();
        assert this.layoutAssetPath != null && !this.layoutAssetPath.isEmpty();
        this.viewport = new FitViewport(1024, 576);
        this.stage = new Stage(this.viewport);
        Gdx.input.setInputProcessor(this.stage);
        Gdx.app.getGraphics().setTitle("TheBestSokobanGameEVER");
        Gdx.app.getGraphics().setForegroundFPS(Gdx.graphics.getDisplayMode().refreshRate + 1);
        Gdx.app.getGraphics().setWindowedMode(1920, 1080);
        Gdx.app.getGraphics().setResizable(true);
        this.skin = new Skin(Gdx.files.internal(this.skinAssetPath));

        SceneComposerStageBuilder builder = new SceneComposerStageBuilder();
        builder.build(this.stage, this.skin, Gdx.files.internal(this.layoutAssetPath));
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height);
    }

    @Override
    public void render() {
        input();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(this.stage);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.skin.dispose();
    }

    public abstract void onShow();

    public abstract void onHide();

    public void input() {

    }

    public Stage getStage() {
        return this.stage;
    }
}
