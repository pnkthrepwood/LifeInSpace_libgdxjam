package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;

/**
 * Created by Jesús Atalaya on 20/12/2015.
 */
public class WeaponSystem extends IteratingSystem {

    private ComponentMapper<WeaponComponent> wm = ComponentMapper.getFor(WeaponComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public WeaponSystem()
    {
        super(Family.all(WeaponComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        WeaponComponent wc = wm.get(entity);
        PositionComponent pc = pm.get(entity);

        wc.timer += deltaTime;

    }
}
