package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by threpwood on 22/12/2015.
 */
public class SinusBehaviourComponent implements Component
{
    public float freq = 5*20.0f;
    public float amp = 5*20.0f;

    public float timer = 0;
}
