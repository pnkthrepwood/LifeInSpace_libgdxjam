package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by threpwood on 22/12/2015.
 */
public class SinusBehaviourComponent implements Component
{
    public float freq = MathUtils.random(2, 2)*20.0f;
    public float amp = MathUtils.random(1, 1)*5*20.0f;

    public SinusBehaviourComponent()
    {
    }

    public SinusBehaviourComponent(float freq, float amp)
    {
        this.freq = freq;
        this.amp = amp;
    }

    public float timer = 0;
}
