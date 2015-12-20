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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;
import com.gdxjam.lifeinspace.Systems.MovementSystem;
import com.gdxjam.lifeinspace.Systems.RenderSystem;

import javax.swing.text.Position;

public class Gaem extends Game
{
	SpriteBatch batch;
	Engine engine;
	Entity ship;
    Entity bullet;

	OrthographicCamera cam;
	Viewport viewport;

	Sprite cursor;

	Sprite background;

	@Override
	public void create ()
	{


		cam = new OrthographicCamera(Constants.RES_X, Constants.RES_Y);
		viewport = new FitViewport(Constants.RES_X, Constants.RES_Y, cam);

		cursor = new Sprite(new Texture("cursor.png"));
		cursor.setOriginCenter();


		Texture tex_bg = new Texture("space_bg.png");
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
		rc.spr = new Sprite(new Texture("ship.png"));
		rc.batch = batch;
		ship.add(rc);

        //Ship Weapon Component
        WeaponComponent wc = new WeaponComponent();
        ship.add(wc);

		engine.addEntity(ship);

		engine.addSystem(new RenderSystem());
		engine.addSystem(new MovementSystem());
	}

    public void shootBullet(float posX, float posY)
    {

        //Bullet Position Parameter
        PositionComponent pc = new PositionComponent();
        pc.x = posX;
        pc.y = posY;

        //Bullet Velocity Component
        VelocityComponent vc = new VelocityComponent();
        vc.y = 500;

        //Bullet Render Component
        RenderComponent rc = new RenderComponent();

        Texture tex = new Texture ("bulletcollection.png");
        TextureRegion texreg =  new TextureRegion();
        texreg.setRegion(tex);
        texreg.setRegion(20*0 + 0, 20*0 + 0, 20, 20);

        rc.spr = new Sprite(texreg);
        rc.batch = batch;

        //Adding components to Bullet
        Entity bullet = new Entity();
        bullet.add(pc);
        bullet.add(vc);
        bullet.add(rc);

        //Adding Bullet Entity to Engine
        engine.addEntity(bullet);

    }

	public void updateGame()
	{
        float dt = Gdx.graphics.getDeltaTime();


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
				shipVel.y = dir.y* Math.min(shipVel.maxSpeed, len);
			}

		}

        //NEW BULLET
        WeaponComponent shipWeapon =  ship.getComponent(WeaponComponent.class);
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            if (shipWeapon.timer >= shipWeapon.coolDown)
            {
                shootBullet(shipPos.x, shipPos.y + 20);
                shipWeapon.timer = 0f;
            }
        }
        shipWeapon.timer += dt;
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

		background.translate(0, -dt*20);
		background.draw(batch);

		engine.update(dt);

		Vector2 inputPos = Utils.inputCurrentWorldPos(cam);

		cursor.setCenterX(inputPos.x);
		cursor.setCenterY(inputPos.y);

		cursor.draw(batch);

		batch.end();

	}
}
