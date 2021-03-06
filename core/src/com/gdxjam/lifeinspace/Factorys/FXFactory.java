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
import com.gdxjam.lifeinspace.Components.MonsterComponent;
import com.gdxjam.lifeinspace.Components.MonsterComponent.MonsterType;
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

import static com.gdxjam.lifeinspace.Components.MonsterComponent.MonsterType.*;

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

        Entity entity = Gaem.engine.createEntity();
        entity.add(new PositionComponent(x, y));
        entity.add(new AnimationComponent(anim));
        entity.add(new RenderComponent(new Sprite(new TextureRegion(tex, 640, 58, 16 ,16))));
        Gaem.engine.addEntity(entity);

    }

    public static void makeBigExplosion(float x, float y)
    {

        Texture tex = TextureManager.getTexture("explosions.png");
        Animation anim = new Animation(0.8f/7,
                new TextureRegion(tex, 375, 10, 30 ,30),
                new TextureRegion(tex, 409, 10, 30 ,30),
                new TextureRegion(tex, 443, 10, 30 ,30),
                new TextureRegion(tex, 477, 10, 30 ,30),
                new TextureRegion(tex, 511, 10, 30 ,30),
                new TextureRegion(tex, 545, 10, 30 ,30),
                new TextureRegion(tex, 579, 10, 30 ,30)
        );
        anim.setPlayMode(Animation.PlayMode.NORMAL);

        Entity entity = Gaem.engine.createEntity();;
        entity.add(new PositionComponent(x, y));
        entity.add(new AnimationComponent(anim));
        entity.add(new RenderComponent(new Sprite(new TextureRegion(tex, 375, 10, 30 ,30))));
        entity.add(new RenderEffectComponent(0.8f, 8, 8, 1, 0.0f, true));
        Gaem.engine.addEntity(entity);


    }

    public static void makeCatchpowerup(float x, float y, PowerupFactory.PowerUpType type)
    {

        TextureRegion texreg = PowerupFactory.getPowerTexture(type);
        Animation anim = new Animation(0.3f, PowerupFactory.getPowerTexture(type));
        anim.setPlayMode(Animation.PlayMode.NORMAL);

        Entity entity = Gaem.engine.createEntity();
        entity.add(new PositionComponent(x, y));
        entity.add(new AnimationComponent(anim));
        entity.add(new RenderComponent(new Sprite(texreg)));
        entity.add(new RenderEffectComponent(0.3f, 1.25f, 4.0f, 1, 0, true));
        Gaem.engine.addEntity(entity);

    }

    public static void makeActionEnemy(float x, float y, MonsterComponent monster, float duration)
    {
        Entity enemy = Gaem.engine.createEntity();
        if (monster.type == SNAKE)
        {
            enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("enemy2.png"))));
            Texture tex = TextureManager.getTexture("enemy_disk.png");
            Animation anim = new Animation(duration,
                    new TextureRegion(tex, 0, 0, 16 ,16),
                    new TextureRegion(tex, 16, 0, 16 ,16)
            );
            anim.setPlayMode(Animation.PlayMode.NORMAL);
            enemy.add(new AnimationComponent(anim));
        }
        else if (monster.type == INVADER)
        {
            enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("enemy_disk.png"))));
            Texture tex = TextureManager.getTexture("monster.png");
            Animation anim = new Animation(duration,
                    new TextureRegion(tex, 0, 0, 32 ,32),
                    new TextureRegion(tex, 32, 0, 32 ,32),
                    new TextureRegion(tex, 0, 0, 32 ,32),
                    new TextureRegion(tex, 32, 0, 32 ,32)
            );
            anim.setPlayMode(Animation.PlayMode.NORMAL);
            enemy.add(new AnimationComponent(anim));
        }
        else if (monster.type == ULTRA)
        {
            enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("enemy_disk.png"))));
            Texture tex = TextureManager.getTexture("monster.png");
            Animation anim = new Animation(duration,
                    new TextureRegion(tex, 64+0, 0, 32 ,32),
                    new TextureRegion(tex, 64+32, 0, 32 ,32),
                    new TextureRegion(tex, 64+0, 0, 32 ,32),
                    new TextureRegion(tex, 64+32, 0, 32 ,32)
            );
            anim.setPlayMode(Animation.PlayMode.NORMAL);
            enemy.add(new AnimationComponent(anim));
        }
        else if (monster.type == OCTOPUS)
        {
            enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("enemy_disk.png"))));
            Texture tex = TextureManager.getTexture("monster.png");
            Animation anim = new Animation(duration,
                    new TextureRegion(tex, 0, 64, 32 ,32),
                    new TextureRegion(tex, 32, 64, 32 ,32),
                    new TextureRegion(tex, 0, 64, 32 ,32),
                    new TextureRegion(tex, 32, 64, 32 ,32)
            );
            anim.setPlayMode(Animation.PlayMode.NORMAL);
            enemy.add(new AnimationComponent(anim));
        }
        else
        {
            enemy = null;
            return;
        }

        enemy.add(new PositionComponent(x, y));
        enemy.add(new RenderEffectComponent(duration*1.5f, 1.0f, 3.0f, 1, 0, true));
        Gaem.engine.addEntity(enemy);





        //Arandela
        Texture tex = TextureManager.getTexture("explosions.png");
        Animation anim = new Animation(duration/2,
                new TextureRegion(tex, 545, 10, 30 ,30),
                new TextureRegion(tex, 579, 10, 30 ,30)
        );
        anim.setPlayMode(Animation.PlayMode.NORMAL);

        Entity entity = Gaem.engine.createEntity();;
        entity.add(new PositionComponent(x, y));
        entity.add(new AnimationComponent(anim));
        entity.add(new RenderComponent(new Sprite(new TextureRegion(tex, 545, 10, 30 ,30))));
        entity.add(new RenderEffectComponent(duration, 2, 2, 1, 0.5f, true));
        Gaem.engine.addEntity(entity);



    }


    public static void makeShieldAppear(float x, float y, float duration)
    {
        Entity enemy = Gaem.engine.createEntity();

        enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("shield.png"))));
        enemy.add(new PositionComponent(x, y));
        enemy.add(new RenderEffectComponent(duration, 0.0f, 1.0f, 0.0f, 1.0f, true));
        Gaem.engine.addEntity(enemy);
    }
    public static void makeShieldHit(float x, float y, float duration)
    {
        Entity enemy = Gaem.engine.createEntity();

        enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("shield.png"))));
        enemy.add(new PositionComponent(x, y));
        enemy.add(new RenderEffectComponent(duration, 1.5f, 1.5f*1.5f, 1.0f, 0.0f, true));
        Gaem.engine.addEntity(enemy);
    }

    public static void makeDashDisplay(float x, float y, float duration)
    {
        Entity enemy = Gaem.engine.createEntity();

        enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("ship.png"))));
        enemy.add(new PositionComponent(x, y));
        enemy.add(new RenderEffectComponent(duration, 1.0f, 2.0f, 0.5f, 0.0f, true));
        Gaem.engine.addEntity(enemy);

        //Arandela
        Texture tex = TextureManager.getTexture("explosions.png");
        Animation anim = new Animation(duration/7,
                new TextureRegion(tex, 92, 152, 31 ,31),
                new TextureRegion(tex, 92+33*1, 152, 31 ,31),
                new TextureRegion(tex, 92+33*2, 152, 31 ,31),
                new TextureRegion(tex, 92+33*3, 152, 31 ,31),
                new TextureRegion(tex, 92+33*4, 152, 31 ,31),
                new TextureRegion(tex, 92+33*5, 152, 31 ,31),
                new TextureRegion(tex, 92+33*6, 152, 31 ,31)
        );
        anim.setPlayMode(Animation.PlayMode.NORMAL);

        Entity entity = Gaem.engine.createEntity();
        entity.add(new PositionComponent(x, y));
        entity.add(new AnimationComponent(anim));
        entity.add(new RenderComponent(new Sprite(new TextureRegion(tex, 92, 152, 31 ,31))));
        entity.add(new RenderEffectComponent(duration, 1.5f, 2.5f, 0.9f, 0.2f, true));
        Gaem.engine.addEntity(entity);
    }

    public static void makeDissapearEnemy(float x, float y, MonsterComponent monster, float duration)
    {
        Entity enemy = Gaem.engine.createEntity();
        if (monster.type == SNAKE)
        {
            enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("enemy2.png"))));
            Texture tex = TextureManager.getTexture("enemy_disk.png");
            Animation anim = new Animation(duration/4,
                    new TextureRegion(tex, 0, 16, 16 ,16),
                    new TextureRegion(tex, 16, 16, 16 ,16),
                    new TextureRegion(tex, 0, 16, 16 ,16),
                    new TextureRegion(tex, 16, 16, 16 ,16)
            );
            anim.setPlayMode(Animation.PlayMode.NORMAL);
            enemy.add(new AnimationComponent(anim));
        }
        else if (monster.type == INVADER)
        {
            enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("enemy_disk.png"))));
            Texture tex = TextureManager.getTexture("monster.png");
            Animation anim = new Animation(duration/4,
                    new TextureRegion(tex, 0, 32, 32 ,32),
                    new TextureRegion(tex, 32, 32, 32 ,32),
                    new TextureRegion(tex, 0, 32, 32 ,32),
                    new TextureRegion(tex, 32, 32, 32 ,32)
            );
            anim.setPlayMode(Animation.PlayMode.NORMAL);
            enemy.add(new AnimationComponent(anim));
        }
        else if (monster.type == ULTRA)
        {
            enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("enemy_disk.png"))));
            Texture tex = TextureManager.getTexture("monster.png");
            Animation anim = new Animation(duration/4,
                    new TextureRegion(tex, 64+0, 32, 32 ,32),
                    new TextureRegion(tex, 64+32, 32, 32 ,32),
                    new TextureRegion(tex, 64+0, 32, 32 ,32),
                    new TextureRegion(tex, 64+32, 32, 32 ,32)
            );
            anim.setPlayMode(Animation.PlayMode.NORMAL);
            enemy.add(new AnimationComponent(anim));
        }
        else if (monster.type == OCTOPUS)
        {
            enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("enemy_disk.png"))));
            Texture tex = TextureManager.getTexture("monster.png");
            Animation anim = new Animation(duration/4,
                    new TextureRegion(tex, 0, 64+32, 32 ,32),
                    new TextureRegion(tex, 32, 64+32, 32 ,32),
                    new TextureRegion(tex, 0, 64+32, 32 ,32),
                    new TextureRegion(tex, 32, 64+32, 32 ,32)
            );
            anim.setPlayMode(Animation.PlayMode.NORMAL);
            enemy.add(new AnimationComponent(anim));
        }
        else
        {
            enemy = null;
            return;
        }

        enemy.add(new PositionComponent(x, y));
        enemy.add(new RenderEffectComponent(duration, 1.0f, 3.0f, 1, 0.5f, true));
        Gaem.engine.addEntity(enemy);

    }

}
