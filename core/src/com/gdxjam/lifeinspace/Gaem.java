package com.gdxjam.lifeinspace;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;
import com.gdxjam.lifeinspace.Systems.BulletSystem;
import com.gdxjam.lifeinspace.Systems.MovementSystem;
import com.gdxjam.lifeinspace.Systems.RenderSystem;
import com.gdxjam.lifeinspace.Systems.WeaponSystem;

import javax.swing.text.Position;

public class Gaem extends Game
{
	SpriteBatch batch;
	Engine engine;
	Entity ship;

	OrthographicCamera cam;
	Viewport viewport;

	Sprite cursor;
	Sprite background;

	@Override
	public void create ()
	{

		cam = new OrthographicCamera(Constants.RES_X, Constants.RES_Y);
		viewport = new FitViewport(Constants.RES_X, Constants.RES_Y, cam);

		cursor = new Sprite(TextureManager.getTexture("cursor.png"));

		Texture tex_bg = TextureManager.getTexture("space_bg.png");
		background = new Sprite(tex_bg);
		background.setX(-300);
		background.setY(-600);
		//background.setRegion(new TextureRegion(tex_bg), 0, 0, 600, 1200);

		batch = new SpriteBatch();
		engine = new Engine();

		ship = new Entity();
		ship.add(new PositionComponent());
		ship.add(new VelocityComponent());

		RenderComponent rc = new RenderComponent();
		rc.spr = new Sprite(TextureManager.getTexture("ship.png"));
		rc.batch = batch;
		ship.add(rc);

        //Ship Weapon Component
        WeaponComponent wc = new WeaponComponent();
        ship.add(wc);

		engine.addEntity(ship);

		engine.addSystem(new RenderSystem());
		engine.addSystem(new MovementSystem());

        engine.addSystem(new WeaponSystem());
        engine.addSystem(new BulletSystem(engine));

        BulletFactory.gaem = this;

		Gdx.graphics.setContinuousRendering(true);
	}

	public void updateGame()
	{
        float dt = Gdx.graphics.getDeltaTime();

		PositionComponent shipPos = Mappers.position.get(ship);
		VelocityComponent shipVel = ship.getComponent(VelocityComponent.class);
		shipVel.x = 0;
		shipVel.y = 0;

		if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
		{
			Vector2 mouseWindowPos = Utils.mousePosBounded();
			Vector3 mouseWorldPos = cam.unproject(new Vector3(mouseWindowPos.x, mouseWindowPos.y, 0));

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
        WeaponComponent shipWeapon =  ship.getComponent(WeaponComponent.class);
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            if (shipWeapon.timer > shipWeapon.coolDown){
                BulletFactory.createBullet(shipPos.x, shipPos.y + 20);
                shipWeapon.timer = 0;
            }
        }
	}

	@Override
	public void render ()
	{
		updateGame();

		float dt = Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(cam.combined);

		batch.begin();

		background.translate(0, -dt * 20);
		background.draw(batch);

		engine.update(dt);

		Vector2 inputPos = Utils.inputCurrentWorldPos(cam);

		cursor.setCenterX(inputPos.x);
		cursor.setCenterY(inputPos.y);

		cursor.draw(batch);

		batch.end();

		Gdx.graphics.setTitle("LifeInSpace | FPS: "+Gdx.graphics.getFramesPerSecond());

	}
}
