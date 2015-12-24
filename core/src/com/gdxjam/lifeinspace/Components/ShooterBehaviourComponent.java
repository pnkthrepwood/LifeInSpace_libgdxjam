package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by threpwood on 24/12/2015.
 */
public class ShooterBehaviourComponent implements Component
{
    public float timer = 0.0f;
    public float shoot_time = 0.0f;

    public ShooterBehaviourComponent(float shoot_time)
    {
        this.shoot_time = shoot_time;
        this.timer = shoot_time;
    }

}
