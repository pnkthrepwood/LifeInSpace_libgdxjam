package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by threpwood on 20/12/2015.
 */
public class VelocityComponent implements Component
{
    public float x = 0.0f;
    public float y = 0.0f;

    public float minSpeed = 200;
    public float maxSpeed = 200;

}