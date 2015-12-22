package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.ShapeComponent;
import com.gdxjam.lifeinspace.Mappers;

/**
 * Created by threpwood on 20/12/2015.
 */
public class RenderSystem extends EntitySystem
{
    private ImmutableArray<Entity> entities;
    SpriteBatch batch;
    Camera cam;

    public RenderSystem(SpriteBatch batch, Camera cam)
    {
        this.batch = batch;
        this.cam = cam;
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

            rc.spr.setCenterX((float) Math.floor(pos.x));
            rc.spr.setCenterY((float) Math.floor(pos.y));

            rc.spr.draw(rc.batch);
        }

        batch.end();

    }
}