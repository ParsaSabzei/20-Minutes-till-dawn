package ap.project.controller;

import ap.project.Main;
import ap.project.model.App;
import ap.project.model.FileChooserCallback;
import ap.project.model.User;
import ap.project.model.enums.Avatars;
import ap.project.screens.MainMenuScreen;
import ap.project.screens.RegisterMenuScreen;
import ap.project.utils.UIUtils;
import ap.project.view.HintMenuView;
import ap.project.view.ProfileMenuView;
import ap.project.view.RegisterMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HintMenuController {
    private final HintMenuView view;
    public HintMenuController(HintMenuView view){
        this.view = view;
        Gdx.input.setInputProcessor(view.stage);
        setupListeners();
    }


    private void setupListeners() {
        view.backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.instance.changeScreen(new MainMenuScreen());
            }
        });
    }


    public void update(float delta) {

    }
}
