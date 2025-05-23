package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    private final String character1_idle0 = "SHANA/Idle_0.png";
    private final String character1_idle1 = "SHANA/Idle_1.png";
    private final String character1_idle2 = "SHANA/Idle_2.png";
    private final String character1_idle3 = "SHANA/Idle_3.png";
    private final String character1_idle4 = "SHANA/Idle_4.png";
    private final String character1_idle5 = "SHANA/Idle_5.png";
    private final Texture character1_idle0_tex = new Texture(character1_idle0);
    private final Texture character1_idle1_tex = new Texture(character1_idle1);
    private final Texture character1_idle2_tex = new Texture(character1_idle2);
    private final Texture character1_idle3_tex = new Texture(character1_idle3);
    private final Texture character1_idle4_tex = new Texture(character1_idle4);
    private final Texture character1_idle5_tex = new Texture(character1_idle5);
    private final Animation<Texture> character1_idle_frames = new Animation<>(0.1f, character1_idle0_tex, character1_idle1_tex, character1_idle2_tex, character1_idle3_tex, character1_idle4_tex, character1_idle5_tex);

    private final String character2_idle0 = "LILITH/Idle_0.png";
    private final String character2_idle1 = "LILITH/Idle_1.png";
    private final String character2_idle2 = "LILITH/Idle_2.png";
    private final String character2_idle3 = "LILITH/Idle_3.png";
    private final String character2_idle4 = "LILITH/Idle_4.png";
    private final String character2_idle5 = "LILITH/Idle_5.png";
    private final Texture character2_idle0_tex = new Texture(character2_idle0);
    private final Texture character2_idle1_tex = new Texture(character2_idle1);
    private final Texture character2_idle2_tex = new Texture(character2_idle2);
    private final Texture character2_idle3_tex = new Texture(character2_idle3);
    private final Texture character2_idle4_tex = new Texture(character2_idle4);
    private final Texture character2_idle5_tex = new Texture(character2_idle5);
    private final Animation<Texture> character2_idle_frames = new Animation<>(0.1f, character2_idle0_tex, character2_idle1_tex, character2_idle2_tex, character2_idle3_tex, character2_idle4_tex, character2_idle5_tex);

    private final String character3_idle0 = "DASHER/Idle_0.png";
    private final String character3_idle1 = "DASHER/Idle_1.png";
    private final String character3_idle2 = "DASHER/Idle_2.png";
    private final String character3_idle3 = "DASHER/Idle_3.png";
    private final String character3_idle4 = "DASHER/Idle_4.png";
    private final Texture character3_idle0_tex = new Texture(character3_idle0);
    private final Texture character3_idle1_tex = new Texture(character3_idle1);
    private final Texture character3_idle2_tex = new Texture(character3_idle2);
    private final Texture character3_idle3_tex = new Texture(character3_idle3);
    private final Texture character3_idle4_tex = new Texture(character3_idle4);
    private final Animation<Texture> character3_idle_frames = new Animation<>(0.1f, character3_idle0_tex, character3_idle1_tex, character3_idle2_tex, character3_idle3_tex, character3_idle4_tex);

    private final String character4_idle0 = "DIAMOND/Idle_0.png";
    private final String character4_idle1 = "DIAMOND/Idle_1.png";
    private final String character4_idle2 = "DIAMOND/Idle_2.png";
    private final String character4_idle3 = "DIAMOND/Idle_3.png";
    private final String character4_idle4 = "DIAMOND/Idle_4.png";
    private final String character4_idle5 = "DIAMOND/Idle_5.png";
    private final Texture character4_idle0_tex = new Texture(character4_idle0);
    private final Texture character4_idle1_tex = new Texture(character4_idle1);
    private final Texture character4_idle2_tex = new Texture(character4_idle2);
    private final Texture character4_idle3_tex = new Texture(character4_idle3);
    private final Texture character4_idle4_tex = new Texture(character4_idle4);
    private final Texture character4_idle5_tex = new Texture(character4_idle5);
    private final Animation<Texture> character4_idle_frames = new Animation<>(0.1f, character4_idle0_tex, character4_idle1_tex, character4_idle2_tex, character4_idle3_tex, character4_idle4_tex, character4_idle5_tex);


    private final String character5_idle0 = "SCARLET/Idle_0.png";
    private final String character5_idle1 = "SCARLET/Idle_1.png";
    private final String character5_idle2 = "SCARLET/Idle_2.png";
    private final String character5_idle3 = "SCARLET/Idle_3.png";
    private final String character5_idle4 = "SCARLET/Idle_4.png";
    private final String character5_idle5 = "SCARLET/Idle_5.png";
    private final Texture character5_idle0_tex = new Texture(character5_idle0);
    private final Texture character5_idle1_tex = new Texture(character5_idle1);
    private final Texture character5_idle2_tex = new Texture(character5_idle2);
    private final Texture character5_idle3_tex = new Texture(character5_idle3);
    private final Texture character5_idle4_tex = new Texture(character5_idle4);
    private final Texture character5_idle5_tex = new Texture(character5_idle5);
    private final Animation<Texture> character5_idle_frames = new Animation<>(0.1f, character5_idle0_tex, character5_idle1_tex, character5_idle2_tex, character5_idle3_tex, character5_idle4_tex, character5_idle5_tex);


    private final String monster0 = "TentacleMonster/TentacleMonster_0.png";
    private final String monster1 = "TentacleMonster/TentacleMonster_1.png";
    private final String monster2 = "TentacleMonster/TentacleMonster_2.png";
    private final String monster3 = "TentacleMonster/TentacleMonster_3.png";

    private final Texture monster0_tex = new Texture(monster0);
    private final Texture monster1_tex = new Texture(monster1);
    private final Texture monster2_tex = new Texture(monster2);
    private final Texture monster3_tex = new Texture(monster3);

    private final Animation<Texture> monster_idle_animation = new Animation<>(0.2f, monster0_tex, monster1_tex, monster2_tex, monster3_tex);


    private final String EyeBat0 = "EyeBat/EyeBat_0.png";
    private final String EyeBat1 = "EyeBat/EyeBat_1.png";
    private final String EyeBat2 = "EyeBat/EyeBat_2.png";
    private final String EyeBat3 = "EyeBat/EyeBat_3.png";

    private final Texture EyeBat0_tex = new Texture(EyeBat0);
    private final Texture EyeBat1_tex = new Texture(EyeBat1);
    private final Texture EyeBat2_tex = new Texture(EyeBat2);
    private final Texture EyeBat3_tex = new Texture(EyeBat3);

    private final Animation<Texture> EyeBat_idle_animation = new Animation<>(0.2f, EyeBat0_tex, EyeBat1_tex, EyeBat2_tex, EyeBat3_tex);

    private final String Tree0 = "Tree/TreeMonster_0.png";
    private final String Tree1 = "Tree/TreeMonster_1.png";
    private final String Tree2 = "Tree/TreeMonster_2.png";
    private final String Tree3 = "Tree/TreeMonster_3.png";

    private final Texture Tree0_tex = new Texture(Tree0);
    private final Texture Tree1_tex = new Texture(Tree1);
    private final Texture Tree2_tex = new Texture(Tree2);
    private final Texture Tree3_tex = new Texture(Tree3);

    private final Animation<Texture> Tree_idle_animation = new Animation<>(0.2f, Tree0_tex, Tree1_tex, Tree2_tex, Tree3_tex);

    private final String Boss0 = "Boss/HasturBoss_0.png";
    private final String Boss1 = "Boss/HasturBoss_1.png";
    private final String Boss2 = "Boss/HasturBoss_2.png";
    private final String Boss3 = "Boss/HasturBoss_3.png";
    private final String Boss4 = "Boss/HasturBoss_4.png";

    private final Texture Boss0_tex = new Texture(Boss0);
    private final Texture Boss1_tex = new Texture(Boss1);
    private final Texture Boss2_tex = new Texture(Boss2);
    private final Texture Boss3_tex = new Texture(Boss3);
    private final Texture Boss4_tex = new Texture(Boss4);

    private final Animation<Texture> Boss_idle_animation = new Animation<>(0.2f, Boss0_tex, Boss1_tex, Boss2_tex, Boss3_tex, Boss4_tex);

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

    public Animation<Texture> getBoss_idle_animation() {
        return Boss_idle_animation;
    }

    public String getEyeBat1() {
        return EyeBat1;
    }

    public String getEyeBat2() {
        return EyeBat2;
    }

    public String getEyeBat3() {
        return EyeBat3;
    }

    public Texture getEyeBat0_tex() {
        return EyeBat0_tex;
    }

    public Texture getEyeBat1_tex() {
        return EyeBat1_tex;
    }

    public Texture getEyeBat2_tex() {
        return EyeBat2_tex;
    }

    public Texture getEyeBat3_tex() {
        return EyeBat3_tex;
    }

    public String getBoss0() {
        return Boss0;
    }

    public String getBoss1() {
        return Boss1;
    }

    public String getBoss2() {
        return Boss2;
    }

    public String getBoss3() {
        return Boss3;
    }

    public String getBoss4() {
        return Boss4;
    }

    public Texture getBoss0_tex() {
        return Boss0_tex;
    }

    public Texture getBoss1_tex() {
        return Boss1_tex;
    }

    public Texture getBoss2_tex() {
        return Boss2_tex;
    }

    public Texture getBoss3_tex() {
        return Boss3_tex;
    }

    public Texture getBoss4_tex() {
        return Boss4_tex;
    }

    public Animation<Texture> getCharacter2_idle_frames() {
        return character2_idle_frames;
    }

    public Animation<Texture> getCharacter3_idle_frames() {
        return character3_idle_frames;
    }

    public Animation<Texture> getCharacter4_idle_frames() {
        return character4_idle_frames;
    }

    public Animation<Texture> getCharacter5_idle_frames() {
        return character5_idle_frames;
    }

    public String getCharacter1_idle0(){
        return character1_idle0;
    }

    public String getCharacter1_idle1() {
        return character1_idle1;
    }

    public String getCharacter1_idle2() {
        return character1_idle2;
    }

    public String getCharacter1_idle3() {
        return character1_idle3;
    }

    public String getCharacter1_idle4() {
        return character1_idle4;
    }

    public String getCharacter1_idle5() {
        return character1_idle5;
    }

    public Texture getCharacter1_idle0_tex() {
        return character1_idle0_tex;
    }

    public Texture getCharacter1_idle1_tex() {
        return character1_idle1_tex;
    }

    public Texture getCharacter1_idle2_tex() {
        return character1_idle2_tex;
    }

    public Texture getCharacter1_idle3_tex() {
        return character1_idle3_tex;
    }

    public Texture getCharacter1_idle4_tex() {
        return character1_idle4_tex;
    }

    public Texture getCharacter1_idle5_tex() {
        return character1_idle5_tex;
    }

    public String getCharacter2_idle0() {
        return character2_idle0;
    }

    public String getCharacter2_idle1() {
        return character2_idle1;
    }

    public String getCharacter2_idle2() {
        return character2_idle2;
    }

    public String getCharacter2_idle3() {
        return character2_idle3;
    }

    public String getCharacter2_idle4() {
        return character2_idle4;
    }

    public String getCharacter2_idle5() {
        return character2_idle5;
    }

    public Texture getCharacter2_idle0_tex() {
        return character2_idle0_tex;
    }

    public Texture getCharacter2_idle1_tex() {
        return character2_idle1_tex;
    }

    public Texture getCharacter2_idle2_tex() {
        return character2_idle2_tex;
    }

    public Texture getCharacter2_idle3_tex() {
        return character2_idle3_tex;
    }

    public Texture getCharacter2_idle4_tex() {
        return character2_idle4_tex;
    }

    public Texture getCharacter2_idle5_tex() {
        return character2_idle5_tex;
    }

    public String getCharacter3_idle0() {
        return character3_idle0;
    }

    public String getCharacter3_idle1() {
        return character3_idle1;
    }

    public String getCharacter3_idle2() {
        return character3_idle2;
    }

    public String getCharacter3_idle3() {
        return character3_idle3;
    }

    public String getCharacter3_idle4() {
        return character3_idle4;
    }

    public Texture getCharacter3_idle0_tex() {
        return character3_idle0_tex;
    }

    public Texture getCharacter3_idle1_tex() {
        return character3_idle1_tex;
    }

    public Texture getCharacter3_idle2_tex() {
        return character3_idle2_tex;
    }

    public Texture getCharacter3_idle3_tex() {
        return character3_idle3_tex;
    }

    public Texture getCharacter3_idle4_tex() {
        return character3_idle4_tex;
    }

    public String getCharacter4_idle0() {
        return character4_idle0;
    }

    public String getCharacter4_idle1() {
        return character4_idle1;
    }

    public String getCharacter4_idle2() {
        return character4_idle2;
    }

    public String getCharacter4_idle3() {
        return character4_idle3;
    }

    public String getCharacter4_idle4() {
        return character4_idle4;
    }

    public String getCharacter4_idle5() {
        return character4_idle5;
    }

    public Texture getCharacter4_idle0_tex() {
        return character4_idle0_tex;
    }

    public Texture getCharacter4_idle1_tex() {
        return character4_idle1_tex;
    }

    public Texture getCharacter4_idle2_tex() {
        return character4_idle2_tex;
    }

    public Texture getCharacter4_idle3_tex() {
        return character4_idle3_tex;
    }

    public Texture getCharacter4_idle4_tex() {
        return character4_idle4_tex;
    }

    public Texture getCharacter4_idle5_tex() {
        return character4_idle5_tex;
    }

    public String getCharacter5_idle0() {
        return character5_idle0;
    }

    public String getCharacter5_idle1() {
        return character5_idle1;
    }

    public String getCharacter5_idle2() {
        return character5_idle2;
    }

    public String getCharacter5_idle3() {
        return character5_idle3;
    }

    public String getCharacter5_idle4() {
        return character5_idle4;
    }

    public String getCharacter5_idle5() {
        return character5_idle5;
    }

    public Texture getCharacter5_idle0_tex() {
        return character5_idle0_tex;
    }

    public Texture getCharacter5_idle1_tex() {
        return character5_idle1_tex;
    }

    public Texture getCharacter5_idle2_tex() {
        return character5_idle2_tex;
    }

    public Texture getCharacter5_idle3_tex() {
        return character5_idle3_tex;
    }

    public Texture getCharacter5_idle4_tex() {
        return character5_idle4_tex;
    }

    public Texture getCharacter5_idle5_tex() {
        return character5_idle5_tex;
    }

    public String getMonster0() {
        return monster0;
    }

    public String getMonster1() {
        return monster1;
    }

    public String getMonster2() {
        return monster2;
    }

    public String getMonster3() {
        return monster3;
    }

    public Texture getMonster0_tex() {
        return monster0_tex;
    }

    public Texture getMonster1_tex() {
        return monster1_tex;
    }

    public Texture getMonster2_tex() {
        return monster2_tex;
    }

    public Texture getMonster3_tex() {
        return monster3_tex;
    }

    public String getTree0() {
        return Tree0;
    }

    public String getTree1() {
        return Tree1;
    }

    public String getTree2() {
        return Tree2;
    }

    public String getTree3() {
        return Tree3;
    }

    public Texture getTree0_tex() {
        return Tree0_tex;
    }

    public Texture getTree1_tex() {
        return Tree1_tex;
    }

    public Texture getTree2_tex() {
        return Tree2_tex;
    }

    public Texture getTree3_tex() {
        return Tree3_tex;
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

    public Animation<Texture> getCharacter2_idle_animation() {
        return character2_idle_frames;
    }

    public Animation<Texture> getCharacter3_idle_animation() {
        return character3_idle_frames;
    }

    public Animation<Texture> getCharacter4_idle_animation() {
        return character4_idle_frames;
    }

    public Animation<Texture> getCharacter5_idle_animation() {
        return character5_idle_frames;
    }

    public Animation<Texture> getEyeBat_idle_animation() {
        return EyeBat_idle_animation;
    }

    public String getEyeBat0() {
        return EyeBat0;
    }
}
