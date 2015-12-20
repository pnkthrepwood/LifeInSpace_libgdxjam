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
    public SpriteBatch batch;
    public Sprite spr;

    public RenderComponent(Sprite spr, SpriteBatch batch)
    {
        this.spr = spr;
        this.batch = batch;
    }
}