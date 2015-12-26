package com.gdxjam.lifeinspace.Factorys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.LifeComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.PowerUpComponent;
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

    public enum PowerUpType
    {
        FIRE_RANGE,
        FIRE_SPEED,
        FLY_SPEED
    }

    public static Gaem gaem;

    public static void spawnPowerup(float x, float y, PowerUpType type)
    {
        Entity powerup = new Entity();
        powerup.add(new TypeComponent(TypeComponent.TypeEntity.POWERUP));
        powerup.add(new PositionComponent(x, y));
        powerup.add(new VelocityComponent(0, 15));
        powerup.add(new SinusBehaviourComponent(MathUtils.random(10, 15), MathUtils.random(1.0f,2.0f)));
        powerup.add(new CollisionComponent(16, 16));

        Sprite spr = new Sprite(new TextureRegion(
                TextureManager.getTexture("powerup.png"),
                0, 0, 16, 16));

        switch (type)
        {
            case FIRE_RANGE:
            {
                spr.setRegion(new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        0, 0, 16, 16));
            } break;
            case FIRE_SPEED:
            {
                spr.setRegion(new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        16, 0, 16, 16));
            } break;
            case FLY_SPEED:
            {
                spr.setRegion(new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        32, 0, 16, 16));
            } break;
        }
        powerup.add(new RenderComponent(spr));
        powerup.add(new PowerUpComponent(type));

        gaem.engine.addEntity(powerup);
    }


}
