package ap.project.controller;

import ap.project.Main;
import ap.project.model.*;
import ap.project.model.enums.Avatars;
import ap.project.model.enums.GameState;
import ap.project.screens.MainMenuScreen;
import ap.project.screens.RegisterMenuScreen;
import ap.project.utils.UIUtils;
import ap.project.view.GameView;
import ap.project.view.ProfileMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class AbilityController {
    private final GameView view;
    private GameController controller;
    public AbilityController(GameView view, GameController controller) {
        this.view = view;
        setupListeners();
        this.controller = controller;
    }

    private void setupListeners() {
        for(int i = 0; i < 3; i++) {
            int finalI = i;
            view.abilityImages[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Game.getInstance().setGameState(GameState.normal);
                    Ability ability = view.chosenAbilities[finalI];
                    Game.getInstance().getPlayer().applyAbility(ability);
                    Gdx.input.setInputProcessor(controller);
                }
            });
        }
    }

}
