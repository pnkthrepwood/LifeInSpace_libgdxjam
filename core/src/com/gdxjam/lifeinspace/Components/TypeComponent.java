package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by threpwood on 20/12/2015.
 */
public class TypeComponent implements Component
{
    public enum TypeEntity
    {
        SHIP,
        ENEMY,
        BULLET
    }

    public TypeEntity type;

    public TypeComponent(TypeEntity type)
    {
        this.type = type;
    }
}
