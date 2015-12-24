package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by threpwood on 22/12/2015.
 */
public class SinusBehaviourComponent implements Component
{
    public float freq = 1;
    public float amp = 1;

    public SinusBehaviourComponent()
    {
    }

    public SinusBehaviourComponent(float freq, float amp)
    {
        this.freq = freq*20.0f;
        this.amp = amp*20.0f;
    }

    public float timer = 0;
}
