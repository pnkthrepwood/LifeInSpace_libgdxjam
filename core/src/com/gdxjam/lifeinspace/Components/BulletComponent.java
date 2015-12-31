package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by Jesús Atalaya on 20/12/2015.
 */
public class BulletComponent implements Component
{
    public float lifeTime = 0f;
    public float timer = 0f;

    public boolean friendly = true;
    public int damage = 1;

    public boolean indestructible = false;
}
