package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdxjam.lifeinspace.TextureManager;

/**
 * Created by threpwood on 20/12/2015.
 */

public class RenderComponent implements Component
{
    public Sprite spr;
    public float rotation;

    public RenderComponent(Sprite spr)
    {
        this.spr = spr;
    }
}