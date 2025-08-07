package ap.project.view;

import ap.project.model.enums.Texts;
import ap.project.utils.UIUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class EndGameView extends Menu{

    public String title;
    public boolean lose;
    public String username;
    public int kills;
    public float survivalTime;
    public int score;
    public TextButton backButton = new TextButton(Texts.BackToMainMenu.getText(), skin);

    public void buildUI() {
        Table gameOverTable = new Table();
        gameOverTable.setFillParent(true);
        gameOverTable.top().center().padTop(50);

        float height = stage.getViewport().getScreenHeight();

        Color color = Color.RED;
        if (!lose)
            color = Color.GREEN;

        Label youLoseLabel = new Label(title, UIUtils.getLabelStyle(90, color));
        gameOverTable.add(youLoseLabel).padBottom(height / 10).padTop(height / 10).row();

        float shakeDistance = 5f;
        float shakeDuration = 0.05f;
        Action shake = Actions.forever(
            Actions.sequence(
                Actions.moveBy(shakeDistance, 0, shakeDuration),
                Actions.moveBy(-shakeDistance * 2, 0, shakeDuration),
                Actions.moveBy(shakeDistance, 0, shakeDuration),
                Actions.moveBy(0, shakeDistance, shakeDuration),
                Actions.moveBy(0, -shakeDistance * 2, shakeDuration),
                Actions.moveBy(0, shakeDistance, shakeDuration)
            )
        );
        youLoseLabel.addAction(shake);

        Label usernameLabel = new Label(Texts.Username.getText() + ": " + username, UIUtils.getLabelStyle(50));
        gameOverTable.add(usernameLabel).padBottom(height / 10).row();

        Label killsLabel = new Label(Texts.Kills.getText() + ": " + kills, UIUtils.getLabelStyle(50));
        killsLabel.setFontScale(0.9f);
        gameOverTable.add(killsLabel).padBottom(height / 10).row();

        Label timeLabel = new Label(Texts.SurvivalTime.getText() + ": " + String.format("%.1f %s", survivalTime, Texts.Seconds.getText()), UIUtils.getLabelStyle(50));
        timeLabel.setFontScale(0.9f);
        gameOverTable.add(timeLabel).padBottom(height / 10).row();

        Label scoreLabel = new Label(Texts.Score.getText() + ": " + score, UIUtils.getLabelStyle(50));
        gameOverTable.add(scoreLabel).padBottom(height / 10).row();

        gameOverTable.add(backButton).center().expandY().bottom().padBottom(30);

        Image background = new Image(UIUtils.createTransparentBackground());
        background.setFillParent(true);
        stage.addActor(background);

        stage.addActor(gameOverTable);

    }
}
