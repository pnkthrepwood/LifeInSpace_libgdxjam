package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.lifeinspace.Components.BulletComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.ShooterBehaviourComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;
import com.gdxjam.lifeinspace.Factorys.BulletFactory;
import com.gdxjam.lifeinspace.Mappers;

/**
 * Created by threpwood on 24/12/2015.
 */

public class EnemyBehaviourSystem extends IteratingSystem
{

    public EnemyBehaviourSystem() {
        super(Family.all(ShooterBehaviourComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        ShooterBehaviourComponent behaviour = Mappers.shoot_behaviour.get(entity);

        behaviour.timer -= deltaTime;
        if (behaviour.timer < 0.0f)
        {
            PositionComponent pos = Mappers.position.get(entity);
            WeaponComponent weapon = Mappers.weapon.get(entity);
            BulletFactory.shootBullet(pos.X(), pos.y - 32, 180, weapon);
            behaviour.timer = weapon.coolDown;//MathUtils.random(5, behaviour.shoot_time);
        }

    }
}
