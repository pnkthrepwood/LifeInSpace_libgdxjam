package com.gdxjam.lifeinspace.Factorys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.PowerUpComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.RenderEffectComponent;
import com.gdxjam.lifeinspace.Components.SinusBehaviourComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Components.WeaponSpecialComponent;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.TextureManager;

/**
 * Created by threpwood on 25/12/2015.
 */
public class PowerupFactory
{

    public enum PowerUpType
    {
        RED,
        GREEN,
        BLUE,

        MINE,
        SHIELD,
        DASH,
        NOVA
    }

    public static Gaem gaem;

    public static void spawnSpecial(float x, float y)
    {
        float r = MathUtils.random(0.f, 1.f);
        if (r < 0.35f)
        {
            if (MathUtils.random(0.f, 1.f) < 0.5f)
                spawnPowerup(x, y, PowerUpType.SHIELD);
            else
                spawnPowerup(x, y, PowerUpType.NOVA);
        }
        else if (r < 0.65f)
        {
            spawnPowerup(x, y, PowerUpType.MINE);
        }
        else
        {
            spawnPowerup(x, y, PowerUpType.DASH);
        }

    }

    public static void spawnPowerup(float x, float y, PowerUpType type)
    {
        Entity powerup = Gaem.engine.createEntity();
        powerup.add(new TypeComponent(TypeComponent.TypeEntity.POWERUP));
        powerup.add(new PositionComponent(x, y));
        powerup.add(new VelocityComponent(0, 15));
        powerup.add(new SinusBehaviourComponent(MathUtils.random(10, 15), MathUtils.random(1.0f,2.0f)));
        powerup.add(new CollisionComponent(16, 16));

        Sprite spr = new Sprite(getPowerTexture(type));
        spr.setScale(1.5f, 1.5f);
        powerup.add(new RenderComponent(spr));
        powerup.add(new PowerUpComponent(type));

        gaem.engine.addEntity(powerup);
    }


    public static TextureRegion getPowerTexture(WeaponSpecialComponent.WeaponSpecialType type)
    {
        switch (type) {
            case MINE:
            {
                return new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        0, 32, 16, 16);
            }
            case SHIELD:
            {
                return new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        16, 32, 16, 16);
            }
            case DASH:
            {
                return new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        32, 32, 16, 16);
            }
            case NOVA:
            {
                return new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        0, 48, 16, 16);
            }
        }
        //Todo: Error?
        return new TextureRegion(
                TextureManager.getTexture("powerup.png"),
                0, 0, 16, 16);    }

    public static TextureRegion getPowerTexture(PowerUpType type)
    {
        switch (type)
        {
            case RED:
            {
                return new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        0, 0, 16, 16);
            }
            case GREEN:
            {
                return new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        16, 0, 16, 16);
            }
            case BLUE:
            {
                return new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        32, 0, 16, 16);
            }
            case MINE:
            {
                return new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        0, 32, 16, 16);
            }
            case SHIELD:
            {
                return new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        16, 32, 16, 16);
            }
            case DASH:
            {
                return new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        32, 32, 16, 16);
            }
            case NOVA:
            {
                return new TextureRegion(
                        TextureManager.getTexture("powerup.png"),
                        0, 48, 16, 16);
            }
        }

        //Todo: Error?
        return new TextureRegion(
                TextureManager.getTexture("powerup.png"),
                0, 0, 16, 16);
    }

}
