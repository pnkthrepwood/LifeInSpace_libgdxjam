package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by ThrepwooD on 30/12/2015.
 */
public class MineComponent implements Component {

    public float explodeTime = 3;
    public float timer = 0;

    public MineComponent(float timeToExplode)
    {
        explodeTime = timeToExplode;
    }

}
