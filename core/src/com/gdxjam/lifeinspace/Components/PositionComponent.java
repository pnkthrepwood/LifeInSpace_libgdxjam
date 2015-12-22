package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by threpwood on 20/12/2015.
 */
public class PositionComponent implements Component
{
    public float x_real = 0.0f;
    public float x_offset = 0.0f;
    public float y = 0.0f;

    public float X()
    {
        return x_offset + x_real;
    }

    public PositionComponent() {}

    public PositionComponent(float x, float y)
    {
        this.x_real = x;
        this.y = y;
    }
}