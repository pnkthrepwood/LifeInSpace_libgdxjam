package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Jesús Atalaya on 20/12/2015.
 */


public class WeaponComponent implements Component
{
    public float timer = 0f;

    public enum WeaponType
    {
        PLAYER_WEAPON,
        PLAYER_DOUBLE_WEAPON,
        PLAYER_TRIPLE_WEAPON,

        ENEMY_WEAPON,
        ENEMY_OCTOPUS
    }

    public float coolDown = 0.5f;
    public float accuracy = 7.5f; // accuracy%: 0 = perfect
    public float bulletLifetime = 0.2f;
    public boolean friendly = true;
    public float bulletSpeed = 700;
    public WeaponType type;

    public WeaponComponent(WeaponType type)
    {
        setStats(type);
    }

    private void setStats(WeaponType type)
    {
        this.type = type;

        switch (type)
        {
            case PLAYER_WEAPON:
            case PLAYER_DOUBLE_WEAPON:
                accuracy = 0.0f;
                friendly = true;

                coolDown = 0.5f;
                bulletLifetime = 0.35f;
                bulletSpeed = 500;
                break;

            case ENEMY_WEAPON:
                accuracy = 35.0f;
                friendly = false;

                coolDown = MathUtils.random(2.0f, 4.0f);
                bulletLifetime = 30.0f;
                bulletSpeed = MathUtils.random(250, 399);
                break;

            case ENEMY_OCTOPUS:
                accuracy = 30.0f;
                friendly = false;

                coolDown = MathUtils.random(3.0f, 4.0f);
                bulletLifetime = 30.0f;
                bulletSpeed = MathUtils.random(300, 399);
                break;
        }
    }

}
