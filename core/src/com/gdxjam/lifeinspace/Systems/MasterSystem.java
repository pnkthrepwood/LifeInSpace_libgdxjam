package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.gdxjam.lifeinspace.Components.AnimationComponent;
import com.gdxjam.lifeinspace.Components.FlashingComponent;
import com.gdxjam.lifeinspace.Components.MineComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.RenderEffectComponent;
import com.gdxjam.lifeinspace.Factorys.BulletFactory;
import com.gdxjam.lifeinspace.Factorys.FXFactory;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.Mappers;

/**
 * Created by ThrepwooD on 30/12/2015.
 */

public class MasterSystem extends EntitySystem
{
    private ImmutableArray<Entity> mines;

    public MasterSystem()
    {
    }

    @Override
    public void addedToEngine (Engine engine) {
        mines = engine.getEntitiesFor(Family.all(
                MineComponent.class
        )
        /*
        .exclude(
                FlashingComponent.class
        )
        */
        .get());

    }

    @Override
    public void removedFromEngine (Engine engine) {
        mines = engine.getEntitiesFor(Family.all(
                MineComponent.class
        )
        /*
        .exclude(
                FlashingComponent.class
        )
        */
                .get());
    }

    @Override
    public void update (float deltaTime)
    {

        for (int i = 0; i < mines.size(); ++i)
        {
            Entity mine = mines.get(i);

            MineComponent mc = Mappers.mines.get(mine);
            mc.timer += deltaTime;
            if (mc.timer > mc.explodeTime)
            {
                PositionComponent pos = Mappers.position.get(mine);
                FXFactory.makeBigExplosion(pos.X(), pos.y);
                BulletFactory.shootExplosion(pos.X(), pos.y);
                Gaem.engine.removeEntity(mine);
            }


        }

    }
}