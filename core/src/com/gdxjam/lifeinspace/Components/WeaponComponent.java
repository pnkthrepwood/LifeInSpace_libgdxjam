package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Jesús Atalaya on 20/12/2015.
 */


public class WeaponComponent implements Component
{
    public float timer = 0f;

    public enum WeaponType
    {
        BASIC,
        FAST
    }

    public float coolDown = 0.5f;
    public float accuracy = 10.0f; // accuracy%: 0 = perfect

    public WeaponComponent()
    {
        setStats(WeaponType.FAST);
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
                coolDown = 0.9f;
                break;

            case FAST:
                coolDown = 0.5f;
                break;
        }
    }

}
