package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.enums.Ability;
import com.tilldawn.Model.DamagePopup;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameView extends InputAdapter implements Screen {
    private Stage stage;
    private Skin skin;
    private GameController controller;
    private Array<DamagePopup> damagePopups = new Array<>();
    private ImageButton pauseButton;
    private Animation<TextureRegion> treeAnimation;
    private Animation<TextureRegion> playerAnimation;
    private float stateTime;
    private GameAssetManager assetManager;
    private Label timerLabel;
    private Label healthLabel;
    private Label killsLabel;
    private Label scoreLabel;
    private Label speedLable;
    private String nameOfTexture;
    private Label levelLabel;
    private Label expLable;
    private ProgressBar expProgressBar;
    private float firstHearth;
    private ShaderProgram grayscaleShader;
    private boolean grayscaleEnabled = false;

    Texture emptyHeartTexture = new Texture(Gdx.files.internal("heart/HeartAnimation_3.png"));
    Texture fullHeartTexture1 = new Texture(Gdx.files.internal("heart/HeartAnimation_1.png"));
    Texture fullHeartTexture2 = new Texture(Gdx.files.internal("heart/HeartAnimation_2.png"));

    // Create TextureRegions for full and half heart
    TextureRegion[] fullHeartFrames = {
        new TextureRegion(fullHeartTexture1),
        new TextureRegion(fullHeartTexture2)
    };

    Animation<TextureRegion> fullHeartAnimation = new Animation<>(0.5f, fullHeartFrames);

    public GameView(GameController controller, Skin skin, Texture playerTexture, String name) {
        this.skin = skin;
        this.controller = controller;
        controller.setView(this, playerTexture, name);
        this.assetManager = GameAssetManager.getGameAssetManager();
        this.stateTime = 0f;
        this.nameOfTexture = name;
        this.firstHearth = controller.getPlayerController().getPlayer().getPlayerHealth();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        String vert = Gdx.files.internal("shaders/grayscale.glsl").readString();
        String frag = Gdx.files.internal("shaders/grayscale.frag").readString();
        grayscaleShader = new ShaderProgram(vert, frag);
        if (!grayscaleShader.isCompiled()) {
            Gdx.app.error("Shader", "Shader compile error: " + grayscaleShader.getLog());
        }


        timerLabel = new Label("Time Left: 10:00", skin);
        timerLabel.setPosition(10, Gdx.graphics.getHeight() - 30);
        stage.addActor(timerLabel);

        healthLabel = new Label("HP: " + controller.getPlayerHealth(), skin);
        healthLabel.setPosition(10, Gdx.graphics.getHeight() - 60);
        stage.addActor(healthLabel);

        killsLabel = new Label("Kills: " + controller.getKillCount(), skin);
        killsLabel.setPosition(10, Gdx.graphics.getHeight() - 90);
        stage.addActor(killsLabel);

        scoreLabel = new Label("Score: " + controller.getPlayerController().getPlayer().getScore(), skin);
        scoreLabel.setPosition(10, Gdx.graphics.getHeight() - 120);
        stage.addActor(scoreLabel);

        speedLable = new Label("Speed: " + controller.getPlayerController().getPlayer().getSpeed(), skin);
        speedLable.setPosition(Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() - 40);
        stage.addActor(speedLable);

        // Create Level label
        levelLabel = new Label("Level: " + controller.getPlayerController().getPlayer().getLevel(), skin);
        levelLabel.setPosition(Gdx.graphics.getWidth() - 250, Gdx.graphics.getHeight() - 40);
        stage.addActor(levelLabel);

        expLable = new Label("exp: " + controller.getPlayerController().getPlayer().getExp(), skin);
        expLable.setPosition(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 40);
        stage.addActor(expLable);

        expProgressBar = new ProgressBar(0, 100, 1, false, skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class));
        expProgressBar.setValue(0);
        expProgressBar.setAnimateDuration(0.25f);
        expProgressBar.setSize(300, 10);
        expProgressBar.setPosition(stage.getWidth() - 320, stage.getHeight() - 80);

        // Change the color of the progress bar when full
        expProgressBar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (expProgressBar.getValue() == expProgressBar.getMaxValue()) {
                    expProgressBar.setColor(Color.RED);
                }
            }
        });

        stage.addActor(expProgressBar);

        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new com.badlogic.gdx.graphics.Texture(Gdx.files.internal("pause_icon.png")))));
        pauseButton.setPosition(Gdx.graphics.getWidth() - 60, 10);  // Positioned at bottom-right
        pauseButton.setSize(50, 50);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.pauseGame();
            }
        });
        stage.addActor(pauseButton);

        Animation<com.badlogic.gdx.graphics.Texture> rawPlayerAnim = assetManager.getCharacter1_idle_animation();
        Array<TextureRegion> playerFrames = new Array<>();
        for (com.badlogic.gdx.graphics.Texture t : rawPlayerAnim.getKeyFrames()) {
            playerFrames.add(new TextureRegion(t));
        }
        playerAnimation = new Animation<>(rawPlayerAnim.getFrameDuration(), playerFrames, Animation.PlayMode.LOOP);

        InputMultiplexer multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stateTime += delta;
        if (grayscaleEnabled && grayscaleShader.isCompiled()) {
            Main.getBatch().setShader(grayscaleShader);
        } else {
            Main.getBatch().setShader(null);
        }
        Main.getBatch().begin();

        long remainingMillis = controller.getRemainingTimeMillis();
        long minutes = remainingMillis / 60000;
        long seconds = (remainingMillis % 60000) / 1000;
        timerLabel.setText(String.format("Time Left: %02d:%02d", minutes, seconds));

        healthLabel.setText("HP: " + controller.getPlayerHealth());
        killsLabel.setText("Kills: " + controller.getKillCount());
        scoreLabel.setText("Score: " + controller.getPlayerController().getPlayer().getScore());
        levelLabel.setText("Level: " + controller.getPlayerController().getPlayer().getLevel());
        expLable.setText("exp: " + controller.getPlayerController().getPlayer().getExp());
        speedLable.setText("Speed: " + controller.getPlayerController().getPlayer().getSpeed());

        expProgressBar.setRange(0, controller.getPlayerController().getPlayer().getLevel() * 20);
        expProgressBar.setValue(controller.getPlayerController().getPlayer().getExp());
        updateHearts();
        controller.updateGame();

        for (int i = damagePopups.size - 1; i >= 0; i--) {
            DamagePopup popup = damagePopups.get(i);
            popup.update(delta);
            popup.draw(Main.getBatch());
            if (popup.isFinished()) {
                damagePopups.removeIndex(i);
            }
        }

        displayGainedAbilities();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        Main.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.N) {
            controller.returnToMainMenu();
            return true;
        }
        if (keycode == Input.Keys.SPACE) {
            controller.toggleAutoAim();
            return true;
        }
        if (keycode == Input.Keys.K) {
            controller.killMonster();
            return true;
        }
        if (keycode == Input.Keys.E) {
            controller.killEyeBat();
            return true;
        }
        if (keycode == Input.Keys.T) {
            controller.addTime(60000);
            return true;
        }
        if (keycode == Input.Keys.B) {
            int newEXP = controller.getPlayerController().getPlayer().getLevel() * 20;
            controller.getPlayerController().getPlayer().setExp(newEXP);
            return true;
        }

        if (keycode == Input.Keys.V) {
            if (controller.getPlayerController().getPlayer().hasAbility(Ability.VITALITY)) {
                float newHealth = controller.getPlayerController().getPlayer().getPlayerHealth() + 20;
                controller.getPlayerController().getPlayer().setPlayerHealth(newHealth);
                controller.getPlayerController().getPlayer().removeAbility(Ability.VITALITY);
            }
            return true;
        }

        if (keycode == Input.Keys.S) {
            Player player = controller.getPlayerController().getPlayer();
            if (player.hasAbility(Ability.SPEEDY)) {
                // Double the player's speed
                player.setSpeed(player.getSpeed() * 2);

                float speedDuration = 10f;
                new Thread(() -> {
                    try {
                        Thread.sleep((long) (speedDuration * 1000));
                        player.setSpeed(player.getSpeed() / 2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                player.removeAbility(Ability.SPEEDY);
            }
            return true;
        }


        if (keycode == Input.Keys.A) {
            Player player = controller.getPlayerController().getPlayer();
            if (player.hasAbility(Ability.AMOCREASE)) {
                controller.getWeaponController().getWeapon().addAmmo(5);
                player.removeAbility(Ability.AMOCREASE);
            }
            return true;
        }

        if (keycode == Input.Keys.P) {
            Player player = controller.getPlayerController().getPlayer();

            if (player.hasAbility(Ability.PROCREASE)) {
                controller.getWeaponController().addProjectile(1);  // Assuming WeaponController has addProjectile method
                System.out.println("Procrease used! Projectiles increased.");
                player.removeAbility(Ability.PROCREASE);
            }
            return true;
        }

        if (keycode == Input.Keys.D) {
            Player player = controller.getPlayerController().getPlayer();
            if (player.hasAbility(Ability.DAMAGER)) {
                controller.getWeaponController().getWeapon().addDamage(1.25f);  // Assuming WeaponController has setWeaponDamage method
                System.out.println("Damager used! Weapon damage is now 125%.");
                player.removeAbility(Ability.DAMAGER);
            }
            return true;
        }
        if(keycode == Input.Keys.R) {
            controller.getWeaponController().getWeapon().setAmmo(controller.getWeaponController().getWeapon().getType().maxAmmo);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        controller.getWeaponController().handleWeaponShoot(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    public void addDamagePopup(float x, float y, String text) {
        damagePopups.add(new DamagePopup(x, y, text));
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public GameController getController() {
        return controller;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public Array<DamagePopup> getDamagePopups() {
        return damagePopups;
    }

    private void updateHearts() {
        int playerHealth = (int) controller.getPlayerHealth();
        int numberOfFullHearts = playerHealth / 20;
        int remainingHealth = playerHealth % 20;

        float heartX = healthLabel.getX() + healthLabel.getWidth() + 10;

        for (Actor actor : stage.getActors()) {
            if (actor instanceof Image) {
                actor.remove();
            }
        }

        for (int i = 0; i < numberOfFullHearts; i++) {
            Image fullHeart = new Image(fullHeartAnimation.getKeyFrame(stateTime, true));
            fullHeart.setPosition(heartX + i * 30, healthLabel.getY() - 5);
            stage.addActor(fullHeart);
        }

        if (remainingHealth > 0) {
            Image halfHeart = new Image(fullHeartAnimation.getKeyFrame(stateTime, true));
            halfHeart.setPosition(heartX + numberOfFullHearts * 30, healthLabel.getY() - 5);
            stage.addActor(halfHeart);
        }

        for (int i = numberOfFullHearts + (remainingHealth > 0 ? 1 : 0); i < 5; i++) {
            Image emptyHeart = new Image(new TextureRegion(emptyHeartTexture));
            emptyHeart.setPosition(heartX + i * 30, healthLabel.getY() - 5);
            stage.addActor(emptyHeart);
        }
    }

    public void showAbilitySelectionDialog() {
        Array<Ability> availableAbilities = new Array<>(Ability.values());
        Array<Ability> randomAbilities = new Array<>();

        for (int i = 0; i < 3; i++) {
            Ability ability;
            do {
                ability = availableAbilities.random(); // Get random ability
            } while (randomAbilities.contains(ability, false)); // Avoid duplicates

            randomAbilities.add(ability); // Add the ability to the list
        }

        Dialog abilityDialog = new Dialog("Choose an Ability", skin);
        abilityDialog.getContentTable().padTop(20);
        abilityDialog.setSize(400, 300);

        for (Ability ability : randomAbilities) {
            TextButton abilityButton = new TextButton(ability.getDescription(), skin);
            abilityButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    controller.getPlayerController().getPlayer().addAbility(ability);
                    abilityDialog.hide();

                }
            });
            abilityDialog.getContentTable().add(abilityButton).fillX().pad(10);
            abilityDialog.getContentTable().row();
        }

        abilityDialog.button("Close", true);
        abilityDialog.show(stage);
    }

    private void displayGainedAbilities() {
        // Get the player's gained abilities
        ArrayList<Ability> abilities = controller.getPlayerController().getPlayer().getGainAbilities();

        float xPosition = 10;
        float yPosition = Gdx.graphics.getHeight() - 150;
        float screenWidth = Gdx.graphics.getWidth();

        // Remove only the ability-related images and labels
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Image || actor instanceof Label) {
                // Check if the actor is related to abilities
                if (actor.getName() != null && actor.getName().equals("ability")) {
                    actor.remove();
                }
            }
        }

        // Create a HashMap to track the count of each ability
        HashMap<Ability, Integer> abilityCounts = new HashMap<>();
        for (Ability ability : abilities) {
            abilityCounts.put(ability, abilityCounts.getOrDefault(ability, 0) + 1);
        }

        for (Map.Entry<Ability, Integer> entry : abilityCounts.entrySet()) {
            Ability ability = entry.getKey();
            int count = entry.getValue();

            Image abilityImage = new Image(ability.getTexture());
            abilityImage.setPosition(xPosition, yPosition);
            abilityImage.setName("ability");
            stage.addActor(abilityImage);

            Label abilityLabel = new Label(String.valueOf(count), skin);
            abilityLabel.setPosition(xPosition + abilityImage.getWidth() + 5, yPosition);
            abilityLabel.setName("ability");
            stage.addActor(abilityLabel);
            xPosition += abilityImage.getWidth() + abilityLabel.getWidth() + 20;

            if (xPosition + 200 > screenWidth) {
                xPosition = 10;
                yPosition -= 50;
            }
        }
    }
    public void setGrayscale(boolean enable) {
        grayscaleEnabled = enable;
    }

}
