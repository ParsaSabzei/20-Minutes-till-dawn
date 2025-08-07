package ap.project.view;

import ap.project.model.GameAssetManager;
import ap.project.model.enums.Texts;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;

public class RegisterMenuView extends Menu implements View{
    public TextField usernameField;
    public TextField passwordField;
    public TextButton registerButton;
    public TextButton loginAsGuestButton;
    public TextButton loginButton;
    public TextButton forgetPasswordButton;

    private Image background;

    public RegisterMenuView() {
        super();
        drawUI();
    }

    private void drawUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.top();

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        registerButton = new TextButton(Texts.Register.getText(), skin);
        loginButton = new TextButton(Texts.Login.getText(), skin);
        loginAsGuestButton = new TextButton(Texts.ContinueAsGuest.getText(), skin);
        forgetPasswordButton = new TextButton(Texts.ForgetPassword.getText(), skin);

        Image signUpImage = new Image(GameAssetManager.getInstance().signupMenuLogo);
        background = new Image(GameAssetManager.getInstance().background);

        float width = stage.getViewport().getWorldWidth();
        float height = stage.getViewport().getWorldHeight();

        float fieldsHeight = height / 14;
        float fieldsWidth = width / 3;

        table.add(signUpImage).colspan(2).center().padBottom(20).row();

        table.add(new Label(Texts.Username.getText()+": ", skin)).center();
        table.add(new Label(Texts.Password.getText()+": ", skin)).center();
        table.row();
        table.add(usernameField).width(fieldsWidth).height(fieldsHeight).expandX();
        table.add(passwordField).width(fieldsWidth).height(fieldsHeight).expandX();

        table.row();
        table.add(registerButton).padTop(fieldsHeight).height(fieldsHeight).width(fieldsWidth / 2).right().padRight(10);
        table.add(loginButton).padTop(fieldsHeight).height(fieldsHeight).width(fieldsWidth / 2).left().padLeft(10);
        table.row();
        table.add(loginAsGuestButton).padTop(fieldsHeight/2).height(fieldsHeight).width(fieldsWidth * ((float) 2 /3)).right().padRight(15);
        table.add(forgetPasswordButton).padTop(fieldsHeight/2).height(fieldsHeight).width(fieldsWidth * ((float) 2 /3)).left().padLeft(15);

        stage.addActor(background);
        stage.addActor(table);
    }

    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        background.setSize(width, height);
    }
    public void dispose() {
        stage.dispose();
    }
}
