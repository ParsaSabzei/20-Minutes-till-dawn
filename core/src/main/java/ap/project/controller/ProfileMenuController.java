package ap.project.controller;

import ap.project.Main;
import ap.project.model.App;
import ap.project.model.FileChooserCallback;
import ap.project.model.Game;
import ap.project.model.User;
import ap.project.model.enums.Avatars;
import ap.project.model.enums.Texts;
import ap.project.screens.MainMenuScreen;
import ap.project.screens.RegisterMenuScreen;
import ap.project.utils.UIUtils;
import ap.project.view.ProfileMenuView;
import ap.project.view.RegisterMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ProfileMenuController {
    private final ProfileMenuView view;
    public ProfileMenuController(ProfileMenuView view){
        this.view = view;
        Gdx.input.setInputProcessor(view.stage);
        setupListeners();
        applyChanges();
    }

    public void applyChanges() {
        view.changeAvatar(UIUtils.createCircularTexture(App.getInstance().getUserAvatar()));
        view.usernameLabel.setText(App.getInstance().getLoggedInUser().getUsername());
        view.scoreLabel.setText(Texts.Score.getText()+": "+App.getInstance().getLoggedInUser().getScore());
        view.usernameField.setText(App.getInstance().getLoggedInUser().getUsername());
    }

    private void setupListeners() {
        view.changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                changeUsernameButton(event, x, y);
            }
        });
        view.changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                changePasswordButton(event, x, y);
            }
        });
        view.avatarSelector.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                avatarSelectorChanged();
            }
        });
        view.deleteUserButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                deleteUserButtonClicked();
            }
        });
        view.backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.instance.changeScreen(new MainMenuScreen());
            }
        });
    }

    private void deleteUserButtonClicked() {
        App.getInstance().getUserService().remove(App.getInstance().getLoggedInUser());
        App.getInstance().setLoggedInUser(null);
        Main.instance.changeScreen(new RegisterMenuScreen());
    }

    private void avatarSelectorChanged() {
        String selected = view.avatarSelector.getSelected();
        Avatars avatar = Avatars.fromName(selected);
        App.getInstance().getLoggedInUser().setAvatar(avatar);
        if(avatar == Avatars.Custom) {
            Main.instance.fileChooserService.chooseImageFileAsync(new FileChooserCallback() {
                @Override
                public void onFileChosen(FileHandle file) {
                    FileHandle dest = Gdx.files.local( App.getInstance().getLoggedInUser().getAvatarPath());
                    if(dest.exists())
                        dest.delete();
                    file.copyTo(dest);
                    applyChanges();
                }

                @Override
                public void onCancel() {
                }
            });
        }
        applyChanges();
    }

    private void changePasswordButton(InputEvent event, float x, float y) {
        String password = view.passwordField.getText();
        var result = User.passwordValidity(password);
        if(!result.isSuccess()){
            view.showAlert(result.getData(), Texts.OK.getText());
            return;
        }
        App.getInstance().getLoggedInUser().setPassword(password);
        view.showAlert(Texts.PasswordChanged.getText(), Texts.OK.getText());
    }

    private void changeUsernameButton(InputEvent event, float x, float y) {
        String username = view.usernameField.getText();
        var result = User.usernameValidity(username);
        if(!result.isSuccess()){
            view.showAlert(result.getData(), Texts.OK.getText());
            return;
        }
        if(App.getInstance().getUserService().isUserExist(username)){
            view.showAlert(Texts.UserAlreadyRegistered.getText(), Texts.OK.getText());
            return;
        }
        App.getInstance().getLoggedInUser().setUsername(username);
        view.usernameLabel.setText(App.getInstance().getLoggedInUser().getUsername());
    }

    public void update(float delta) {

    }

    public void fileReceived(FileHandle file) {
        FileHandle dest = Gdx.files.local(App.getInstance().getLoggedInUser().getAvatarPath());
        if(dest.exists())
            dest.delete();
        App.getInstance().getLoggedInUser().setAvatar(Avatars.Custom);
        file.copyTo(dest);
        applyChanges();
    }
}
