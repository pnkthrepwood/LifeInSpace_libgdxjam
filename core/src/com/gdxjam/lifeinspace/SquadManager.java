package com.gdxjam.lifeinspace;

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
            PlayerManager.score += 100;

            PowerupFactory.spawnPowerup(x, y);

            squadCounter.remove(ct);
        }
        else
        {
            squadCounter.replace(squad, ct);
        }

    }

}
