package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Constants;
import com.gdxjam.lifeinspace.Mappers;

/**
 * Created by threpwood on 20/12/2015.
 */
public class RenderSystem extends EntitySystem
{
    private ImmutableArray<Entity> entities;
    SpriteBatch batch;
    Camera cam;

    //BitmapFont
    BitmapFont font;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    int score;


    public RenderSystem(SpriteBatch batch, Camera cam)
    {
        this.batch = batch;
        this.cam = cam;

        //Font instantation & configuration (through FreeTypeFontGenerator parameters)
        font = new BitmapFont();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("IMMORTAL.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);

        score = 0;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, RenderComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime)
    {

        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            RenderComponent rc = Mappers.render.get(e);
            PositionComponent pos = Mappers.position.get(e);

            //rc.spr.setCenterX((float) Math.floor(pos.X()));
            //rc.spr.setCenterY((float) Math.floor(pos.y));

            rc.spr.setCenterX(pos.X());
            rc.spr.setCenterY(pos.y);

            rc.spr.setOriginCenter();
            rc.spr.setRotation(rc.rotation);

            rc.spr.draw(batch);
        }

        font.draw(batch, "Score: " + score, -Constants.RES_X/2, Constants.RES_Y/ 2);

        batch.end();

    }
}