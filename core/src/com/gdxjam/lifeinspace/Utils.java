package com.gdxjam.lifeinspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by threpwood on 20/12/2015.
 */
public class Utils
{

    public static Vector2 inputCurrentWorldPos(Camera cam)
    {
        Vector2 inputScreenPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        Vector3 pos = cam.unproject(new Vector3(inputScreenPos.x, inputScreenPos.y, 0));
        return new Vector2(pos.x, pos.y);
    }

}
