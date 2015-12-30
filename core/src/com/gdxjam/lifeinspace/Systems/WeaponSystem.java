package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;
import com.gdxjam.lifeinspace.Mappers;

/**
 * Created by Jesús Atalaya on 20/12/2015.
 */
public class WeaponSystem extends IteratingSystem {

    public WeaponSystem()
    {
        super(Family.all(WeaponComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        WeaponComponent wc = Mappers.weapon.get(entity);
        wc.timer += deltaTime;

        if (Mappers.weapon_special.has(entity))
        {
            Mappers.weapon_special.get(entity).timer += deltaTime;
        }

    }
}
