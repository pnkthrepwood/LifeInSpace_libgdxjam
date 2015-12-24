package com.gdxjam.lifeinspace.Screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gdxjam.lifeinspace.Components.CircleShapeComponent;
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
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.Mappers;
import com.gdxjam.lifeinspace.Systems.BackgroundRenderSystem;
import com.gdxjam.lifeinspace.Systems.BulletSystem;
import com.gdxjam.lifeinspace.Systems.CollisionSystem;
import com.gdxjam.lifeinspace.Systems.MovementSystem;
import com.gdxjam.lifeinspace.Systems.RenderSystem;
import com.gdxjam.lifeinspace.Systems.WeaponSystem;
import com.gdxjam.lifeinspace.TextureManager;
import com.gdxjam.lifeinspace.Utils;
import com.gdxjam.lifeinspace.XBox360Pad;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

/**
 * Created by threpwood on 20/12/2015.
 */
public class PlayScreen implements Screen {

    Gaem game;

    Sprite cursor;
    Sprite background;
    Entity ship;
    Sprite planet_spr;
    Sprite ship_spr;

    Controller controller;

    float time_since_last_enemy = 0;

    //BitmapFont
    BitmapFont font;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    Integer score;
    String scoreText;
    float fontPosX = (-Constants.RES_X / 2 ) + Constants.RES_X * 0.01f ;
    float fontPosY = (Constants.RES_Y / 2 ) - Constants.RES_Y * 0.02f;

    public PlayScreen(Gaem game)
    {
        this.game = game;

        cursor = new Sprite(TextureManager.getTexture("cursor.png"));

        Texture tex_bg = TextureManager.getTexture("space_bg.png");
        background = new Sprite(tex_bg);
        background.setX(-tex_bg.getWidth() / 2);
        background.setY(-tex_bg.getHeight() / 2);
        background.setScale(10, 10);
        //background.setRegion(new TextureRegion(tex_bg), 0, 0, 600, 1200);


        //CAREFUL: ORDER MATTERS!
        game.engine.addSystem(new MovementSystem(game.engine));
        game.engine.addSystem(new CollisionSystem(game.engine));
        game.engine.addSystem(new WeaponSystem());
        game.engine.addSystem(new BulletSystem(game.engine));
        game.engine.addSystem(new BackgroundRenderSystem(game.shapeRenderer));
        game.engine.addSystem(new RenderSystem(game.batch, game.cam));
        BulletFactory.gaem = this.game;
        EnemyFactory.gaem = this.game;



        Entity planet_bg = new Entity();
        planet_bg.add(new PositionComponent(0, -Constants.RES_Y / 2 + TextureManager.getTexture("planet_bg.png").getHeight() / 2));
        planet_bg.add(new VelocityComponent(0, -0.5f));
        planet_spr = new Sprite(TextureManager.getTexture("planet_bg.png"));
        //planet_spr.setScale(5, 5);
        planet_bg.add(new RenderComponent(planet_spr));
        game.engine.addEntity(planet_bg);

        ship = new Entity();
        ship.add(new TypeComponent(TypeEntity.SHIP));
        ship.add(new PositionComponent());
        ship.add(new VelocityComponent(0, 0));
        ship_spr = new Sprite(TextureManager.getTexture("ship.png"));
        //ship_spr.setScale(2,2);
        ship.add(new RenderComponent(ship_spr));
        ship.add(new WeaponComponent());
        ship.add(new CollisionComponent(20, 20));
        game.engine.addEntity(ship);

        EnemyFactory.spawnSnakeEnemy(0, Constants.RES_Y / 2);

        controller = null;
        if (Controllers.getControllers().size > 0)
        {
            controller = Controllers.getControllers().first();
        }

        generateBackgroundEntities();


        //Font instantation & configuration (through FreeTypeFontGenerator parameters)
        font = new BitmapFont();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("bitDarling.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);

        score = 0;
        scoreText = score.toString();
    }

    private void generateBackgroundEntities()
    {
        for (int i = 0; i < 1000; ++i)
        {
            Entity star = new Entity();
            CircleShapeComponent shape = new CircleShapeComponent();
            shape.radius = 1;
            star.add(shape);
            star.add(new PositionComponent(MathUtils.random(0, Constants.RES_X), MathUtils.random(0, Constants.RES_Y)));
            star.add(new VelocityComponent(0,-MathUtils.random(0,3)));
            game.engine.addEntity(star);
        }
        for (int i = 0; i < 50; ++i)
        {
            Entity star = new Entity();
            CircleShapeComponent shape = new CircleShapeComponent();
            star.add(new PositionComponent(MathUtils.random(0, Constants.RES_X), MathUtils.random(0, Constants.RES_Y)));
            shape.radius = MathUtils.random(1,1.5f);
            if (MathUtils.random(0.0f, 1.0f) < 0.5f)
            {
                shape.color.r = (MathUtils.random(0, 4))*0.25f;
                shape.color.g = 0;
                shape.color.b = 0;
            }
            star.add(shape);
            game.engine.addEntity(star);
        }
        for (int i = 0; i < 50; ++i)
        {
            Entity star = new Entity();
            CircleShapeComponent shape = new CircleShapeComponent();
            star.add(new PositionComponent(MathUtils.random(0, Constants.RES_X), MathUtils.random(0, Constants.RES_Y)));
            shape.radius = MathUtils.random(1,1.5f);
            if (MathUtils.random(0.0f, 1.0f) < 0.5f)
            {
                float y = MathUtils.random(0.0f, 1.0f);

                shape.color.r = y;
                shape.color.g = y;
                shape.color.b = 0;
            }
            star.add(shape);
            game.engine.addEntity(star);
        }
        for (int i = 0; i < 50; ++i)
        {
            Entity star = new Entity();
            CircleShapeComponent shape = new CircleShapeComponent();
            star.add(new PositionComponent(MathUtils.random(0, Constants.RES_X), MathUtils.random(0, Constants.RES_Y)));
            shape.radius = MathUtils.random(1,1.5f);
            if (MathUtils.random(0.0f, 1.0f) < 0.5f)
            {
                float c = MathUtils.random(0.0f, 1.0f);

                shape.color.r = 0;
                shape.color.g = 0;
                shape.color.b = c;
            }
            star.add(shape);
            game.engine.addEntity(star);
        }
    }

    @Override
    public void show() {

    }

    public void updateGame()
    {
        PositionComponent shipPos = Mappers.position.get(ship);
        VelocityComponent shipVel = Mappers.velocity.get(ship);
        shipVel.x = 0;
        shipVel.y = 0;

        if (controller != null)
        {
            if (Math.abs(controller.getAxis(XBox360Pad.AXIS_LEFT_X)) > 0.2)
                shipVel.x = controller.getAxis(XBox360Pad.AXIS_LEFT_X)* Constants.RES_X/2;

            if (Math.abs(controller.getAxis(XBox360Pad.AXIS_LEFT_Y)) > 0.2)
                shipVel.y = -controller.getAxis(XBox360Pad.AXIS_LEFT_Y)* Constants.RES_Y/2;


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
                            MathUtils.random(-shipWeapon.accuracy, shipWeapon.accuracy));
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
                shipVel.x = dir.x* Constants.RES_X/2;// Math.max(shipVel.minSpeed, len);
                shipVel.y = dir.y*Constants.RES_Y/2;// Math.min(shipVel.maxSpeed, len);
            }
        }


        //NEW BULLET
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            WeaponComponent shipWeapon =  Mappers.weapon.get(ship);
            if (shipWeapon.timer > shipWeapon.coolDown){
                BulletFactory.shootBullet(shipPos.X(), shipPos.y + 20, 0);
                shipWeapon.timer = 0;
            }
        }
    }


    @Override
    public void render(float delta)
    {

        if (time_since_last_enemy > 10.0f)
        {
            EnemyFactory.spawnSnakeEnemy(
                    MathUtils.random(-Constants.RES_X*0.25f, Constants.RES_X*0.25f),
                    Constants.RES_Y / 2);
            time_since_last_enemy = 0.0f;
        }
        time_since_last_enemy += delta;

        updateGame();
        //background.translate(0, -delta * 20);
        //float planet_scale = planet_spr.getScaleX() - delta*(5.0f/30.0f);
        //planet_spr.setScale(planet_scale, planet_scale);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.engine.update(delta); //Update inside batch: it may draw things
        Vector2 inputPos = Utils.inputMouseWorldPos(game.cam);

        cursor.setCenterX(inputPos.x);
        cursor.setCenterY(inputPos.y);
        //cursor.draw(game.batch);

        Gdx.graphics.setTitle("LifeInSpace | FPS: " + Gdx.graphics.getFramesPerSecond());
        
        Gaem.batch.begin();
        font.draw(Gaem.batch, "SCORE\n" + score, fontPosX , fontPosY);
        Gaem.batch.end();

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
