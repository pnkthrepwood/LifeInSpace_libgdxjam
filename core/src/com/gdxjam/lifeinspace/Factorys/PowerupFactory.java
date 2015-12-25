package com.gdxjam.lifeinspace.Factorys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.LifeComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.ShooterBehaviourComponent;
import com.gdxjam.lifeinspace.Components.SinusBehaviourComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.TextureManager;

/**
 * Created by threpwood on 25/12/2015.
 */
public class PowerupFactory
{

    public static Gaem gaem;

    public static void spawnPowerup(float x, float y)
    {
        Entity powerup = new Entity();
        powerup.add(new TypeComponent(TypeComponent.TypeEntity.POWERUP));
        powerup.add(new PositionComponent(x, y));
        powerup.add(new VelocityComponent(0, 15));
        powerup.add(new RenderComponent(new Sprite(TextureManager.getTexture("powerup.png"))));
        //powerup.add(new SinusBehaviourComponent(MathUtils.random(5, 15), MathUtils.random(1,3)));
        powerup.add(new CollisionComponent(16, 16));
        gaem.engine.addEntity(powerup);
    }


}
