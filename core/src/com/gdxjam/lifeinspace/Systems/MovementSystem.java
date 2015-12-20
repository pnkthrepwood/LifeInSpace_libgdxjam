package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Mappers;

/**
 * Created by threpwood on 20/12/2015.
 */
public class MovementSystem extends IteratingSystem
{

    public MovementSystem()
    {
        super(Family.all(RenderComponent.class).get());
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        PositionComponent pos = Mappers.position.get(entity);
        VelocityComponent vel = Mappers.velocity.get(entity);

        pos.x += vel.x*deltaTime;
        pos.y += vel.y*deltaTime;
    }
}