package com.gdxjam.lifeinspace.Screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gdxjam.lifeinspace.BulletFactory;
import com.gdxjam.lifeinspace.Components.IDComponent;
import com.gdxjam.lifeinspace.Components.IDComponent.IDEntity;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.Mappers;
import com.gdxjam.lifeinspace.Systems.BulletSystem;
import com.gdxjam.lifeinspace.Systems.MovementSystem;
import com.gdxjam.lifeinspace.Systems.RenderSystem;
import com.gdxjam.lifeinspace.Systems.WeaponSystem;
import com.gdxjam.lifeinspace.TextureManager;
import com.gdxjam.lifeinspace.Utils;

/**
 * Created by threpwood on 20/12/2015.
 */
public class PlayScreen implements Screen {

    Gaem game;

    Sprite cursor;
    Sprite background;
    Entity ship;

    public PlayScreen(Gaem game)
    {
        this.game = game;

        cursor = new Sprite(TextureManager.getTexture("cursor.png"));

        Texture tex_bg = TextureManager.getTexture("space_bg.png");
        background = new Sprite(tex_bg);
        background.setX(-300);
        background.setY(-600);
        //background.setRegion(new TextureRegion(tex_bg), 0, 0, 600, 1200);


        game.engine.addSystem(new RenderSystem());
        game.engine.addSystem(new MovementSystem());
        game.engine.addSystem(new WeaponSystem());
        game.engine.addSystem(new BulletSystem(game.engine));
        BulletFactory.gaem = this.game;


        ship = new Entity();
        ship.add(new IDComponent(IDEntity.SHIP));
        ship.add(new PositionComponent());
        ship.add(new VelocityComponent());
        ship.add(new RenderComponent(new Sprite(TextureManager.getTexture("ship.png")), game.batch));
        ship.add(new WeaponComponent());
        game.engine.addEntity(ship);


        Entity enemy = new Entity();
        enemy.add(new IDComponent(IDEntity.ENEMY));
        enemy.add(new PositionComponent(0, 100));
        enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("ship.png")), game.batch));
        game.engine.addEntity(enemy);

    }

    @Override
    public void show() {

    }

    public void updateGame()
    {
        float dt = Gdx.graphics.getDeltaTime();

        PositionComponent shipPos = Mappers.position.get(ship);
        VelocityComponent shipVel = Mappers.velocity.get(ship);
        shipVel.x = 0;
        shipVel.y = 0;

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
        {
            Vector2 mouseWindowPos = Utils.mousePosBounded();
            Vector3 mouseWorldPos = game.cam.unproject(new Vector3(mouseWindowPos.x, mouseWindowPos.y, 0));

            Vector2 dir = new Vector2(mouseWorldPos.x - shipPos.x, mouseWorldPos.y - shipPos.y);

            float len = dir.len();

            if (len > 2.0)
            {
                dir = dir.nor();
                shipVel.x = dir.x* Math.max(shipVel.minSpeed, len);
                shipVel.y = dir.y* Math.min(shipVel.maxSpeed, len);
            }

        }

        //NEW BULLET
        WeaponComponent shipWeapon =  Mappers.weapon.get(ship);
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            if (shipWeapon.timer > shipWeapon.coolDown){
                BulletFactory.createBullet(shipPos.x, shipPos.y + 20);
                shipWeapon.timer = 0;
            }
        }
    }

    @Override
    public void render(float delta) {
        updateGame();

        float dt = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(game.cam.combined);

        game.batch.begin();

        background.translate(0, -dt * 20);
        background.draw(game.batch);

        game.engine.update(dt);

        Vector2 inputPos = Utils.inputCurrentWorldPos(game.cam);

        cursor.setCenterX(inputPos.x);
        cursor.setCenterY(inputPos.y);

        cursor.draw(game.batch);

        game.batch.end();

        Gdx.graphics.setTitle("LifeInSpace | FPS: "+Gdx.graphics.getFramesPerSecond());
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
