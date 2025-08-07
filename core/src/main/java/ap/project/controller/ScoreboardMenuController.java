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
import ap.project.view.ScoreboardMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardMenuController {
    private ScoreboardMenuView view;
    public ScoreboardMenuController(ScoreboardMenuView view){
        this.view = view;
        Gdx.input.setInputProcessor(view.stage);
        setupListeners();
        view.topPlayers = getByScore();
        view.drawUI();
    }

    public List<User> getByScore() {
        List<User> sorted = new ArrayList<>(App.getInstance().getUsers());
        sorted.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        return sorted.subList(0, Math.min(10, sorted.size()));
    }
    public List<User> getByKill() {
        List<User> sorted = new ArrayList<>(App.getInstance().getUsers());
        sorted.sort((a, b) -> Integer.compare(b.getTotalKillCount(), a.getTotalKillCount()));
        return sorted.subList(0, Math.min(10, sorted.size()));
    }
    public List<User> getByUsername() {
        List<User> sorted = new ArrayList<>(App.getInstance().getUsers());
        sorted.sort((a, b) -> b.getUsername().compareTo(a.getUsername()));
        return sorted.subList(0, Math.min(10, sorted.size()));
    }
    public List<User> getBySurvivalTime(){
        List<User> sorted = new ArrayList<>(App.getInstance().getUsers());
        sorted.sort((a, b) -> Float.compare(b.getTotalTimeSurvived(), a.getTotalTimeSurvived()));
        return sorted.subList(0, Math.min(10, sorted.size()));
    }
    private void setupListeners() {
        view.backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.instance.changeScreen(new MainMenuScreen());
                view.drawUI();
            }
        });
        view.sortBySurvival.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                view.topPlayers = getBySurvivalTime();
                view.drawUI();
            }
        });
        view.sortByKills.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                view.topPlayers = getByKill();
                view.drawUI();
            }
        });
        view.sortByUsername.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                view.topPlayers = getByUsername();
                view.drawUI();
            }
        });
        view.sortByScore.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                view.topPlayers = getByScore();
                view.drawUI();
            }
        });
    }


    public void update(float delta) {

    }
}
