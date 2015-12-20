package com.gdxjam.lifeinspace;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * Created by threpwood on 20/12/2015.
 */
public class TextureManager
{
    static HashMap<String, Texture> textureMap;

    public TextureManager()
    {
        textureMap = new HashMap<String, Texture>();
    }

    public void preLoadAll()
    {

    }

    public Texture getTexture(String name)
    {
        if (!textureMap.containsKey(name))
        {
            textureMap.put(name, new Texture(name));
        }
        return textureMap.get(name);
    }
}
