package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by threpwood on 20/12/2015.
 */
public class CollisionComponent implements Component
{

    public float sizeX = 1.0f;
    public float sizeY = 1.0f;

    public CollisionComponent(float sizeX, float sizeY)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
}



