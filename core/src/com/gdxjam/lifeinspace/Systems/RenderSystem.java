package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Mappers;

/**
 * Created by threpwood on 20/12/2015.
 */
public class RenderSystem extends IteratingSystem
{

    public RenderSystem()
    {
        super(Family.all(RenderComponent.class).get());
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        RenderComponent rc = Mappers.render.get(entity);
        PositionComponent pos = Mappers.position.get(entity);

        rc.spr.setCenterX((float) Math.floor(pos.x));
        rc.spr.setCenterY((float) Math.floor(pos.y));

        rc.spr.draw(rc.batch);
    }
}