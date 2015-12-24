package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.SinusBehaviourComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Mappers;

/**
 * Created by threpwood on 20/12/2015.
 */
public class MovementSystem extends IteratingSystem
{
    Engine engine;

    public MovementSystem(Engine engine)
    {
        super(Family
                .all(PositionComponent.class, VelocityComponent.class)
                .get());

        this.engine = engine;
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        PositionComponent pos = Mappers.position.get(entity);
        VelocityComponent vel = Mappers.velocity.get(entity);

        if (Mappers.sinus_behaviour.has(entity))
        {
            SinusBehaviourComponent sc = Mappers.sinus_behaviour.get(entity);
            sc.timer += deltaTime;

            pos.x_offset = MathUtils.cos((float) (pos.y*2.0f*Math.PI/(sc.freq)))*sc.amp;
        }

        pos.x_real += vel.x*deltaTime;
        pos.y += vel.y*deltaTime;


        if (Mappers.type.has(entity))
        {
            if (Mappers.type.get(entity).type == TypeComponent.TypeEntity.ENEMY)
            {
                if (pos.y < 0) engine.removeEntity(entity);
            }
        }


    }
}