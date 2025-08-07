package ap.project.controller;

import ap.project.Main;
import ap.project.model.*;
import ap.project.model.enums.Characters;
import ap.project.model.enums.Guns;
import ap.project.model.enums.Texts;
import ap.project.screens.GameScreen;
import ap.project.screens.MainMenuScreen;
import ap.project.view.PreGameMenuView;
import ap.project.view.RegisterMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PreGameMenuController {
    private final PreGameMenuView view;
    private int selectedTime = -1;
    private Characters selectedCharacter = null;
    private Guns selectedGun = null;

    public PreGameMenuController(PreGameMenuView view){
        this.view = view;
        Gdx.input.setInputProcessor(view.stage);

        setupListeners();
    }

    private void setupListeners() {
        for(int i = 0; i < view.charactersImage.length; i++){
            Image charImage = view.charactersImage[i];
            addHoverAnimation(charImage);
            charImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedCharacter = Characters.fromName(charImage.getName());
                    view.setChar(selectedCharacter.getName());
                }
            });
        }

        for(Image charImage : view.gunsImage) {
            addHoverAnimation(charImage);
            charImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedGun = Guns.fromName(charImage.getName());
                    view.setGun(selectedGun.getName());
                }
            });
        }
        for(TextButton timeButtons : view.timesButtons){
            timeButtons.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedTime = Integer.parseInt(timeButtons.getText().toString());
                    view.setTime(selectedTime+"");
                }
            });
        }
        view.playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(selectedTime == -1)
                    view.showAlert(Texts.SelectGameTime.getText(), Texts.OK.getText());
                else if(selectedGun == null)
                    view.showAlert(Texts.SelectGun.getText(), Texts.OK.getText());
                else if(selectedCharacter == null)
                    view.showAlert(Texts.SelectChar.getText(), Texts.OK.getText());
                else{
                    createNewGame();
                }
            }
        });
        view.backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.instance.setScreen(new MainMenuScreen());
            }
        });
    }

    private void createNewGame() {
        Player player = new Player();

        Gun gun = new Gun();
        gun.setType(selectedGun);
        player.setGun(gun);
        player.setUser(App.getInstance().getLoggedInUser());
        player.setCharacter(selectedCharacter);
        Game.getInstance().setPlayer(player);
        Game.getInstance().setTimeControl(selectedTime);
        Main.instance.setScreen(new GameScreen());
    }
    private void addHoverAnimation(Image charImage) {
        charImage.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                charImage.addAction(Actions.scaleTo(1.2f, 1.2f, 0.5f, Interpolation.smooth));
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                charImage.addAction(Actions.scaleTo(1f, 1f, 0.5f, Interpolation.smooth));
            }
        });
    }

    public void update(float delta) {

    }
}
