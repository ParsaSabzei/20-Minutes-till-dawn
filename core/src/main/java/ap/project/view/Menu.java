package ap.project.view;

import ap.project.model.GameAssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.function.Consumer;

public abstract class Menu {
    public Stage stage;
    public SpriteBatch batch = new SpriteBatch();
    protected Skin skin;

    public Menu() {
        stage = new Stage(new ScreenViewport(), batch);
        skin = GameAssetManager.getInstance().uiSkin;
    }
    public void showAlert(String text, String buttonText){
        Dialog dialog = new Dialog("", skin);
        dialog.text(text);
        dialog.button(buttonText);
        dialog.show(stage);
    }
    public void showAlertWithTextField(String text, String buttonText, Consumer<String> onConfirm){
        final TextField textField = new TextField("", skin);

        Dialog dialog = new Dialog("", skin) {
            @Override
            protected void result(Object object) {
                String name = textField.getText();
                onConfirm.accept(name);
            }
        };

        dialog.getContentTable().add(text).pad(10);
        dialog.getContentTable().row();
        dialog.getContentTable().add(textField).width(250).pad(10);
        dialog.button(buttonText, true);

        dialog.show(stage);
    }
}
