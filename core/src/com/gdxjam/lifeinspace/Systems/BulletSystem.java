package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gdxjam.lifeinspace.Components.BulletComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;

/**
 * Created by Jesús Atalaya on 20/12/2015.
 */
public class BulletSystem extends IteratingSystem {

    private ComponentMapper<BulletComponent> bm = ComponentMapper.getFor(BulletComponent.class);
    Engine engine;

    public BulletSystem(Engine engine) {
        super(Family.all(BulletComponent.class).get());
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        BulletComponent bc = bm.get(entity);
        if (bc.lifeTime < bc.timer)
        {
            engine.removeEntity(entity);
        }
        bc.timer += deltaTime;

    }
}
