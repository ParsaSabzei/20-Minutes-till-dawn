package ap.project.controller;

import ap.project.model.*;
import ap.project.model.enums.Guns;
import ap.project.model.enums.MonsterTypes;
import ap.project.view.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class GunController {
    private GameView view;
    public GunController(GameView view) {
        this.view = view;
    }
    public void update(float delta){
        Game.getInstance().getPlayer().getGun().setTimeSpentFromLastShot(
            Game.getInstance().getPlayer().getGun().getTimeSpentFromLastShot() + delta);

        if(Game.getInstance().getPlayer().getGun().getReloadTimeRemain() > 0)
            Game.getInstance().getPlayer().getGun().setReloadTimeRemain(
                Game.getInstance().getPlayer().getGun().getReloadTimeRemain()-delta);

        for(int i = Game.getInstance().getBullets().size()-1; i >= 0; i--){
            Bullet bullet = Game.getInstance().getBullets().get(i);
            if(!view.isInCamera(bullet.getPosition().x, bullet.getPosition().y)) {
                Game.getInstance().getBullets().remove(i);
                continue;
            }
            boolean hitTree = false;
            Monster monsterHitted = null;
            for(Tree tree : Game.getInstance().getTrees().values()) {
                if(tree != null && tree.getRectangle().overlaps(bullet.getRectangle())) {
                    hitTree = true;
                }
            }
            for(Monster monster : Game.getInstance().getMonsters()) {
                if(monster.getRectangle().overlaps(bullet.getRectangle())) {
                    monsterHitted = monster;
                    break;
                }
            }
            if(hitTree || monsterHitted != null) {
                view.animations.add(new Anim(bullet.getPosition(), GameAssetManager.getInstance().explosionAnimation));
                Game.getInstance().getBullets().remove(i);
            }
            if(monsterHitted != null) {
                boolean dead = monsterHitted.damage(Game.getInstance().getPlayer().getGunDamage());
                if(dead) {
                    Game.getInstance().setTotalKills(Game.getInstance().getTotalKills()+1);
                    view.animations.add(new Anim(bullet.getPosition(), GameAssetManager.getInstance().monsterDeathAnimation));
                    Game.getInstance().getGems().add(new Gem(monsterHitted.getPosition()));
                    if(monsterHitted.getType() == MonsterTypes.Elder)
                        Game.getInstance().setElectricWallRadius(0);
                }else{
                    if(monsterHitted.getType() != MonsterTypes.Elder)
                        monsterHitted.getForces().add(new Force(200, bullet.getDirection().cpy(), 0.2f));
                }
            }
            Vector2 toAdd = bullet.getDirection().cpy().scl(delta * bullet.getSpeed());
            bullet.getPosition().add(toAdd);
            bullet.getRectangle().set(Bullet.createRectangle(
                bullet.getPosition().x, bullet.getPosition().y,view.getBulletWidth(), view.getBulletHeight()));
        }
    }

    public void shot() {
        Player player = Game.getInstance().getPlayer();
        if(player.getGun().getReloadTimeRemain() > 0.01f)
            return;
        if(player.getGun().getTimeSpentFromLastShot() < player.getGun().getFireRate())
            return;
        if(player.getGun().getAmmo() == 0) {
            if(App.getInstance().getSettings().isAutoReloadOn())
                player.getGun().reload();
            return;
        }
        player.getGun().setTimeSpentFromLastShot(0);
        int projectile = player.getGun().getProjectile();
        float rad = (float) Math.toRadians((float) (5 / Math.log(projectile+1)));

        player.getGun().setAmmo(player.getGun().getAmmo() - 1);
        SfxManager.playShot();
        Vector2 pos = player.getPosition().cpy();
        Vector2 direction = new Vector2(Gdx.input.getX(), -Gdx.input.getY());
        direction.sub(Gdx.graphics.getWidth()/2f, -Gdx.graphics.getHeight()/2f);
        direction.nor();

        for(int i = -(projectile/2); i <= projectile/2; i++) {
            if((projectile%2) == 0 && i == 0)
                continue;
            float angle = rad * i;
            float alpha = (float) Math.atan2(direction.y, direction.x);
            Vector2 dir = new Vector2((float) Math.cos(angle + alpha), (float) Math.sin(angle + alpha));
            Bullet bullet = new Bullet(pos.cpy(), dir);
            bullet.setRectangle(Bullet.createRectangle(pos.x, pos.y, view.getBulletWidth(), view.getBulletHeight()));
            Game.getInstance().addBullet(bullet);

            if(player.getGun().getType() == Guns.SmgDual) {
                bullet = new Bullet(pos.cpy().add(new Vector2(
                    new Random().nextInt(100)-50, new Random().nextInt(100)-50)), dir.cpy());
                bullet.setRectangle(Bullet.createRectangle(pos.x, pos.y, view.getBulletWidth(), view.getBulletHeight()));
                Game.getInstance().addBullet(bullet);
            }
        }

    }
}
