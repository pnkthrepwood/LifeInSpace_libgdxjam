package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;

/**
 * Created by threpwood on 20/12/2015.
 */
public class MovementSystem extends IteratingSystem
{
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public MovementSystem()
    {
        super(Family.all(RenderComponent.class).get());
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        PositionComponent pos = pm.get(entity);
        VelocityComponent vel = vm.get(entity);

        pos.x += vel.x*deltaTime;
        pos.y += vel.y*deltaTime;
    }
}