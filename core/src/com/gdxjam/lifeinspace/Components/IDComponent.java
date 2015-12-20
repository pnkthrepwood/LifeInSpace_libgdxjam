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
        ENEMY
    }

    IDEntity type;

    public IDComponent(IDEntity type)
    {
        this.type = type;
    }
}
