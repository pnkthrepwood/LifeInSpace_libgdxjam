package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;

/**
 * Created by threpwood on 20/12/2015.
 */
public class CollisionSystem extends IteratingSystem
{
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<CollisionComponent> cm = ComponentMapper.getFor(CollisionComponent.class);

    private Engine engine;

    public CollisionSystem(Engine engine)
    {
        super(Family.all(RenderComponent.class).get());
        this.engine = engine;
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        Family family = Family.all(CollisionComponent.class).get();

        ImmutableArray<Entity> entities = engine.getEntitiesFor(family);

        PositionComponent pos_me = pm.get(entity);
        CollisionComponent col_me = cm.get(entity);
        Rectangle rect_me = new Rectangle(pos_me.x, pos_me.y, col_me.sizeX, col_me.sizeY);

        for (Entity other : entities)
        {
            if (other == entity) continue;

            PositionComponent pos_other = pm.get(other);
            CollisionComponent col_other = cm.get(other);
            Rectangle rect_other = new Rectangle(pos_other.x, pos_other.y, col_other.sizeX, col_other.sizeY);

            if (rect_me.overlaps(rect_other))
            {
                //To-Do: Collision
            }
        }

    }
}