package com.gdxjam.lifeinspace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;

/**
 * Created by threpwood on 20/12/2015.
 */
public class TextureManager
{
    static final HashMap<String, Texture> textureMap = new HashMap<String, Texture>();

    public static void preLoadAll()
    {

    }

    public static Texture getTexture(String name)
    {
        if (!textureMap.containsKey(name))
        {
            textureMap.put(name, new Texture(name));
        }
        return textureMap.get(name);
    }

    public static void setSpriteHudPowerup(Sprite spr, PlayerManager.LevelUpgrade upgrade)
    {

        spr.setTexture(getTexture("level_cards.png"));
        switch (upgrade)
        {
            case FIRE_DIST:
                spr.setRegion(0, 0, 32, 32);
                break;
            case CONVERT_ORBS:
                spr.setRegion(32, 0, 32, 32);
                break;
            case DOBLE_ATK:
                spr.setRegion(64, 0, 32, 32);
                break;
            case LATERAL_SHOOT:
                spr.setRegion(96, 0, 32, 32);
                break;
            case FIRE_RATE:
                spr.setRegion(0, 32, 32, 32);
                break;
            case LUCKY:
                spr.setRegion(32, 32, 32, 32);
                break;
            case SPEED:
                spr.setRegion(64, 32, 32, 32);
                break;
            default:
        }

    }
}
