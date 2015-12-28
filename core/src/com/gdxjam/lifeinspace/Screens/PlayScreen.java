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
import com.gdxjam.lifeinspace.Components.FlashingComponent;
import com.gdxjam.lifeinspace.Components.RenderEffectComponent;
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
import com.gdxjam.lifeinspace.Factorys.FXFactory;
import com.gdxjam.lifeinspace.Factorys.PowerupFactory;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.Mappers;
import com.gdxjam.lifeinspace.PlayerManager;
import com.gdxjam.lifeinspace.Systems.BackgroundRenderSystem;
import com.gdxjam.lifeinspace.Systems.BulletSystem;
import com.gdxjam.lifeinspace.Systems.CollisionSystem;
import com.gdxjam.lifeinspace.Systems.EnemyBehaviourSystem;
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

    Sprite cursor;
    Entity ship;
    Sprite planet_spr;
    Sprite ship_spr;

    Sprite background_spr;

    Controller controller;

    float time_since_last_enemy = 0;

    BitmapFont font;
    BitmapFont font_big;

    Sprite orbs_spr;

    public PlayScreen(Gaem game)
    {
        this.game = game;

        PlayerManager.reset();

        if (game.engine != null) game.engine = null;
        game.engine = new Engine();

        cursor = new Sprite(TextureManager.getTexture("cursor.png"));

        //CAREFUL: ORDER MATTERS!
        game.engine.addSystem(new MovementSystem(game.engine));
        game.engine.addSystem(new CollisionSystem(game.engine));
        game.engine.addSystem(new WeaponSystem());
        game.engine.addSystem(new BulletSystem(game.engine));
        game.engine.addSystem(new EnemyBehaviourSystem());
        game.engine.addSystem(new BackgroundRenderSystem(game.shapeRenderer));
        game.engine.addSystem(new RenderSystem(game.batch, game.cam));
        BulletFactory.gaem = this.game;
        EnemyFactory.gaem = this.game;
        PowerupFactory.gaem = this.game;


        Entity planet_bg = new Entity();
        planet_bg.add(new PositionComponent(0, -Constants.RES_Y / 2 + TextureManager.getTexture("planet_bg.png").getHeight() / 2));
        planet_bg.add(new VelocityComponent(0, -1.5f));
        planet_spr = new Sprite(TextureManager.getTexture("planet_bg.png"));
        //planet_spr.setScale(5, 5);
        planet_bg.add(new RenderComponent(planet_spr));
        planet_bg.add(new RenderEffectComponent(120, 5, 0.5f, 1, 1));
        game.engine.addEntity(planet_bg);

        ship = new Entity();
        ship.add(new TypeComponent(TypeEntity.SHIP));
        ship.add(new PositionComponent());
        ship.add(new VelocityComponent(0, 0));
        ship_spr = new Sprite(TextureManager.getTexture("ship.png"));
        //ship_spr.setScale(2,2);
        ship.add(new RenderComponent(ship_spr));
        ship.add(new WeaponComponent(WeaponComponent.WeaponType.PLAYER_WEAPON));
        ship.add(new CollisionComponent(20, 20));
        //ship.add(new FlashingComponent());
        game.engine.addEntity(ship);

        EnemyFactory.spawnSnakeEnemy(0, Constants.RES_Y / 2, 7);
        EnemyFactory.spawnShooterEnemy(
                MathUtils.random(-Constants.RES_X * 0.25f, Constants.RES_X * 0.25f),
                Constants.RES_Y / 2);

        controller = null;
        if (Controllers.getControllers().size > 0)
        {
            controller = Controllers.getControllers().first();
        }

        generateBackgroundScene();

        font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("bitDarling.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);

        font_big = new BitmapFont();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("bitDarling.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        font_big = generator.generateFont(parameter);
        font_big.getData().markupEnabled = true;

        orbs_spr = new Sprite(TextureManager.getTexture("powerup.png"));
        orbs_spr.setSize(16,16);
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


            if (Math.abs(controller.getAxis(XBox360Pad.AXIS_RIGHT_X)) > 0.2)
            {
                cursor.setX( cursor.getX() + controller.getAxis(XBox360Pad.AXIS_RIGHT_X)*0.1f);
            }
            if (Math.abs(controller.getAxis(XBox360Pad.AXIS_RIGHT_Y)) > 0.2)
            {
                cursor.setY( cursor.getY() - controller.getAxis(XBox360Pad.AXIS_RIGHT_Y)*0.1f);
            }

            //NEW BULLET
            if (controller.getAxis(XBox360Pad.AXIS_RIGHT_TRIGGER) < -0.75f)
            {
                WeaponComponent shipWeapon =  Mappers.weapon.get(ship);
                if (shipWeapon.timer > shipWeapon.coolDown){
                    BulletFactory.shootBullet(
                            shipPos.X(),
                            shipPos.y + 20,
                            MathUtils.random(-shipWeapon.accuracy, shipWeapon.accuracy),
                            shipWeapon);
                    BulletFactory.shootBullet(
                            shipPos.X(),
                            shipPos.y + 20,
                            -45,
                            shipWeapon);
                    BulletFactory.shootBullet(
                            shipPos.X(),
                            shipPos.y + 20,
                            45,
                            shipWeapon);
                    shipWeapon.timer = 0;
                }
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
                shipVel.x = dir.x* Constants.RES_X/8 * PlayerManager.ship_speed;// Math.max(shipVel.minSpeed, len);
                shipVel.y = dir.y* Constants.RES_Y/8 * PlayerManager.ship_speed;// Math.min(shipVel.maxSpeed, len);
            }
        }


        //NEW BULLET
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            WeaponComponent shipWeapon =  Mappers.weapon.get(ship);
            if (shipWeapon.timer > shipWeapon.coolDown){
                BulletFactory.shootBullet(shipPos.X(), shipPos.y + 20, 0, shipWeapon);
                shipWeapon.timer = 0;
            }
        }
    }


    void updateEnemySpawner(float delta)
    {
        if (time_since_last_enemy > 1.0f)
        {
            EnemyFactory.spawnSnakeEnemy(
                    MathUtils.random(-Constants.RES_X*0.25f, Constants.RES_X*0.25f),
                    Constants.RES_Y / 2,
                    7);
            time_since_last_enemy = 0.0f;

            EnemyFactory.spawnShooterEnemy(
                    MathUtils.random(-Constants.RES_X * 0.25f, Constants.RES_X * 0.25f),
                    Constants.RES_Y / 2);
        }
        time_since_last_enemy += delta;

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
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            {
                game.setScreen(new PlayScreen(game));
                return;
            }
        }
        else
        {
            updateEnemySpawner(delta);
            updateInput();

        }
        game.engine.update(delta);

        /*
        Vector2 inputPos = Utils.inputMouseWorldPos(game.cam);
        cursor.setCenterX(inputPos.x);
        cursor.setCenterY(inputPos.y);
        cursor.draw(game.batch);
        */

        Gaem.batch.begin();
        font.draw(Gaem.batch,
                "SCORE\n" + Utils.textScoreNice(PlayerManager.score),
                -(Constants.RES_X / 2) + Constants.RES_X * 0.02f,
                (Constants.RES_Y / 2) - Constants.RES_Y * 0.02f);

        //Red orbs
        orbs_spr.setRegion(0, 0, 16, 16);
        orbs_spr.setOrigin(8, 8);
        orbs_spr.setScale(2, 2);
        orbs_spr.setCenterX(-(Constants.RES_X / 2) + Constants.RES_X * 0.03f);
        orbs_spr.setCenterY((Constants.RES_Y / 2) - Constants.RES_Y * 0.2f);
        orbs_spr.draw(Gaem.batch);
        font.draw(Gaem.batch,
                "x" + PlayerManager.red_orbs,
                -(Constants.RES_X / 2) + Constants.RES_X * 0.05f,
                (Constants.RES_Y / 2) - Constants.RES_Y * 0.2f);

        //Green orbs
        orbs_spr.setRegion(16, 0, 16, 16);
        orbs_spr.setOrigin(8, 8);
        orbs_spr.setScale(2, 2);
        orbs_spr.setCenterX(-(Constants.RES_X / 2) + Constants.RES_X * 0.03f);
        orbs_spr.setCenterY((Constants.RES_Y / 2) - Constants.RES_Y * 0.25f);
        orbs_spr.draw(Gaem.batch);
        font.draw(Gaem.batch,
                "x" + PlayerManager.green_orbs,
                -(Constants.RES_X / 2) + Constants.RES_X * 0.05f,
                (Constants.RES_Y / 2) - Constants.RES_Y * 0.25f);

        //Blue orbs
        orbs_spr.setRegion(32, 0, 16, 16);
        orbs_spr.setOrigin(8, 8);
        orbs_spr.setScale(2, 2);
        orbs_spr.setCenterX(-(Constants.RES_X / 2) + Constants.RES_X * 0.03f);
        orbs_spr.setCenterY((Constants.RES_Y / 2) - Constants.RES_Y * 0.3f);
        orbs_spr.draw(Gaem.batch);
        font.draw(Gaem.batch,
                "x" + PlayerManager.blue_orbs,
                -(Constants.RES_X / 2) + Constants.RES_X * 0.05f,
                (Constants.RES_Y / 2) - Constants.RES_Y * 0.3f);


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


        Gaem.batch.end();


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
        */

        Gdx.graphics.setTitle("LifeInSpace | FPS: " + Gdx.graphics.getFramesPerSecond());
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
