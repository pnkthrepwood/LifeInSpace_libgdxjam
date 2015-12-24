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

    public static String textScoreNice(int score)
    {
        String pre_score = "";
        if (score < 10000) pre_score += "0";
        if (score < 1000) pre_score += "0";
        if (score < 100) pre_score += "0";
        if (score < 10) pre_score += "0";

        return pre_score+score;
    }

    public static Vector2 inputMouseWorldPos(Camera cam)
    {
        Vector2 inputScreenPos = inputMouseWindowPosBounded();
        Vector3 pos = cam.unproject(new Vector3(inputScreenPos.x, inputScreenPos.y, 0));
        return new Vector2(pos.x, pos.y);
    }

    public static Vector2 inputMouseWindowPosBounded()
    {
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY());

        mousePos.x = Math.min(mousePos.x, Constants.RES_X);
        mousePos.x = Math.max(mousePos.x, 0);

        mousePos.y = Math.min(mousePos.y, Constants.RES_Y);
        mousePos.y = Math.max(mousePos.y, 0);

        return mousePos;
    }

}
