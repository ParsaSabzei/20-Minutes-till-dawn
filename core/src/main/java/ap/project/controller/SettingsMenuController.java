package ap.project.controller;

import ap.project.Main;
import ap.project.model.App;
import ap.project.model.GameSettings;
import ap.project.model.User;
import ap.project.model.enums.BackgroundMusics;
import ap.project.model.enums.Languages;
import ap.project.model.enums.Texts;
import ap.project.screens.MainMenuScreen;
import ap.project.screens.SettingsMenuScreen;
import ap.project.view.RegisterMenuView;
import ap.project.view.SettingsMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class SettingsMenuController {
    private final SettingsMenuView view;
    private GameSettings settings;

    private String waitingForKey = "";

    public SettingsMenuController(SettingsMenuView view){
        this.view = view;

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(view.stage);
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                keyPressed(keycode);
                return true;
            }
        });
        Gdx.input.setInputProcessor(multiplexer);
        setupListeners();

        settings = App.getInstance().getSettings();
        view.setMusicVolume(settings.getMusicVolume());
        view.isSfxOnCheckBox.setChecked(settings.isSfxOn());
        view.backgroundMusicSelector.setSelected(settings.getBackgroundMusicType().getName());
        changeMovementButtonsText();
        view.isAutoRealoadOnCheckBox.setChecked(settings.isAutoReloadOn());
        view.blackWhiteSelector.setSelected(settings.isBlackWhiteGame() ? Texts.Yes.getText() : Texts.No.getText());
        view.languageSelector.setSelected(settings.getLanguage().toString());
    }

    private void changeMovementButtonsText() {
        view.moveLeftButton.setText(Input.Keys.toString(settings.getInputSettings().keyLeft));
        view.moveRightButton.setText(Input.Keys.toString(settings.getInputSettings().keyRight));
        view.moveUpButton.setText(Input.Keys.toString(settings.getInputSettings().keyUp));
        view.moveDownButton.setText(Input.Keys.toString(settings.getInputSettings().keyDown));
        view.reloadButton.setText(Input.Keys.toString(settings.getInputSettings().reload));
    }

    private void keyPressed(int keycode) {
        if(waitingForKey.isEmpty())
            return;
        switch (waitingForKey) {
            case "UP" -> settings.getInputSettings().keyUp = keycode;
            case "DOWN" -> settings.getInputSettings().keyDown = keycode;
            case "LEFT" -> settings.getInputSettings().keyLeft = keycode;
            case "RIGHT" -> settings.getInputSettings().keyRight = keycode;
            case "RELOAD" -> settings.getInputSettings().reload = keycode;
        }
        changeMovementButtonsText();
        waitingForKey = "";
    }

    private void setupListeners() {
        view.musicVolumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicVolumeSliderChanged();
            }
        });
        view.backgroundMusicSelector.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backgroundMusicSelectorChanged();
            }
        });
        view.blackWhiteSelector.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                blackWhiteGameSelectorChanged();
            }
        });
        view.isSfxOnCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sfxCheckBoxChanged();
            }
        });
        view.moveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.showAlert(Texts.NowPressKey.getText(), Texts.OK.getText());
               waitingForKey = "UP";
            }
        });
        view.moveDownButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.showAlert(Texts.NowPressKey.getText(), Texts.OK.getText());
                waitingForKey = "DOWN";
            }
        });
        view.moveRightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.showAlert(Texts.NowPressKey.getText(), Texts.OK.getText());
                waitingForKey = "RIGHT";
            }
        });
        view.reloadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.showAlert(Texts.NowPressKey.getText(), Texts.OK.getText());
                waitingForKey = "RELOAD";
            }
        });
        view.moveLeftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.showAlert(Texts.NowPressKey.getText(), Texts.OK.getText());
                waitingForKey = "LEFT";
            }
        });
        view.isAutoRealoadOnCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                autoReloadChanged();
            }
        });
        view.backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.instance.changeScreen(new MainMenuScreen());
            }
        });
        view.languageSelector.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                languageSelectorChanged();
            }
        });
    }

    private void languageSelectorChanged() {
        String answer = view.languageSelector.getSelected();
        settings.setLanguage(Languages.valueOf(answer));
    }

    private void blackWhiteGameSelectorChanged() {
        String answer = view.blackWhiteSelector.getSelected();
        settings.setBlackWhiteGame(answer.equals(Texts.Yes.getText()));
    }

    private void autoReloadChanged() {
        boolean checked = view.isAutoRealoadOnCheckBox.isChecked();
        settings.setAutoReloadOn(checked);
    }


    private void sfxCheckBoxChanged() {
        boolean checked = view.isSfxOnCheckBox.isChecked();
        settings.setSfxOn(checked);
    }

    private void backgroundMusicSelectorChanged() {
        String musicName = view.backgroundMusicSelector.getSelected();
        BackgroundMusics music = BackgroundMusics.fromName(musicName);
        settings.changeMusic(music);
    }

    private void musicVolumeSliderChanged() {
        int volume = (int)Math.floor(view.musicVolumeSlider.getValue());
        settings.setMusicVolume(volume);
        view.setMusicVolume(volume);
    }


    public void update(float delta) {
    }
}
