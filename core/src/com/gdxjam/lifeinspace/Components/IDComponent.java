package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by threpwood on 20/12/2015.
 */
public class IDComponent implements Component
{
    public enum IDEntity
    {
        SHIP,
        ENEMY,
        BULLET
    }

    public IDEntity type;

    public IDComponent(IDEntity type)
    {
        this.type = type;
    }
}
