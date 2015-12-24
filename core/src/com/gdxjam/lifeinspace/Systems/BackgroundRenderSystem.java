package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gdxjam.lifeinspace.Components.CircleShapeComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
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
        entities = engine.getEntitiesFor(Family.all(CircleShapeComponent.class).get());
    }

    @Override
    public void removedFromEngine (Engine engine) {

    }

    @Override
    public void update (float deltaTime) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);
            CircleShapeComponent shape = Mappers.shape.get(e);
            PositionComponent pos = Mappers.position.get(e);

            shapeRenderer.setColor(shape.color);
            shapeRenderer.rect(pos.X() - shape.radius/2,
                               pos.y - shape.radius/2,
                               shape.radius,
                               shape.radius);
            shapeRenderer.circle(pos.X(), pos.y, shape.radius);
        }

        shapeRenderer.end();




    }
}
