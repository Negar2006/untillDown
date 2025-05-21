package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    private final String character1_idle0 = "1/Idle_0.png";
    private final String character1_idle1 = "1/Idle_1.png";
    private final String character1_idle2 = "1/Idle_2.png";
    private final String character1_idle3 = "1/Idle_3.png";
    private final String character1_idle4 = "1/Idle_4.png";
    private final String character1_idle5 = "1/Idle_5.png";
    private final Texture character1_idle0_tex = new Texture(character1_idle0);
    private final Texture character1_idle1_tex = new Texture(character1_idle1);
    private final Texture character1_idle2_tex = new Texture(character1_idle2);
    private final Texture character1_idle3_tex = new Texture(character1_idle3);
    private final Texture character1_idle4_tex = new Texture(character1_idle4);
    private final Texture character1_idle5_tex = new Texture(character1_idle5);
    private final Animation<Texture> character1_idle_frames = new Animation<>(0.1f, character1_idle0_tex, character1_idle1_tex, character1_idle2_tex, character1_idle3_tex, character1_idle4_tex, character1_idle5_tex);

    private final String monster0 = "BrainMonster/BrainMonster_0.png";
    private final String monster1 = "BrainMonster/BrainMonster_1.png";
    private final String monster2 = "BrainMonster/BrainMonster_2.png";
    private final String monster3 = "BrainMonster/BrainMonster_3.png";

    private final Texture monster0_tex = new Texture(monster0);
    private final Texture monster1_tex = new Texture(monster1);
    private final Texture monster2_tex = new Texture(monster2);
    private final Texture monster3_tex = new Texture(monster3);

    private final Animation<Texture> monster_idle_animation = new Animation<>(0.2f, monster0_tex, monster1_tex, monster2_tex, monster3_tex);

    private final String Tree0 = "Tree/TreeMonster_0.png";
    private final String Tree1 = "Tree/TreeMonster_1.png";
    private final String Tree2 = "Tree/TreeMonster_2.png";
    private final String Tree3 = "Tree/TreeMonster_3.png";

    private final Texture Tree0_tex = new Texture(Tree0);
    private final Texture Tree1_tex = new Texture(Tree1);
    private final Texture Tree2_tex = new Texture(Tree2);
    private final Texture Tree3_tex = new Texture(Tree3);

    private final Animation<Texture> Tree_idle_animation = new Animation<>(0.2f, Tree0_tex, Tree1_tex, Tree2_tex, Tree3_tex);

    private final String smg = "smg/SMGStill.png";
    private final Texture smgTexture = new Texture(smg);

    private final String bullet = "bullet.png";


    private GameAssetManager(){

    }

    public static GameAssetManager getGameAssetManager(){
        if (gameAssetManager == null){
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    public Skin getSkin() {
        return skin;
    }

    public Animation<Texture> getCharacter1_idle_animation() {
        return character1_idle_frames;
    }

    public String getCharacter1_idle0(){
        return character1_idle0;
    }

    public Texture getSmgTexture(){
        return smgTexture;
    }

    public String getSmg(){
        return smg;
    }

    public String getBullet(){
        return bullet;
    }

    public Animation<Texture> getMonsterIdleAnimation() {
        return monster_idle_animation;
    }

    public Animation<Texture> getCharacter1_idle_frames() {
        return character1_idle_frames;
    }

    public Animation<Texture> getMonster_idle_animation() {
        return monster_idle_animation;
    }

    public Animation<Texture> getTree_idle_animation() {
        return Tree_idle_animation;
    }

    public static void setGameAssetManager(GameAssetManager gameAssetManager) {
        GameAssetManager.gameAssetManager = gameAssetManager;
    }
}
