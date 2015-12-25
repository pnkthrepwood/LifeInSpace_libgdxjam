package com.gdxjam.lifeinspace.Factorys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.LifeComponent;
import com.gdxjam.lifeinspace.Components.ShooterBehaviourComponent;
import com.gdxjam.lifeinspace.Components.SinusBehaviourComponent;
import com.gdxjam.lifeinspace.Components.SquadComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
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

        for (int i = 0; i < squad_size; ++i)
        {
            Entity enemy = new Entity();
            enemy.add(new TypeComponent(TypeComponent.TypeEntity.ENEMY));
            enemy.add(new PositionComponent(x, y + 20.f*i*(A*0.5f)));
            enemy.add(new VelocityComponent(0, -V));
            enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("enemy2.png"))));
            enemy.add(new CollisionComponent(20, 20));
            enemy.add(new SinusBehaviourComponent(F, A));
            enemy.add(new SquadComponent(squad));
            gaem.engine.addEntity(enemy);
        }
    }

    public static void spawnShooterEnemy(float x, float y)
    {
        Entity enemy = new Entity();
        enemy.add(new TypeComponent(TypeComponent.TypeEntity.ENEMY));
        enemy.add(new PositionComponent(x, y));
        enemy.add(new VelocityComponent(0, -30));
        enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("monster.png"))));
        enemy.add(new SinusBehaviourComponent(MathUtils.random(5,15), MathUtils.random(1,3)));
        enemy.add(new CollisionComponent(32, 32));
        enemy.add(new LifeComponent(3));
        enemy.add(new ShooterBehaviourComponent(2));
        gaem.engine.addEntity(enemy);

    }

}
