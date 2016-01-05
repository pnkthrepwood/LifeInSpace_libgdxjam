package com.gdxjam.lifeinspace;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent.WeaponType;
import com.gdxjam.lifeinspace.Screens.MenuScreen;
import com.gdxjam.lifeinspace.Screens.PlayScreen;
import com.gdxjam.lifeinspace.Systems.BulletSystem;
import com.gdxjam.lifeinspace.Systems.MovementSystem;
import com.gdxjam.lifeinspace.Systems.RenderSystem;
import com.gdxjam.lifeinspace.Systems.WeaponSystem;


public class Gaem extends Game
{
	public static SpriteBatch batch;
	public static ShapeRenderer shapeRenderer;
	public OrthographicCamera cam;
	public Viewport viewport;
	public static PooledEngine engine;


	@Override
	public void create ()
	{
		cam = new OrthographicCamera(Constants.RES_X, Constants.RES_Y);
		viewport = new FitViewport(Constants.RES_X, Constants.RES_Y, cam);

		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

		engine = new PooledEngine(4096,   4096*2,
								  1024,   4096*2);

		Gdx.graphics.setContinuousRendering(true);
		setScreen(new MenuScreen(this));
	}


	@Override
	public void render ()
	{
		super.render();

	}
}
