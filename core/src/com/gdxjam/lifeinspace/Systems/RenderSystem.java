package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;

/**
 * Created by threpwood on 20/12/2015.
 */
public class RenderSystem extends IteratingSystem
{
    private ComponentMapper<RenderComponent> rm = ComponentMapper.getFor(RenderComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public RenderSystem()
    {
        super(Family.all(RenderComponent.class).get());
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        RenderComponent rc = rm.get(entity);
        PositionComponent pos = pm.get(entity);

        rc.spr.setX(pos.x);
        rc.spr.setY(pos.y);
        rc.spr.draw(rc.batch);
    }
}