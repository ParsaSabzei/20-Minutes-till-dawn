package ap.project.controller;

import ap.project.Main;
import ap.project.model.*;
import ap.project.model.enums.BackgroundMusics;
import ap.project.model.enums.GameState;
import ap.project.model.enums.Texts;
import ap.project.screens.MainMenuScreen;
import ap.project.view.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EndGameController {
    private final GameView view;
    private GameController controller;
    public EndGameController(GameView view, GameController controller) {
        this.view = view;
        setupListeners();
        this.controller = controller;
    }

    private void setupListeners() {
            view.endGameView.backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Game.getInstance().clearGame();
                    Main.instance.changeScreen(new MainMenuScreen());
                    App.getInstance().getSettings().continuePlaying();
                }
            });
    }

    public void sendData() {
        Player player = Game.getInstance().getPlayer();
        view.endGameView.kills = Game.getInstance().getTotalKills();
        view.endGameView.lose = Game.getInstance().getTimeElapsed() < Game.getInstance().getTimeControl()*60.0f;
        view.endGameView.survivalTime = Math.round(Game.getInstance().getTimeElapsed());
        if(view.endGameView.lose)
            view.endGameView.title = Texts.YouLose.getText();
        else
            view.endGameView.title = Texts.YouWin.getText();
        view.endGameView.score = (int) (view.endGameView.kills * view.endGameView.survivalTime);
        view.endGameView.username = player.getUser().getUsername();
        User user = player.getUser();
        user.addScore(view.endGameView.score);
        user.setTotalKillCount(user.getTotalKillCount() +view.endGameView.kills );
        user.setTotalTimeSurvived(user.getTotalTimeSurvived() + view.endGameView.survivalTime);
    }
}
