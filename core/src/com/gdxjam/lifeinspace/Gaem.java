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
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Systems.RenderSystem;

public class Gaem extends ApplicationAdapter
{
	SpriteBatch batch;
	Engine engine;
	Entity ship;

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
