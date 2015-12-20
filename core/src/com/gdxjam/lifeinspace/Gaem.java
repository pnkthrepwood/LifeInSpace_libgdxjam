package com.gdxjam.lifeinspace;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Gaem extends ApplicationAdapter
{
	SpriteBatch batch;

	Engine engine;

	Entity ship;

	public class RenderSystem extends IteratingSystem {
		private ComponentMapper<RenderComponent> rm = ComponentMapper.getFor(RenderComponent.class);
		private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

		public RenderSystem() {
			super(Family.all(RenderComponent.class).get());
		}

		public void processEntity(Entity entity, float deltaTime) {
			RenderComponent rc = rm.get(entity);
			PositionComponent pos = pm.get(entity);

			rc.batch.draw(rc.img, pos.x, pos.y);
		}
	}

	public class PositionComponent implements Component
	{
		public float x = 0.0f;
		public float y = 0.0f;
	}

	public class VelocityComponent implements Component
	{
		public float x = 0.0f;
		public float y = 0.0f;
	}

	public class RenderComponent implements Component
	{
		public Texture img;
		public SpriteBatch batch;
	}

	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		engine = new Engine();

		ship = new Entity();
		ship.add(new PositionComponent());
		ship.add(new VelocityComponent());
		RenderComponent rc = new RenderComponent();
		rc.img = new Texture("ship.png");
		rc.batch = batch;
		ship.add(rc);

		engine.addEntity(ship);

		engine.addSystem(new RenderSystem());
	}

	@Override
	public void render ()
	{
		float dt = Gdx.graphics.getDeltaTime();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		engine.update(dt);
		batch.end();

	}
}
