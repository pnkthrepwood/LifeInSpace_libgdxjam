package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by threpwood on 22/12/2015.
 */

public class ShapeComponent implements Component
{
    public float xPos = 0f;
    public float yPos = 0f;
    public float radius = 0f;
    public Color color = new Color(1,1,1,1);

}