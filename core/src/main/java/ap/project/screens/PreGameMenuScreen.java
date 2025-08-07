package ap.project.screens;

import ap.project.Main;
import ap.project.controller.PreGameMenuController;
import ap.project.controller.RegisterMenuController;
import ap.project.controller.SettingsMenuController;
import ap.project.model.App;
import ap.project.view.PreGameMenuView;
import ap.project.view.RegisterMenuView;
import ap.project.view.SettingsMenuView;
import com.badlogic.gdx.Screen;

public class PreGameMenuScreen implements Screen {
    private PreGameMenuView view;
    private PreGameMenuController controller;

    public PreGameMenuScreen(){
        view = new PreGameMenuView();
        controller = new PreGameMenuController(view);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        if(App.getInstance().getSettings().isBlackWhiteGame())
            view.batch.setShader(Main.instance.grayscaleShader);
        view.render(delta);
        if(App.getInstance().getSettings().isBlackWhiteGame())
            view.batch.setShader(null);
    }

    @Override
    public void resize(int width, int height) {
        view.resize(width, height);
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
    public void dispose() {

    }
}
