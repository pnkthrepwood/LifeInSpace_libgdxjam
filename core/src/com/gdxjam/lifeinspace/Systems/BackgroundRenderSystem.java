package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gdxjam.lifeinspace.Components.BulletComponent;
import com.gdxjam.lifeinspace.Components.ShapeComponent;
import com.gdxjam.lifeinspace.Mappers;

/**
 * Created by threpwood on 22/12/2015.
 */
public class BackgroundRenderSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    ShapeRenderer shapeRenderer;

    public BackgroundRenderSystem(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ShapeComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);
            ShapeComponent shape = Mappers.shape.get(e);

            shapeRenderer.setColor(shape.color);
            shapeRenderer.circle(shape.xPos, shape.yPos, shape.radius);
        }

        shapeRenderer.end();
    }
}
