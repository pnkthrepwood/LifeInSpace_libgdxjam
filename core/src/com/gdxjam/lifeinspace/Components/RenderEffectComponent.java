package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by threpwood on 27/12/2015.
 */
public class RenderEffectComponent implements Component {

    public float timer = 0;

    public float timer_total = 0;
    public float scale_start = 1;
    public float scale_end = 1;
    public float alpha_start = 1;
    public float alpha_end = 1;
    public boolean single_use = false;


    public RenderEffectComponent(float t,
                                 float sc0, float sc1,
                                 float a0, float a1, boolean single_use)
    {
        this.single_use = single_use;

        scale_start = sc0;
        scale_end = sc1;

        alpha_start = a0;
        alpha_end = a1;

        timer_total = t;
        timer = 0.0f;
    }

}
