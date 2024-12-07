package io.github.nofe1248.gui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;

public class LoginPanel extends ApplicationAdapter {
    private Stage stage;
    private Skin skin;

    @Override
    public void create() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.stage);
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        SceneComposerStageBuilder builder = new SceneComposerStageBuilder();
        builder.build(this.stage, this.skin, Gdx.files.internal("skin/uiskin.json"));
    }
}
