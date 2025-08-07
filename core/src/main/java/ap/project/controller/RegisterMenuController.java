package ap.project.controller;

import ap.project.Main;
import ap.project.model.App;
import ap.project.model.User;
import ap.project.model.enums.Texts;
import ap.project.screens.MainMenuScreen;
import ap.project.view.RegisterMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class RegisterMenuController {
    private final RegisterMenuView view;
    public RegisterMenuController(RegisterMenuView view){
        this.view = view;
        Gdx.input.setInputProcessor(view.stage);

        setupListeners();
    }

    private void setupListeners() {
        view.registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                registerButtonClicked(event, x, y);
            }
        });
        view.loginAsGuestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                loginAsGuestButtonClicked(event, x, y);
            }
        });
        view.loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                loginButtonClicked(event, x, y);
            }
        });
        view.forgetPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                forgetPasswordClicked(event, x, y);
            }
        });
    }

    private void forgetPasswordClicked(InputEvent event, float x, float y) {
        String username = view.usernameField.getText();
        User user = App.getInstance().getUserService().findUserByUserName(username);
        if(user == null){
            view.showAlert(Texts.InvalidUsername.getText(), Texts.OK.getText());
            return;
        }
        view.showAlertWithTextField(Texts.FirstSchool.getText(), Texts.Subimt.getText(), (sec) -> {
            if(sec.equals(user.getSecurityQuestion())){
                view.showAlertWithTextField(Texts.EnterYourNewPass.getText(), Texts.Subimt.getText(), (password) -> {
                    var result = User.passwordValidity(password);
                    if(!result.isSuccess()) {
                        view.showAlert(result.getData(), Texts.OK.getText());
                        return;
                    }
                    user.setPassword(password);
                    App.getInstance().setLoggedInUser(user);
                    Main.instance.changeScreen(new MainMenuScreen());
                });
            }else{
                view.showAlert(Texts.WrongAnswer.getText(), Texts.OK.getText());
            }
        });
    }

    private void loginButtonClicked(InputEvent event, float x, float y) {
        String username = view.usernameField.getText();
        String password = view.passwordField.getText();
        User user = App.getInstance().getUserService().findUserByUserName(username);
        if(user == null){
            view.showAlert(Texts.InvalidUsername.getText(), Texts.OK.getText());
            return;
        }
        if(!user.getPassword().equals(password)) {
            view.showAlert(Texts.InvalidPassword.getText(), Texts.OK.getText());
            return;
        }
        App.getInstance().setLoggedInUser(user);
        Main.instance.changeScreen(new MainMenuScreen());
    }

    private void loginAsGuestButtonClicked(InputEvent event, float x, float y) {
        User user = new User();
        user.setGuest(true);
        user.setUsername(Texts.Guest.getText());
        App.getInstance().setLoggedInUser(user);
        Main.instance.changeScreen(new MainMenuScreen());
    }

    private void registerButtonClicked(InputEvent event, float x, float y){
        String username = view.usernameField.getText();
        String password = view.passwordField.getText();

        var result = User.usernameValidity(username);
        if(!result.isSuccess()){
            view.showAlert(result.getData(), Texts.OK.getText());
            return;
        }
        if(App.getInstance().getUserService().isUserExist(username)){
            view.showAlert(result.getData(), Texts.UserAlreadyRegistered.getText());
            return;
        }
        result = User.passwordValidity(password);
        if(!result.isSuccess()){
            view.showAlert(result.getData(),  Texts.OK.getText());
            return;
        }

        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        view.showAlertWithTextField(Texts.FirstSchool.getText(), Texts.Subimt.getText(), (sec) -> {
            user.setSecurityQuestion(sec);
            App.getInstance().getUserService().addUser(user);
            App.getInstance().setLoggedInUser(user);
            Main.instance.changeScreen(new MainMenuScreen());
        });
    }

    public void update(float delta) {

    }
}
