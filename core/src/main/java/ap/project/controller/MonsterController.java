package ap.project.controller;

import ap.project.model.*;
import ap.project.model.enums.BackgroundMusics;
import ap.project.model.enums.MonsterTypes;
import ap.project.view.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MonsterController {
    private GameView view;
    public MonsterController(GameView view) {
        this.view = view;
    }
    float timer = 0;
    float eyeEnemyTimer = 0;
    private boolean allowElder = false;
    public void setAllowElder() {
        this.allowElder = true;
    }
    public void update(float delta) {
        Game.getInstance().setElectricWallRadius(Game.getInstance().getElectricWallRadius()
            - Gdx.graphics.getWidth() * delta / Game.getInstance().getTimeElectricWall());

        if(Game.game.isAutoAim()) {
            Monster monster = findNearestMonster();
            if(monster != null && view.isInCamera(monster.getPosition().x, monster.getPosition().y)) {
                Vector3 position = new Vector3(monster.getPosition().x, monster.getPosition().y, 0);
                position = view.camera.project(position);
                position.y = Gdx.graphics.getHeight() - position.y;
                Gdx.input.setCursorPosition((int) position.x, (int) position.y);
            }
        }
        timer += delta;

        if(timer >= 1) {
            timer = 0;
            spawnBrain();
        }
        if(Game.getInstance().getTimeControl() *15 <= Game.getInstance().getTimeElapsed()) {
            eyeEnemyTimer += delta;
        }
        if(eyeEnemyTimer >= 10) {
            eyeEnemyTimer = 0;
            spawnEye();
        }
        if((Game.getInstance().getTimeElapsed() >= (Game.getInstance().getTimeControl() / 2f)*60 || allowElder) && !Game.getInstance().isElderSpawned()) {
            Game.getInstance().setElderSpawned(true);
            App.getInstance().getSettings().playMonsterMusic();
            spawnElder();
        }
        for(Monster monster : Game.getInstance().getMonsters()) {
            for(int i = monster.getForces().size() - 1; i >= 0; i--) {
                monster.getForces().get(i).setTimeRemaining(monster.getForces().get(i).getTimeRemaining() - delta);
                if(monster.getForces().get(i).getTimeRemaining() <= 0) {
                    monster.getForces().remove(i);
                }
            }
            Vector2 position = monster.getPosition();
            Vector2 playerPosition = Game.getInstance().getPlayer().getPosition();
            Vector2 speed = playerPosition.cpy().sub(position).nor().scl(monster.getSpeed() * delta);
            for(Force force : monster.getForces()) {
                Vector2 f = force.getDirection().cpy().nor().scl(force.getPower() * delta);
                speed.add(f);
            }
            Vector2 nextPosition = position.cpy().add(speed);
            Rectangle nextRectangle = Monster.createRectangle(nextPosition.x, nextPosition.y,
                view.getMonsterWidthHeight().x, view.getMonsterWidthHeight().y);

            monster.setTimeElapsedFromLastShot(monster.getTimeElapsedFromLastShot() + delta);
            monster.setTimer(monster.getTimer() + delta);

            for(int i = monster.getProjectiles().size() - 1; i >= 0; i--) {
                Bullet bullet = monster.getProjectiles().get(i);
                if(!view.isInCamera(bullet.getPosition().x, bullet.getPosition().y)) {
                    monster.getProjectiles().remove(i);
                    continue;
                }
                Vector2 toAdd = bullet.getDirection().cpy().scl(delta * bullet.getSpeed());
                bullet.getPosition().add(toAdd);
                bullet.getRectangle().set(Bullet.createRectangle(
                    bullet.getPosition().x, bullet.getPosition().y,view.getBulletWidth(), view.getBulletHeight()));

                if(bullet.getRectangle().overlaps(Game.getInstance().getPlayer().getCollider())) {
                    Game.getInstance().getPlayer().damage();
                    monster.getProjectiles().remove(i);
                }
            }

            if(monster.getType() == MonsterTypes.Eyebat) {
                Vector2 dis = playerPosition.cpy().sub(position);
                if(dis.len() < Gdx.graphics.getWidth() / 3.5f &&
                    monster.getTimeElapsedFromLastShot() > monster.getShotTime()) {
                    monster.addBullet(new Bullet(position.cpy(), dis.nor()));
                    monster.setTimeElapsedFromLastShot(0);
                }
            }else if(monster.getType() == MonsterTypes.Elder) {
                Vector2 dis = playerPosition.cpy().sub(position);
                if(monster.isWalking()) {
                    if(dis.len() < Gdx.graphics.getWidth() / 6f){
                        monster.setTimer(0);
                        monster.setWalking(false);
                        monster.setRaging(true);
                        monster.setDirection(playerPosition.cpy().sub(position).nor());
                    }
                }else if(monster.isRaging()) {
                    if(monster.getTimer() >= 1) {
                        monster.setTimer(0);
                        monster.setRaging(false);
                        monster.setAttacking(true);
                    }
                    continue;
                }else {
                    nextPosition.set(
                        monster.getDirection().cpy().scl(delta * monster.getMoveSpeedWhileAttacking()).add(position));
                    if(monster.getTimer() >= 1) {
                        monster.setTimer(0);
                        monster.setWalking(true);
                        monster.setAttacking(false);
                    }
                }

            }
            Player player = Game.getInstance().getPlayer();
            boolean overlapPlayer = nextRectangle.overlaps(player.getCollider());
            if(overlapPlayer && monster.getType() != MonsterTypes.Eyebat) {
                player.damage();
            }
            position.set(nextPosition);
            monster.setRectangle(nextRectangle);
        }
    }

    private void spawnElder() {
        Game.getInstance().setElectricWallRadius(Gdx.graphics.getWidth());
        Game.getInstance().setElectricWallStartPoint(Game.getInstance().getPlayer().getPosition().cpy());

        Vector2 position = getRandomPosition();
        Monster monster = new Monster(MonsterTypes.Elder, position);
        monster.setRectangle(Monster.createRectangle(position.x, position.y, view.getElderWidthHeight().x
            , view.getElderWidthHeight().y));
        Game.getInstance().addMonster(monster);
    }

    private void spawnEye() {
        int count = (int) Math.floor((4 * Game.getInstance().getTimeElapsed() - Game.getInstance().getTimeControl()*60)/30 + 1);
        for(int i = 0; i < count; i++) {
            Vector2 position = getRandomPosition();
            Monster monster = new Monster(MonsterTypes.Eyebat, position);
            monster.setRectangle(Monster.createRectangle(position.x, position.y, view.getMonsterWidthHeight().x
                , view.getMonsterWidthHeight().y));
            Game.getInstance().addMonster(monster);
        }
    }

    private void spawnBrain() {
        int count = (int) Math.ceil(Math.sqrt(Game.getInstance().getTimeElapsed()) / 5f);
        for(int i = 0; i < count; i++) {
            Vector2 position = getRandomPosition();
            Monster monster = new Monster(MonsterTypes.BrainMonster, position);
            monster.setRectangle(Monster.createRectangle(position.x, position.y, view.getMonsterWidthHeight().x
                , view.getMonsterWidthHeight().y));
            Game.getInstance().addMonster(monster);
        }
    }

    private Monster findNearestMonster() {
        Vector2 pos = Game.getInstance().getPlayer().getPosition();
        Monster nearest = null;
        float nearDis = 0;
        for(Monster monster : Game.getInstance().getMonsters()) {
            Vector2 dis = pos.cpy().sub(monster.getPosition());
            if(nearest == null || dis.len() < nearDis) {
                nearest = monster;
                nearDis = dis.len();
            }
        }
        return nearest;
    }
    private Vector2 getRandomPosition() {
        Vector2 playerPosition = Game.getInstance().getPlayer().getPosition();
        float radius = Gdx.graphics.getWidth();
        float radian = (float) (Math.random() * 2 * Math.PI);
        return new Vector2((float) (radius * Math.cos(radian)) + playerPosition.x,
            (float) (radius * Math.sin(radian)) + playerPosition.y);
    }
}
