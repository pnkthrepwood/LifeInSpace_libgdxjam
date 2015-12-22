package com.gdxjam.lifeinspace.Factorys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
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
        Entity enemy = new Entity();
        enemy.add(new TypeComponent(TypeComponent.TypeEntity.ENEMY));
        enemy.add(new PositionComponent(x, y));
        enemy.add(new RenderComponent(new Sprite(TextureManager.getTexture("ship.png")), gaem.batch));
        enemy.add(new CollisionComponent(20, 20));
        gaem.engine.addEntity(enemy);
    }

}
