package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by ThrepwooD on 05/01/2016.
 */
public class DashComponent implements Component {

    public float timer = 0.0f;
    public float dashTime = 0.5f;

    public DashComponent()
    {
        timer = 0.0f;
        dashTime = 1.0f;
    }

}
