package com.gdxjam.lifeinspace.Factorys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
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

    public static void spawnSnakeEnemy(float x, float y)
    {
        float A = MathUtils.random(2,4);
        float F = MathUtils.random(4,8);
        float V = MathUtils.random(40,60);

        int squad_size = 7;
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

}
