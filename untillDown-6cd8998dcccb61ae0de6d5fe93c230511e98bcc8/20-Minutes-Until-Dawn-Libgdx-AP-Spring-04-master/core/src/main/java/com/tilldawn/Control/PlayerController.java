package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Player;

public class PlayerController {
    private Player player;
    private Animation<TextureRegion> playerAnimation;
    private float stateTime = 0f;
    private Animation<Texture> rawAnim ;
    private GameController gameController;

    public PlayerController(Player player, String characterName, GameController gameController) {
        this.gameController = gameController;
        this.player = player;
        player.setPlayerController(this);
        switch (characterName) {
            case "SHANA":
                rawAnim = GameAssetManager.getGameAssetManager().getCharacter1_idle_animation();
                player.setPlayerHealth(40);
                player.setSpeed(80f);
                break;
            case "SCARLET":
                rawAnim = GameAssetManager.getGameAssetManager().getCharacter5_idle_animation();
                player.setPlayerHealth(30);
                player.setSpeed(100f);
                break;
            case "LILITH":
                rawAnim = GameAssetManager.getGameAssetManager().getCharacter2_idle_animation();
                player.setPlayerHealth(50);
                player.setSpeed(60f);
                break;
            case "DASHER":
                rawAnim = GameAssetManager.getGameAssetManager().getCharacter3_idle_animation();
                player.setPlayerHealth(20);
                player.setSpeed(200f);
                break;
            case "DIAMOND":
                rawAnim = GameAssetManager.getGameAssetManager().getCharacter4_idle_animation();
                player.setPlayerHealth(70);
                player.setSpeed(20f);
                break;
        }
        Array<TextureRegion> frames = new Array<>();
        for (Texture t : rawAnim.getKeyFrames()) {
            frames.add(new TextureRegion(t));
        }
        this.playerAnimation = new Animation<>(rawAnim.getFrameDuration(), frames, Animation.PlayMode.LOOP);
    }

    public void update(float delta) {
        float dx = 0, dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += player.getSpeed() * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= player.getSpeed() * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= player.getSpeed() * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += player.getSpeed() * delta;

        player.updatePosition(dx, dy);

        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = playerAnimation.getKeyFrame(stateTime, true);
        Main.getBatch().draw(currentFrame, player.getPosX(), player.getPosY(),
            player.getPlayerSprite().getWidth(), player.getPlayerSprite().getHeight());

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}


