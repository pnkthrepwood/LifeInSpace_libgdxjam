package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by threpwood on 24/12/2015.
 */
public class LifeComponent implements Component{
    public int lifes = 1;

    public LifeComponent(int lifes)
    {
        this.lifes = lifes;
    }
}
