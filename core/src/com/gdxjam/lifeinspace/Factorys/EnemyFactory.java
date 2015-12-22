package com.gdxjam.lifeinspace.Factorys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.SinusBehaviourComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.TextureManager;

/**
 * Created by threpwood on 20/12/2015.
 */
public class EnemyFactory
{
    public static Gaem gaem;

    public static void spawnEnemy(float x, float y)
    {
        for (int i = 0; i < MathUtils.random(3,7); ++i)
        {
            Entity enemy = new Entity();
            enemy.add(new TypeComponent(TypeComponent.TypeEntity.ENEMY));
            enemy.add(new PositionComponent(x, y + MathUtils.random(30,60)*i));
            enemy.add(new VelocityComponent(0, -20));
            enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("ship.png"))));
            enemy.add(new CollisionComponent(20, 20));
            enemy.add(new SinusBehaviourComponent());
            gaem.engine.addEntity(enemy);
        }
    }

}
