package ap.project.model;

import ap.project.model.enums.Avatars;
import ap.project.service.UserService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class App {
    private static App instance = null;
    private User loggedInUser = null;
    private List<User> users = new ArrayList<>();
    private GameSettings settings;

    public List<User> getUsers() {
        return users;
    }

    public UserService getUserService() {
        return new UserService(users);
    }
    public static App getInstance() {
        if(instance == null)
            instance = new App();
        return instance;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setSettings(GameSettings settings) {
        this.settings = settings;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    public Texture getUserAvatar(){
        if(loggedInUser == null)
            return new Texture(Gdx.files.internal(Avatars.Default.getPath()));
        if(loggedInUser.getAvatar() != Avatars.Custom)
            return new Texture(Gdx.files.internal(loggedInUser.getAvatar().getPath()));
        FileHandle avatar = Gdx.files.local(loggedInUser.getAvatarPath());
        if(avatar.exists())
            return new Texture(avatar);
        return new Texture(Gdx.files.internal(Avatars.Default.getPath()));
    }

    public GameSettings getSettings() {
        return settings;
    }

    public void saveToFile() {
        AppData data = new AppData();
        data.users = this.users;
        data.settings = this.settings;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(data);

        FileHandle file = Gdx.files.local("app_data.json");
        file.writeString(json, false);
    }

    public void loadFromFile() {
        FileHandle file = Gdx.files.local("app_data.json");
        if (!file.exists()) {
            createNew();
            return;
        }

        String json = file.readString();
        Gson gson = new Gson();
        AppData data = gson.fromJson(json, AppData.class);
        this.users = data.users;
        this.settings = data.settings;

    }

    private void createNew() {
        this.settings = new GameSettings();
    }
}
