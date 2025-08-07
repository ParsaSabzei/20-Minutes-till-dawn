package ap.project.screens;

import ap.project.Main;
import ap.project.controller.MainMenuController;
import ap.project.controller.ProfileMenuController;
import ap.project.model.App;
import ap.project.view.MainMenuView;
import ap.project.view.ProfileMenuView;
import com.badlogic.gdx.Screen;

public class ProfileMenuScreen implements Screen {
    private ProfileMenuView view;

    public ProfileMenuController getController() {
        return controller;
    }

    private ProfileMenuController controller;

    public ProfileMenuScreen(){
        view = new ProfileMenuView();
        controller = new ProfileMenuController(view);
        view.drawUI();
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
