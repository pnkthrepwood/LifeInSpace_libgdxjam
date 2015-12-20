package com.gdxjam.lifeinspace;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Systems.MovementSystem;
import com.gdxjam.lifeinspace.Systems.RenderSystem;

import javax.swing.text.Position;

public class Gaem extends Game
{
	SpriteBatch batch;
	Engine engine;
	Entity ship;

	OrthographicCamera cam;
	Viewport viewport;

	Sprite cursor;

	@Override
	public void create ()
	{


		cam = new OrthographicCamera(1200, 800);
		viewport = new FitViewport(1200, 800, cam);

		cursor = new Sprite(new Texture("cursor.png"));
		cursor.setOriginCenter();

		batch = new SpriteBatch();
		engine = new Engine();

		ship = new Entity();
		ship.add(new PositionComponent());
		ship.add(new VelocityComponent());

		RenderComponent rc = new RenderComponent();
		rc.spr = new Sprite(new Texture("ship.png"));
		rc.batch = batch;
		ship.add(rc);

		engine.addEntity(ship);

		engine.addSystem(new RenderSystem());
		engine.addSystem(new MovementSystem());
	}

	public void updateGame()
	{


		PositionComponent shipPos = ship.getComponent(PositionComponent.class);
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
				shipVel.y = dir.y* Math.max(shipVel.maxSpeed, len);
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
		engine.update(dt);

		Vector2 inputPos = Utils.inputCurrentWorldPos(cam);

		cursor.setCenterX(inputPos.x);
		cursor.setCenterY(inputPos.y);

		cursor.draw(batch);

		batch.end();

	}
}
