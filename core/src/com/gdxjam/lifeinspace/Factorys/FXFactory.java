package com.gdxjam.lifeinspace.Factorys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.lifeinspace.Components.AnimationComponent;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.LifeComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.RenderEffectComponent;
import com.gdxjam.lifeinspace.Components.ShooterBehaviourComponent;
import com.gdxjam.lifeinspace.Components.SinusBehaviourComponent;
import com.gdxjam.lifeinspace.Components.SquadComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.TextureManager;

/**
 * Created by threpwood on 25/12/2015.
 */
public class FXFactory {

    public static void makeExplosion(float x, float y)
    {

        Texture tex = TextureManager.getTexture("explosions.png");
        Animation anim = new Animation(0.2f/6,
                new TextureRegion(tex, 640, 58, 16 ,16),
                new TextureRegion(tex, 660, 58, 16 ,16),
                new TextureRegion(tex, 680, 58, 16 ,16),
                new TextureRegion(tex, 700, 58, 16 ,16),
                new TextureRegion(tex, 720, 58, 16 ,16),
                new TextureRegion(tex, 740, 58, 16 ,16)
        );
        anim.setPlayMode(Animation.PlayMode.NORMAL);

        Entity entity = new Entity();
        entity.add(new PositionComponent(x, y));
        entity.add(new AnimationComponent(anim));
        entity.add(new RenderComponent(new Sprite(new TextureRegion(tex, 640, 58, 16 ,16))));
        Gaem.engine.addEntity(entity);

    }

    public static void makeBigExplosion(float x, float y)
    {

        Texture tex = TextureManager.getTexture("explosions.png");
        Animation anim = new Animation(0.4f/7,
                new TextureRegion(tex, 375, 10, 30 ,30),
                new TextureRegion(tex, 409, 10, 30 ,30),
                new TextureRegion(tex, 443, 10, 30 ,30),
                new TextureRegion(tex, 477, 10, 30 ,30),
                new TextureRegion(tex, 511, 10, 30 ,30),
                new TextureRegion(tex, 545, 10, 30 ,30),
                new TextureRegion(tex, 579, 10, 30 ,30)
        );
        anim.setPlayMode(Animation.PlayMode.NORMAL);

        Entity entity = new Entity();
        entity.add(new PositionComponent(x, y));
        entity.add(new AnimationComponent(anim));
        entity.add(new RenderComponent(new Sprite(new TextureRegion(tex, 375, 10, 30 ,30))));
        Gaem.engine.addEntity(entity);

    }

    public static void makeCatchpowerup(float x, float y, PowerupFactory.PowerUpType type)
    {

        TextureRegion texreg = PowerupFactory.getPowerTexture(type);
        Animation anim = new Animation(0.3f, PowerupFactory.getPowerTexture(type));
        anim.setPlayMode(Animation.PlayMode.NORMAL);

        Entity entity = new Entity();
        entity.add(new PositionComponent(x, y));
        entity.add(new AnimationComponent(anim));
        entity.add(new RenderComponent(new Sprite(texreg)));
        entity.add(new RenderEffectComponent(0.3f, 1, 3.0f, 1, 0));
        Gaem.engine.addEntity(entity);

    }

    public static void makeDissapearSnake(float x, float y)
    {
        Entity enemy = new Entity();
        enemy.add(new TypeComponent(TypeComponent.TypeEntity.ENEMY));
        enemy.add(new PositionComponent(x, y));
        enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("enemy2.png"))));

        Texture tex = TextureManager.getTexture("enemy_disk.png");
        Animation anim = new Animation(0.10f,
                new TextureRegion(tex, 0, 16, 16 ,16),
                new TextureRegion(tex, 16, 16, 16 ,16),
                new TextureRegion(tex, 0, 16, 16 ,16)
        );
        anim.setPlayMode(Animation.PlayMode.NORMAL);
        enemy.add(new AnimationComponent(anim));

        enemy.add(new RenderEffectComponent(0.10f, 1, 1.0f, 1, 0));
        Gaem.engine.addEntity(enemy);

    }

}
