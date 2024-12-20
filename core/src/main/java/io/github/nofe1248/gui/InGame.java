package io.github.nofe1248.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.nofe1248.map.map.InFlightMap;

class TimerUpdateThread extends Thread {
    private final Object lock = new Object();
    private boolean suspended = false;

    public void suspendThread() {
        synchronized (lock) {
            suspended = true;
        }
    }

    public void resumeThread() {
        synchronized (lock) {
            suspended = false;
            lock.notify();
        }
    }

    public void run() {
        InGame inGame = (InGame) GUIManager.getManager().getCurrentGUI();
        while (true) {
            synchronized (lock) {
                while (suspended) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
            try {
                Thread.sleep(1000); // Simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            inGame.updateTimer();
        }
    }
}

public class InGame extends BaseGUI {
    private InFlightMap activeMap;
    private Table mapRenderTable;
    private boolean isPressingW = false;
    private boolean isPressingS = false;
    private boolean isPressingA = false;
    private boolean isPressingD = false;
    private boolean isPressingRevert = false;
    private ImageButton upButton, downButton, leftButton, rightButton;
    private Label timerLabel, scoreLabel, stepLabel;
    private final TimerUpdateThread timerUpdateThread = new TimerUpdateThread();

    public InGame() {
        super("gui/Playing/Playing.json", "gui/Playing/PlayingLayout.json");
    }

    public void updateTimer() {
        this.timerLabel.setText(activeMap.getElapsedTime() / 1000 + "s");
        this.scoreLabel.setText(String.valueOf(activeMap.getScore()));
    }

    @Override
    public void create() {
        super.create();

        timerLabel = this.stage.getRoot().findActor("time");
        scoreLabel = this.stage.getRoot().findActor("score");
        stepLabel = this.stage.getRoot().findActor("steps");
        assert timerLabel != null;
        assert scoreLabel != null;
        assert stepLabel != null;
        timerLabel.setText("0s");
        stepLabel.setText("0");
        scoreLabel.setText("0");

        upButton = this.stage.getRoot().findActor("up");
        assert upButton != null;
        upButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                if (activeMap.movePlayerUp()) {
                    stepLabel.setText(String.valueOf(activeMap.getSteps()));
                }
            }
        });

        downButton = this.stage.getRoot().findActor("down");
        assert downButton != null;
        downButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                if (activeMap.movePlayerDown()) {
                    stepLabel.setText(String.valueOf(activeMap.getSteps()));
                }
            }
        });

        leftButton = this.stage.getRoot().findActor("left");
        assert leftButton != null;
        leftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                if (activeMap.movePlayerLeft()) {
                    stepLabel.setText(String.valueOf(activeMap.getSteps()));
                }
            }
        });

        rightButton = this.stage.getRoot().findActor("right");
        assert rightButton != null;
        rightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                if (activeMap.movePlayerRight()) {
                    stepLabel.setText(String.valueOf(activeMap.getSteps()));
                }
            }
        });

        TextButton undoButton = this.stage.getRoot().findActor("undo");
        assert undoButton != null;
        undoButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                if (activeMap.revertLastMove()) {
                    stepLabel.setText(String.valueOf(activeMap.getSteps()));
                }
            }
        });

        TextButton hintButton = this.stage.getRoot().findActor("hint");
        assert hintButton != null;
        hintButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
            }
        });

        TextButton solveButton = this.stage.getRoot().findActor("solve");
        assert solveButton != null;
        solveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
            }
        });

        TextButton saveButton = this.stage.getRoot().findActor("save");
        assert saveButton != null;
        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.SAVE_GAME);
            }
        });

        TextButton loadButton = this.stage.getRoot().findActor("load");
        assert loadButton != null;
        loadButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.LOAD_GAME);
            }
        });

        TextButton settingsButton = this.stage.getRoot().findActor("settings");
        assert settingsButton != null;
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.SETTINGS);
            }
        });
    }

    public void setActiveMap(InFlightMap activeMap) {
        this.activeMap = activeMap;
    }

    public InFlightMap getActiveMap() {
        return activeMap;
    }

    @Override
    public void onShow() {
        assert activeMap != null;
        activeMap.startTimer();
        mapRenderTable = this.activeMap.getMap().getRenderTable();
        mapRenderTable.padBottom(100);
        this.stage.addActor(mapRenderTable);
        if (!timerUpdateThread.isAlive()) {
            timerUpdateThread.start();
        } else {
            timerUpdateThread.resumeThread();
        }
    }

    @Override
    public void onHide() {
        activeMap.suspendTimer();
        timerUpdateThread.suspendThread();
    }

    @Override
    public void input() {
        super.input();

        if (this.activeMap != null) {
            mapRenderTable.remove();
            //need to be adjusted to prevent very fast moving when holding the key
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (!isPressingW) {
                    if (activeMap.movePlayerUp()) {
                        stepLabel.setText(String.valueOf(activeMap.getSteps()));
                    }
                    upButton.setDisabled(true);
                    isPressingW = true;
                }
            } else {
                upButton.setDisabled(false);
                isPressingW = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if (!isPressingS) {
                    if (activeMap.movePlayerDown()) {
                        stepLabel.setText(String.valueOf(activeMap.getSteps()));
                    }
                    downButton.setDisabled(true);
                    isPressingS = true;
                }
            } else {
                downButton.setDisabled(false);
                isPressingS = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                if (!isPressingA) {
                    if (activeMap.movePlayerLeft()) {
                        stepLabel.setText(String.valueOf(activeMap.getSteps()));
                    }
                    leftButton.setDisabled(true);
                    isPressingA = true;
                }
            } else {
                leftButton.setDisabled(false);
                isPressingA = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if (!isPressingD) {
                    if (activeMap.movePlayerRight()) {
                        stepLabel.setText(String.valueOf(activeMap.getSteps()));
                    }
                    rightButton.setDisabled(true);
                    isPressingD = true;
                }
            } else {
                rightButton.setDisabled(false);
                isPressingD = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.Z)) {
                if (!isPressingRevert) {
                    if (activeMap.revertLastMove()) {
                        stepLabel.setText(String.valueOf(activeMap.getSteps()));
                    }
                    isPressingRevert = true;
                }
            } else {
                isPressingRevert = false;
            }
            mapRenderTable = this.activeMap.getMap().getRenderTable();
            mapRenderTable.padBottom(100);
            this.stage.addActor(mapRenderTable);
        }
    }
}
