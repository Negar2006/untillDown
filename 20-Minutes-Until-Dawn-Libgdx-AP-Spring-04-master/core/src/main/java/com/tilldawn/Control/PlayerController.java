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

    public PlayerController(Player player, String characterName) {
        this.player = player;
        Animation<Texture> rawAnim = GameAssetManager.getGameAssetManager().getCharacter1_idle_animation();
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
}
