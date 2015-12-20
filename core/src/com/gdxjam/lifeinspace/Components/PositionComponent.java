package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by threpwood on 20/12/2015.
 */
public class PositionComponent implements Component
{
    public float x = 0.0f;
    public float y = 0.0f;

    public PositionComponent() {}

    public PositionComponent(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
}