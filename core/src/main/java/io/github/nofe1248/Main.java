package io.github.nofe1248;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.GUIConsole;
import com.strongjoshua.console.LogLevel;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private Viewport viewport;

    private Stage stage;

    private Console console;

    @Override public void create () {
        VisUI.load();
        viewport = new FitViewport(16, 9);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        Gdx.app.getGraphics().setTitle("TheBestSokobanGameEVER");
        Gdx.app.getGraphics().setForegroundFPS(Gdx.graphics.getDisplayMode().refreshRate + 1);
        Gdx.app.getGraphics().setWindowedMode(1600, 900);

        console = new GUIConsole(VisUI.getSkin(), true, 0, VisWindow.class, VisTable.class,
            "default-pane", TextField.class, VisTextButton.class, VisLabel.class, VisScrollPane.class);
        console.setSizePercent(50, 50);
        console.setPosition(0, 0);
        console.setVisible(false);
        console.enableSubmitButton(true);
        console.resetInputProcessing();

        image = new Texture("libgdx.png");

        batch = new SpriteBatch();

        input();
    }

    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void input () {
        if (Gdx.input.isKeyJustPressed(Input.Keys.GRAVE)) {
            console.setVisible(!console.isVisible());
        }
    }

    @Override public void render () {
        input();
        ScreenUtils.clear(Color.GRAY);
        stage.getViewport().apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        console.draw();
        batch.draw(image, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        batch.end();
    }
}
