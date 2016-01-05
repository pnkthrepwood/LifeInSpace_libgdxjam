package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.gdxjam.lifeinspace.Components.AnimationComponent;
import com.gdxjam.lifeinspace.Components.FlashingComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.RenderEffectComponent;
import com.gdxjam.lifeinspace.Constants;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.Mappers;
import com.gdxjam.lifeinspace.TextureManager;

import java.util.Map;

/**
 * Created by threpwood on 20/12/2015.
 */
public class RenderSystem extends EntitySystem
{
    private ImmutableArray<Entity> entities;
    SpriteBatch batch;
    Camera cam;
    Sprite spr;

    public RenderSystem(SpriteBatch batch, Camera cam)
    {
        this.batch = batch;
        this.cam = cam;
        this.spr = new Sprite();
    }

    @Override
    public void addedToEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(
                PositionComponent.class,
                RenderComponent.class
        ).exclude(
                FlashingComponent.class
        ).get());

    }

    @Override
    public void removedFromEngine (Engine engine) {
        entities = engine.getEntitiesFor(Family.all(
                PositionComponent.class,
                RenderComponent.class
        ).exclude(
                FlashingComponent.class
        ).get());
    }

    @Override
    public void update (float deltaTime)
    {

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        for (int i = 0; i < entities.size(); ++i) {
            Entity e = entities.get(i);

            RenderComponent rc = Mappers.render.get(e);
            PositionComponent pos = Mappers.position.get(e);

            if (Mappers.animation.has(e))
            {
                AnimationComponent anim = Mappers.animation.get(e);
                anim.timer += deltaTime;
                rc.spr.setRegion(anim.animation.getKeyFrame(anim.timer));
                if (anim.animation.getPlayMode() == Animation.PlayMode.NORMAL
                    && anim.timer > anim.animation.getAnimationDuration())
                {
                    //Todo:Make a TimeOutSystem and TimeOutComponent and get rid of this shit
                    e.remove(AnimationComponent.class);
                    Gaem.engine.removeEntity(e);
                }
            }

            rc.spr.setCenterX(pos.X());
            rc.spr.setCenterY(pos.y);

            rc.spr.setRotation(rc.rotation);




            if (Mappers.render_effect.has(e))
            {
                RenderEffectComponent effect = Mappers.render_effect.get(e);
                effect.timer += deltaTime;

                float sc = Interpolation.linear.apply(
                        effect.scale_start, effect.scale_end,
                        effect.timer / effect.timer_total);
                rc.spr.setScale(sc, sc);

                float a = Interpolation.linear.apply(
                        effect.alpha_start, effect.alpha_end,
                        effect.timer / effect.timer_total
                );
                rc.spr.setAlpha(a);

                if (effect.timer > effect.timer_total)
                {
                    e.remove(RenderEffectComponent.class);
                    if (effect.single_use)
                    {
                        Gaem.engine.removeEntity(e);
                    }

                }
            }

            if (Mappers.dash.has(e))
            {
                rc.spr.setAlpha(0.5f);
            }

            rc.spr.draw(batch);


            if (Mappers.shield.has(e))
            {

                spr.setTexture(TextureManager.getTexture("shield.png"));
                spr.setRegion(0, 0, 32, 32);
                spr.setSize(32, 32);
                spr.setOrigin(16, 16);
                spr.setCenterX(pos.X());
                spr.setCenterY(pos.y);
                spr.setScale(1.5f, 1.5f);
                spr.draw(batch);
            }

        }
        batch.end();
    }
}