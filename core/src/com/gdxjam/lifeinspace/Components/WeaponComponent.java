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
        PLAYER_WEAPON,
        ENEMY_WEAPON
    }

    public float coolDown = 0.5f;
    public float accuracy = 7.5f; // accuracy%: 0 = perfect
    public float bulletLifetime = 0.2f;
    public boolean friendly = true;
    public float bulletSpeed = 700;

    public WeaponComponent(WeaponType type)
    {
        setStats(type);
    }

    private void setStats(WeaponType type)
    {
        switch (type)
        {
            case PLAYER_WEAPON:
                coolDown = 0.9f;
                accuracy = 7.5f;
                bulletLifetime = 0.2f;
                friendly = true;
                bulletSpeed = 700;
                break;

            case ENEMY_WEAPON:
                coolDown = -1.0f;
                accuracy = 15.0f;
                bulletLifetime = 3.0f;
                friendly = false;
                bulletSpeed = 500;
                break;
        }
    }

}
