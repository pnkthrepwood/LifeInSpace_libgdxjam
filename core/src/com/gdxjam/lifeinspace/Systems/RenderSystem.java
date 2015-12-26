package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gdxjam.lifeinspace.Components.AnimationComponent;
import com.gdxjam.lifeinspace.Components.FlashingComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Constants;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.Mappers;

import java.util.Map;

/**
 * Created by threpwood on 20/12/2015.
 */
public class RenderSystem extends EntitySystem
{
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> flashing_entities;
    SpriteBatch batch;
    Camera cam;

    public RenderSystem(SpriteBatch batch, Camera cam)
    {
        this.batch = batch;
        this.cam = cam;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(
                PositionComponent.class,
                RenderComponent.class
        ).exclude(
                FlashingComponent.class
        ).get());

        flashing_entities = engine.getEntitiesFor(Family.all(
                PositionComponent.class,
                RenderComponent.class,
                FlashingComponent.class
        ).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(
                PositionComponent.class,
                RenderComponent.class
        ).exclude(
                FlashingComponent.class
        ).get());

        flashing_entities = engine.getEntitiesFor(Family.all(
                PositionComponent.class,
                RenderComponent.class,
                FlashingComponent.class
        ).get());
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

            if (Mappers.animation.has(e))
            {
                AnimationComponent anim = Mappers.animation.get(e);
                anim.timer += deltaTime;
                rc.spr.setRegion(anim.animation.getKeyFrame(anim.timer));
                if (anim.timer > anim.animation.getAnimationDuration())
                {
                    Gaem.engine.removeEntity(e);
                }
            }

            rc.spr.setCenterX(pos.X());
            rc.spr.setCenterY(pos.y);

            rc.spr.setRotation(rc.rotation);

            rc.spr.draw(batch);
        }
        batch.end();

/*
        ShaderProgram.pedantic = false;
        ShaderProgram defaultShader = batch.createDefaultShader();
        ShaderProgram shaderWhite = new ShaderProgram(
                Gdx.files.internal("white.vs").readString(),
                Gdx.files.internal("white.fs").readString());
        batch.setShader(shaderWhite);
        batch.begin();
        for (int i = 0; i < flashing_entities.size(); ++i) {
            Entity e = flashing_entities.get(i);

            RenderComponent rc = Mappers.render.get(e);
            PositionComponent pos = Mappers.position.get(e);

            if (Mappers.animation.has(e))
            {
                AnimationComponent anim = Mappers.animation.get(e);
                anim.timer += deltaTime;
                rc.spr.setRegion(anim.animation.getKeyFrame(anim.timer));
                if (anim.timer > anim.animation.getAnimationDuration())
                {
                    Gaem.engine.removeEntity(e);
                }
            }

            rc.spr.setCenterX(pos.X());
            rc.spr.setCenterY(pos.y);

            rc.spr.setRotation(rc.rotation);

            rc.spr.draw(batch);
        }
        batch.end();
        batch.setShader(defaultShader);
*/
    }
}