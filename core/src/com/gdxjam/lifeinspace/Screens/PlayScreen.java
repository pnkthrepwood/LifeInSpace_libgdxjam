package com.gdxjam.lifeinspace.Screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gdxjam.lifeinspace.Components.DashComponent;
import com.gdxjam.lifeinspace.Components.RenderEffectComponent;
import com.gdxjam.lifeinspace.Components.ShieldComponent;
import com.gdxjam.lifeinspace.Components.WeaponSpecialComponent;
import com.gdxjam.lifeinspace.Factorys.BulletFactory;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent.TypeEntity;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;
import com.gdxjam.lifeinspace.Constants;
import com.gdxjam.lifeinspace.Factorys.EnemyFactory;
import com.gdxjam.lifeinspace.Factorys.PowerupFactory;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.Mappers;
import com.gdxjam.lifeinspace.PlayerManager;
import com.gdxjam.lifeinspace.Systems.BackgroundRenderSystem;
import com.gdxjam.lifeinspace.Systems.BulletSystem;
import com.gdxjam.lifeinspace.Systems.CollisionSystem;
import com.gdxjam.lifeinspace.Systems.EnemyBehaviourSystem;
import com.gdxjam.lifeinspace.Systems.MasterSystem;
import com.gdxjam.lifeinspace.Systems.MovementSystem;
import com.gdxjam.lifeinspace.Systems.RenderSystem;
import com.gdxjam.lifeinspace.Systems.WeaponSystem;
import com.gdxjam.lifeinspace.TextureManager;
import com.gdxjam.lifeinspace.Utils;
import com.gdxjam.lifeinspace.XBox360Pad;

/**
 * Created by threpwood on 20/12/2015.
 */
public class PlayScreen implements Screen {

    Gaem game;

    Entity ship;
    Sprite planet_spr;
    Sprite ship_spr;

    Sprite background_spr;

    Controller controller;

    BitmapFont font;
    BitmapFont font_big;

    Sprite gui_orbs_spr;
    Sprite weapon_special_item_spr;
    Sprite weapon_special_slot_spr;

    Sprite level_bar_spr;

    Sprite sprite;

    boolean is_paused = false;

    public PlayScreen(Gaem game)
    {
        this.game = game;

        PlayerManager.reset();

        game.engine.removeAllEntities();
        game.engine.clearPools();

        //CAREFUL: ORDER MATTERS!
        game.engine.addSystem(new MovementSystem(game.engine));
        game.engine.addSystem(new CollisionSystem(game.engine));
        game.engine.addSystem(new MasterSystem());
        game.engine.addSystem(new WeaponSystem());
        game.engine.addSystem(new BulletSystem(game.engine));
        game.engine.addSystem(new EnemyBehaviourSystem());
        game.engine.addSystem(new BackgroundRenderSystem(game.shapeRenderer));
        game.engine.addSystem(new RenderSystem(game.batch, game.cam));
        BulletFactory.gaem = this.game;
        EnemyFactory.gaem = this.game;
        PowerupFactory.gaem = this.game;


        Entity planet_bg = Gaem.engine.createEntity();
        planet_bg.add(new PositionComponent(0, -Constants.RES_Y / 2 + TextureManager.getTexture("planet_bg.png").getHeight() / 2));
        planet_bg.add(new VelocityComponent(0, -1.5f));
        planet_spr = new Sprite(TextureManager.getTexture("planet_bg.png"));
        planet_bg.add(new RenderComponent(planet_spr));
        planet_bg.add(new RenderEffectComponent(140, 5, 0.25f, 1, 1, true));
        game.engine.addEntity(planet_bg);


        Entity moon_bg = Gaem.engine.createEntity();
        moon_bg.add(new PositionComponent(
                        (Constants.RES_X / 2)+Constants.RES_X/3,
                        (Constants.RES_Y / 2)+Constants.RES_Y/3)
        );
        moon_bg.add(new VelocityComponent(-1.25f, -1.25f));
        Sprite moon_spr = new Sprite(TextureManager.getTexture("moon.png"));
        moon_bg.add(new RenderComponent(moon_spr));
        moon_bg.add(new RenderEffectComponent(280, 0, 3.0f, 1, 1, false));
        game.engine.addEntity(moon_bg);

        ship = Gaem.engine.createEntity();
        ship.add(new TypeComponent(TypeEntity.SHIP));
        ship.add(new PositionComponent());
        ship.add(new VelocityComponent(0, 0));
        ship_spr = new Sprite(TextureManager.getTexture("ship.png"));
        ship.add(new RenderComponent(ship_spr));
        ship.add(new WeaponComponent(WeaponComponent.WeaponType.PLAYER_WEAPON));

        ship.add(new CollisionComponent(24, 24));
        game.engine.addEntity(ship);
        PlayerManager.ship = ship;

        /*
        EnemyFactory.spawnSnakeEnemy(0, Constants.RES_Y / 2, 7);
        EnemyFactory.spawnShooterEnemy(
                MathUtils.random(-Constants.RES_X * 0.25f, Constants.RES_X * 0.25f),
                Constants.RES_Y / 2);
        */

        controller = (Controllers.getControllers().size > 0) ?
             Controllers.getControllers().first() : null;

        generateBackgroundScene();

        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("bitDarling.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
        font.getData().markupEnabled = true;

        font_big = new BitmapFont();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("bitDarling.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        font_big = generator.generateFont(parameter);
        font_big.getData().markupEnabled = true;

        gui_orbs_spr = new Sprite(TextureManager.getTexture("powerup.png"));
        gui_orbs_spr.setSize(16, 16);

        weapon_special_slot_spr = new Sprite(TextureManager.getTexture("hud_slot.png"));
        weapon_special_slot_spr.setSize(32, 32);

        weapon_special_item_spr = new Sprite(TextureManager.getTexture("hud_slot.png"));
        weapon_special_item_spr.setSize(32, 32);

        level_bar_spr = new Sprite(TextureManager.getTexture("hud_levelbar.png"));
        level_bar_spr.setSize(128, 32);

        sprite = new Sprite();
    }

    private void generateBackgroundScene()
    {
        Pixmap pixmap = new Pixmap((int)Constants.RES_X, (int)Constants.RES_Y, Pixmap.Format.RGB888);

        for (int i = 0; i < 1000; ++i)
        {
            pixmap.setColor(Color.WHITE);
            float rnd = MathUtils.random(0.0f, 1.0f);
            if (rnd < 0.05f)
            {
                if (rnd < 0.02f)
                    pixmap.setColor(Color.YELLOW);
                else if (rnd < 0.04f)
                    pixmap.setColor(Color.ORANGE);
                else
                    pixmap.setColor(Color.RED);
            }

            pixmap.drawCircle(
                    (int) MathUtils.random(0, Constants.RES_X),
                    (int) MathUtils.random(0, Constants.RES_Y),
                    1
            );
        }

        background_spr = new Sprite(new Texture(pixmap));
    }

    public void updateInput()
    {
        PositionComponent shipPos = Mappers.position.get(ship);
        VelocityComponent shipVel = Mappers.velocity.get(ship);
        shipVel.x = 0;
        shipVel.y = 0;

        if (controller != null)
        {
            if (Math.abs(controller.getAxis(XBox360Pad.AXIS_LEFT_X)) > 0.2)
                shipVel.x = controller.getAxis(XBox360Pad.AXIS_LEFT_X)* Constants.RES_X/8 * PlayerManager.ship_speed;

            if (Math.abs(controller.getAxis(XBox360Pad.AXIS_LEFT_Y)) > 0.2)
                shipVel.y = -controller.getAxis(XBox360Pad.AXIS_LEFT_Y)* Constants.RES_Y/8 * PlayerManager.ship_speed;

            //NEW BULLET
            if (controller.getAxis(XBox360Pad.AXIS_RIGHT_TRIGGER) < -0.75f)
            {
                WeaponComponent shipWeapon =  Mappers.weapon.get(ship);
                if (shipWeapon.timer > shipWeapon.coolDown){
                    BulletFactory.fireWeapon(
                            shipPos.X(),
                            shipPos.y + 20,
                            MathUtils.random(-shipWeapon.accuracy, shipWeapon.accuracy),
                            shipWeapon);
                    shipWeapon.timer = 0;
                }
            }


            if (controller.getButton(XBox360Pad.BUTTON_LB)
                && Mappers.weapon_special.has(ship))
            {

                PlayerManager.useSpecial(ship);

            }
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            WeaponComponent shipWeapon =  Mappers.weapon.get(ship);
            if (shipWeapon.timer > shipWeapon.coolDown){
                BulletFactory.fireWeapon(shipPos.X(), shipPos.y + 20, 0, shipWeapon);
                shipWeapon.timer = 0;
            }
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
        {
            Vector2 mouseWindowPos = Utils.inputMouseWindowPosBounded();
            Vector3 mouseWorldPos = game.cam.unproject(new Vector3(mouseWindowPos.x, mouseWindowPos.y, 0));

            Vector2 dir = new Vector2(mouseWorldPos.x - shipPos.X(), mouseWorldPos.y - shipPos.y);

            float len = dir.len();

            if (len > 15.0)
            {
                dir = dir.nor();
                shipVel.x = dir.x* Constants.RES_X/8 * PlayerManager.ship_speed;
                shipVel.y = dir.y* Constants.RES_Y/8 * PlayerManager.ship_speed;
            }
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
                && Mappers.weapon_special.has(ship))
        {
            PlayerManager.useSpecial(ship);
        }


    }

    float time_since_last_enemy = 0;
    float timer_stage = 0;
    float timer_total = 0;
    void updateEnemySpawner(float delta)
    {
        timer_total += delta;
        timer_stage += delta;
        time_since_last_enemy += delta;

        if (timer_stage > PlayerManager.timeToNextStage(PlayerManager.stage))
        {
            PlayerManager.stage++;
            timer_stage = 0;
            time_since_last_enemy = 0.0f;
        }

        if (time_since_last_enemy > PlayerManager.timeToSpawnInStage(PlayerManager.stage))
        {
            PlayerManager.spawnInStage(PlayerManager.stage);
            time_since_last_enemy = 0.0f;
        }

    }

    @Override
    public void show() {

    }


    @Override
    public void render(float delta)
    {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gaem.batch.begin();
        background_spr.setCenterX(0);
        background_spr.setCenterY(0);
        background_spr.draw(Gaem.batch);
        Gaem.batch.end();


        if (PlayerManager.is_game_over)
        {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)
                    || (controller != null && controller.getButton(XBox360Pad.BUTTON_START)))
            {
                game.setScreen(new PlayScreen(game));
                return;
            }
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            is_paused = !is_paused;
        }
        else if (!PlayerManager.player_level_event && !is_paused)
        {
            updateEnemySpawner(delta);
            updateInput();
        }




        if (PlayerManager.player_level_event || PlayerManager.is_game_over || is_paused)
        {
            game.engine.update(0);
        }
        else if (!is_paused)
        {
            game.engine.update(delta);
        }


        Gaem.batch.begin();
        renderGame();
        renderHud();
        Gaem.batch.end();

        Gdx.graphics.setTitle("LifeInSpace | FPS: " + Gdx.graphics.getFramesPerSecond());

        /* DRAW DEBUG
        Family debug_family = Family.all(CollisionComponent.class, PositionComponent.class, RenderComponent.class).get();
        ImmutableArray<Entity> debug_entities = game.engine.getEntitiesFor(debug_family);
        for (Entity e : debug_entities)
        {
            PositionComponent pos = Mappers.position.get(e);
            CollisionComponent col = Mappers.collision.get(e);

            game.shapeRenderer.setProjectionMatrix(game.cam.combined);
            game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            game.shapeRenderer.rect(pos.X() - col.sizeX*0.5f,
                    pos.y - col.sizeY*0.5f,
                    col.sizeX,
                    col.sizeY);
            game.shapeRenderer.end();
        }
        /**/


    }


    void renderGame()
    {
        font.draw(Gaem.batch,
                "SCORE\n" + Utils.textScoreNice(PlayerManager.score),
                -(Constants.RES_X / 2) + Constants.RES_X * 0.02f,
                (Constants.RES_Y / 2) - Constants.RES_Y * 0.02f);

        boolean has_weapon_special = Mappers.weapon_special.has(ship);
        boolean has_red_orbs = false, has_green_orbs = false, has_blue_orbs = false;
        if (has_weapon_special)
        {

            WeaponSpecialComponent ws = Mappers.weapon_special.get(ship);

            has_red_orbs = ws.red_cost>0;
            has_green_orbs = ws.green_cost>0;
            has_blue_orbs = ws.blue_cost>0;

            weapon_special_item_spr.setRegion(PowerupFactory.getPowerTexture(ws.type));
            weapon_special_item_spr.setSize(16, 16);
            weapon_special_slot_spr.setOrigin(32, 32);
            weapon_special_item_spr.setScale(2, 2);
            weapon_special_item_spr.setCenterX(-(Constants.RES_X / 2) + Constants.RES_X * 0.03f + 16+8);
            weapon_special_item_spr.setCenterY((Constants.RES_Y / 2) - Constants.RES_Y * 0.1f - 8);
            weapon_special_item_spr.draw(Gaem.batch);

        }

        weapon_special_slot_spr.setOrigin(0, 32);
        weapon_special_slot_spr.setScale(2, 2);
        weapon_special_slot_spr.setCenterX(-(Constants.RES_X / 2) + Constants.RES_X * 0.03f);
        weapon_special_slot_spr.setCenterY((Constants.RES_Y / 2) - Constants.RES_Y * 0.1f);
        weapon_special_slot_spr.draw(Gaem.batch);

        //Red orbs
        gui_orbs_spr.setRegion(0, 0, 16, 16);
        gui_orbs_spr.setOrigin(8, 8);
        gui_orbs_spr.setScale(2, 2);
        gui_orbs_spr.setCenterX(-(Constants.RES_X / 2) + Constants.RES_X * 0.03f);
        gui_orbs_spr.setCenterY((Constants.RES_Y / 2) - Constants.RES_Y * 0.2f);
        gui_orbs_spr.draw(Gaem.batch);
        font.draw(Gaem.batch,
                (has_red_orbs?"[YELLOW]":"")+"x" + PlayerManager.red_orbs+(has_red_orbs?"[]":""),
                -(Constants.RES_X / 2) + Constants.RES_X * 0.05f,
                (Constants.RES_Y / 2) - Constants.RES_Y * 0.2f);

        //Green orbs
        gui_orbs_spr.setRegion(16, 0, 16, 16);
        gui_orbs_spr.setOrigin(8, 8);
        gui_orbs_spr.setScale(2, 2);
        gui_orbs_spr.setCenterX(-(Constants.RES_X / 2) + Constants.RES_X * 0.03f);
        gui_orbs_spr.setCenterY((Constants.RES_Y / 2) - Constants.RES_Y * 0.25f);
        gui_orbs_spr.draw(Gaem.batch);
        font.draw(Gaem.batch,
                (has_green_orbs?"[YELLOW]":"")+"x" + PlayerManager.green_orbs+(has_green_orbs?"[]":""),
                -(Constants.RES_X / 2) + Constants.RES_X * 0.05f,
                (Constants.RES_Y / 2) - Constants.RES_Y * 0.25f);

        //Blue orbs
        gui_orbs_spr.setRegion(32, 0, 16, 16);
        gui_orbs_spr.setOrigin(8, 8);
        gui_orbs_spr.setScale(2, 2);
        gui_orbs_spr.setCenterX(-(Constants.RES_X / 2) + Constants.RES_X * 0.03f);
        gui_orbs_spr.setCenterY((Constants.RES_Y / 2) - Constants.RES_Y * 0.3f);
        gui_orbs_spr.draw(Gaem.batch);
        font.draw(Gaem.batch,
                (has_blue_orbs?"[YELLOW]":"")+"x" + PlayerManager.blue_orbs+(has_blue_orbs?"[]":""),
                -(Constants.RES_X / 2) + Constants.RES_X * 0.05f,
                (Constants.RES_Y / 2) - Constants.RES_Y * 0.3f);

        font.draw(Gaem.batch,
                "LEVEL " + PlayerManager.player_level,
                -(Constants.RES_X / 2) + Constants.RES_X * 0.02f,
                (Constants.RES_Y / 2) - Constants.RES_Y * 0.90f);


        level_bar_spr.setRegion(0, 0, 128, 32);
        level_bar_spr.setX(-(Constants.RES_X / 2) + Constants.RES_X * 0.02f);
        level_bar_spr.setCenterY((Constants.RES_Y / 2) - Constants.RES_Y * 0.9f - 32);
        level_bar_spr.setSize(128, 32);
        level_bar_spr.draw(Gaem.batch);

        level_bar_spr.setRegion(0, 32, 128, 32);
        level_bar_spr.setX(-(Constants.RES_X / 2) + Constants.RES_X * 0.02f);
        level_bar_spr.setCenterY((Constants.RES_Y / 2) - Constants.RES_Y * 0.9f - 32);
        level_bar_spr.setSize(
                ((PlayerManager.score - PlayerManager.exp_before()) * 128 /
                        (PlayerManager.exp_next() - PlayerManager.exp_before())),
                32);
        level_bar_spr.draw(Gaem.batch);
    }

    void renderHud()
    {

        if (PlayerManager.is_game_over)
        {
            font_big.draw(Gaem.batch,
                    "SCORE\n" + Utils.textScoreNice(PlayerManager.score),
                    -(Constants.RES_X / 2) + Constants.RES_X * 0.35f,
                    (Constants.RES_Y / 2) - Constants.RES_Y * 0.45f);

            font_big.draw(Gaem.batch,
                    "PRESS THE [ORANGE]INTRO[]",
                    -(Constants.RES_X / 2) + Constants.RES_X * 0.35f,
                    (Constants.RES_Y / 2) - Constants.RES_Y * 0.6f);
        }
        else if (PlayerManager.player_level_event)
        {
            if (   Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)
                    ||
                    ( controller != null && (controller.getButton(XBox360Pad.BUTTON_X) ) )
                    )
            {
                PlayerManager.applySkillChoice(PlayerManager.player_level_event_choice_1, ship);
                PlayerManager.player_level_event = false;
            }
            if (   Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)
                    ||
                    ( controller != null && (controller.getButton(XBox360Pad.BUTTON_B) ) )
                    )
            {
                PlayerManager.applySkillChoice(PlayerManager.player_level_event_choice_2, ship);
                PlayerManager.player_level_event = false;
            }


            font_big.draw(Gaem.batch,
                    "LEVEL UP!",
                    -(Constants.RES_X / 2) + Constants.RES_X * 0.40f,
                    (Constants.RES_Y / 2) - Constants.RES_Y * 0.3f);


            font_big.draw(Gaem.batch,
                    PlayerManager.stringForSkill(PlayerManager.player_level_event_choice_1),
                    -(Constants.RES_X / 2) + Constants.RES_X * 0.25f - 64,
                    (Constants.RES_Y / 2) - Constants.RES_Y * 0.55f);
            TextureManager.setSpriteHudPowerup(sprite, PlayerManager.player_level_event_choice_1);
            sprite.setSize(128, 128);
            sprite.setOrigin(16, 16);
            sprite.setCenterX(-(Constants.RES_X / 2) + Constants.RES_X * 0.25f);
            sprite.setCenterY((Constants.RES_Y / 2) - Constants.RES_Y * 0.7f);
            sprite.draw(Gaem.batch);


            font_big.draw(Gaem.batch,
                    PlayerManager.stringForSkill(PlayerManager.player_level_event_choice_2),
                    -(Constants.RES_X / 2) + Constants.RES_X * 0.7f - 64,
                    (Constants.RES_Y / 2) - Constants.RES_Y * 0.55f);
            TextureManager.setSpriteHudPowerup(sprite, PlayerManager.player_level_event_choice_2);
            sprite.setSize(128, 128);
            sprite.setCenterX(-(Constants.RES_X / 2) + Constants.RES_X * 0.7f);
            sprite.setCenterY((Constants.RES_Y / 2) - Constants.RES_Y * 0.7f);
            sprite.draw(Gaem.batch);
        }
        else if (is_paused)
        {

            font_big.draw(Gaem.batch,
                    "PAUSE",
                    -(Constants.RES_X / 2) + Constants.RES_X * 0.40f,
                    (Constants.RES_Y / 2) - Constants.RES_Y * 0.3f);
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
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

    }
}
