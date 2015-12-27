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
import com.gdxjam.lifeinspace.Components.ShooterBehaviourComponent;
import com.gdxjam.lifeinspace.Components.SinusBehaviourComponent;
import com.gdxjam.lifeinspace.Components.SquadComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.SquadManager;
import com.gdxjam.lifeinspace.TextureManager;

/**
 * Created by threpwood on 20/12/2015.
 */
public class EnemyFactory
{
    public static Gaem gaem;

    public static void spawnSnakeEnemy(float x, float y, int squad_size)
    {
        float A = MathUtils.random(2,4);
        float F = MathUtils.random(4,8);
        float V = MathUtils.random(40,60);

        int squad = SquadManager.registerNewSquad(squad_size);

        float anim_time = MathUtils.random(0.2f, 0.7f);

        for (int i = 0; i < squad_size; ++i)
        {
            Entity enemy = new Entity();
            enemy.add(new TypeComponent(TypeComponent.TypeEntity.ENEMY));
            enemy.add(new PositionComponent(x, y + 20.f*i*(A*0.5f)));
            enemy.add(new VelocityComponent(0, -V));
            enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("enemy2.png"))));

            Texture tex = TextureManager.getTexture("enemy_disk.png");
            Animation anim = new Animation(anim_time/2,
                    new TextureRegion(tex, 0, 0, 16 ,16),
                    new TextureRegion(tex, 16, 0, 16 ,16),
                    new TextureRegion(tex, 0, 16, 16 ,16),
                    new TextureRegion(tex, 16, 16, 16 ,16)
            );
            anim.setPlayMode(Animation.PlayMode.LOOP);
            enemy.add(new AnimationComponent(anim));

            enemy.add(new CollisionComponent(20, 20));
            enemy.add(new SinusBehaviourComponent(F, A));
            enemy.add(new SquadComponent(squad));
            gaem.engine.addEntity(enemy);
        }
    }

    public static void spawnShooterEnemy(float x, float y)
    {
        int squad = SquadManager.registerNewSquad(1);

        Entity enemy = new Entity();
        enemy.add(new TypeComponent(TypeComponent.TypeEntity.ENEMY));
        enemy.add(new PositionComponent(x, y));
        enemy.add(new VelocityComponent(0, -30));

        Texture tex = TextureManager.getTexture("monster.png");
        enemy.add(new RenderComponent(new Sprite(new TextureRegion(tex, 0, 0, 32 ,32))));

        Animation anim = new Animation(0.3f/4,
                new TextureRegion(tex, 0, 0, 32 ,32),
                new TextureRegion(tex, 32, 0, 32 ,32),
                new TextureRegion(tex, 0, 0, 32 ,32),
                new TextureRegion(tex, 0, 32, 32 ,32)
        );
        anim.setPlayMode(Animation.PlayMode.LOOP);
        enemy.add(new AnimationComponent(anim));

        enemy.add(new SinusBehaviourComponent(MathUtils.random(5,15), MathUtils.random(1,3)));
        enemy.add(new CollisionComponent(32, 32));
        enemy.add(new LifeComponent(3));
        enemy.add(new ShooterBehaviourComponent(2));
        enemy.add(new SquadComponent(squad));
        enemy.add(new WeaponComponent(WeaponComponent.WeaponType.ENEMY_WEAPON));
        gaem.engine.addEntity(enemy);

    }

}
