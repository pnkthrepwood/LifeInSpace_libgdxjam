package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Jesús Atalaya on 20/12/2015.
 */


public class WeaponComponent implements Component
{
    public enum WeaponType
    {
        BASIC,
        FAST
    }

    public float coolDown = 0.5f;
    public float timer = 0f;

    public WeaponComponent()
    {
        setStats(WeaponType.BASIC);
    }

    public WeaponComponent(WeaponType type)
    {
        setStats(type);
    }

    private void setStats(WeaponType type)
    {
        switch (type)
        {
            case BASIC:
                coolDown = 0.5f;
                break;

            case FAST:
                coolDown = 0.25f;
                break;
        }
    }

}
