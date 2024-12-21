package io.github.nofe1248.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public abstract class BaseSaveGUI extends BaseGUI {
    protected int currentPage = 1;
    protected TextButton map1TitleButton;
    protected TextButton map2TitleButton;
    protected TextButton map3TitleButton;
    protected TextButton map4TitleButton;
    protected ImageButton map1Button;
    protected ImageButton map2Button;
    protected ImageButton map3Button;
    protected ImageButton map4Button;
    protected TextButton frontPageButton;
    protected TextButton previousPageButton;
    protected TextButton nextPageButton;
    protected TextButton lastPageButton;
    static final int MAX_PAGE = 256;
    protected String prefix = "Map ";
    protected boolean map1Exists = false;
    protected boolean map2Exists = false;
    protected boolean map3Exists = false;
    protected boolean map4Exists = false;
    protected Texture placeholder = new Texture("images/Menu/frame_Modified.png");
    protected TextField pageField;

    public BaseSaveGUI(String skinAssetPath, String layoutAssetPath) {
        super(skinAssetPath, layoutAssetPath);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        updateMapTitleOnPageChange();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    protected void updateMapTitleOnPageChange() {
        this.map1TitleButton.setText(prefix + (this.currentPage * 4 - 3));
        this.map2TitleButton.setText(prefix + (this.currentPage * 4 - 2));
        this.map3TitleButton.setText(prefix + (this.currentPage * 4 - 1));
        this.map4TitleButton.setText(prefix + (this.currentPage * 4));
    }

    @Override
    public void create() {
        super.create();

        map1TitleButton = this.stage.getRoot().findActor("map1_title");
        assert map1TitleButton != null;
        map1TitleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                mapButtonCallback(currentPage * 4 - 3, map1Exists);
            }
        });

        map2TitleButton = this.stage.getRoot().findActor("map2_title");
        assert map2TitleButton != null;
        map2TitleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                mapButtonCallback(currentPage * 4 - 2, map2Exists);
            }
        });

        map3TitleButton = this.stage.getRoot().findActor("map3_title");
        assert map3TitleButton != null;
        map3TitleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                mapButtonCallback(currentPage * 4 - 1, map3Exists);
            }
        });

        map4TitleButton = this.stage.getRoot().findActor("map4_title");
        assert map4TitleButton != null;
        map4TitleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                mapButtonCallback(currentPage * 4, map4Exists);
            }
        });

        map1Button = this.stage.getRoot().findActor("map1");
        assert map1Button != null;
        map1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                mapButtonCallback(currentPage * 4 - 3, map1Exists);
            }
        });

        map2Button = this.stage.getRoot().findActor("map2");
        assert map2Button != null;
        map2Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                mapButtonCallback(currentPage * 4 - 2, map2Exists);
            }
        });

        map3Button = this.stage.getRoot().findActor("map3");
        assert map3Button != null;
        map3Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                mapButtonCallback(currentPage * 4 - 1, map3Exists);
            }
        });

        map4Button = this.stage.getRoot().findActor("map4");
        assert map4Button != null;
        map4Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                mapButtonCallback(currentPage * 4, map4Exists);
            }
        });

        frontPageButton = this.stage.getRoot().findActor("left_left");
        assert frontPageButton != null;
        frontPageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                setCurrentPage(1);
            }
        });

        previousPageButton = this.stage.getRoot().findActor("left");
        assert previousPageButton != null;
        previousPageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                if (currentPage > 1) {
                    setCurrentPage(currentPage - 1);
                }
            }
        });

        nextPageButton = this.stage.getRoot().findActor("right");
        assert nextPageButton != null;
        nextPageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                setCurrentPage(currentPage + 1);
            }
        });

        lastPageButton = this.stage.getRoot().findActor("right_right");
        assert lastPageButton != null;
        lastPageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                setCurrentPage(MAX_PAGE);
            }
        });

        pageField = this.stage.getRoot().findActor("page");
        assert pageField != null;
        pageField.setTextFieldListener((textField, c) -> {
            if (c == '\n') {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                try {
                    int page = Integer.parseInt(textField.getText());
                    if (page >= 1 && page <= MAX_PAGE) {
                        setCurrentPage(page);
                    }
                    if (page < 1) {
                        setCurrentPage(1);
                    }
                    if (page > MAX_PAGE) {
                        setCurrentPage(MAX_PAGE);
                    }
                } catch (NumberFormatException e) {
                    textField.setText(String.valueOf(currentPage));
                }
            }
        });
    }

    abstract protected void mapButtonCallback(int mapNumber, boolean exists);
}
