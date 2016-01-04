package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by ThrepwooD on 30/12/2015.
 */
public class WeaponSpecialComponent implements Component
{

    public enum WeaponSpecialType
    {
        MINE,
        SHIELD
    }
    public WeaponSpecialType type;

    public float coolDown = 1.0f;
    public float timer = 99.0f;

    public int red_cost = 0;
    public int blue_cost = 0;
    public int green_cost = 0;

    public WeaponSpecialComponent(WeaponSpecialType type)
    {
        this.type = type;

        switch (type)
        {
            case MINE:
            {
                red_cost = 1;
                blue_cost = 0;
                green_cost = 0;
            } break;
            case SHIELD:
            {
                red_cost = 0;
                blue_cost = 1;
                green_cost = 0;
            } break;
        }

    }

}
