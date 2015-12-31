package com.gdxjam.lifeinspace;

import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.lifeinspace.Factorys.PowerupFactory;

import java.util.HashMap;

/**
 * Created by threpwood on 24/12/2015.
 */
public class SquadManager
{
    static HashMap<Integer, Integer> squadCounter = new HashMap<Integer, Integer>();

    static int squad_number_generator = 0;

    public static int registerNewSquad(int size)
    {
        squadCounter.put(++squad_number_generator, size);
        return squad_number_generator;
    }

    public static void enemyFromSquadKilled(int squad, float x, float y)
    {
        Integer ct = squadCounter.get(squad);
        ct--;
        if (ct == 0)
        {
            PlayerManager.addScore(5);

            float r  = MathUtils.random(0.0f, 3.0f);

            if (r < 1.0f)
            {
                PowerupFactory.spawnPowerup(x, y, PowerupFactory.PowerUpType.FIRE_RANGE);
            }
            else if (r < 2.0f)
            {
                PowerupFactory.spawnPowerup(x, y, PowerupFactory.PowerUpType.FIRE_SPEED);
            }
            else
            {
                PowerupFactory.spawnPowerup(x, y, PowerupFactory.PowerUpType.FLY_SPEED);
            }

            squadCounter.remove(ct);
        }
        else
        {
            squadCounter.remove(squad);
            squadCounter.put(squad, ct);
        }

    }

}
