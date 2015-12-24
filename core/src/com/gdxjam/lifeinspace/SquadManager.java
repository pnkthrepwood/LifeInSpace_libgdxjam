package com.gdxjam.lifeinspace;

import java.util.HashMap;

/**
 * Created by threpwood on 24/12/2015.
 */
public class SquadManager
{

    static HashMap<Integer, Integer> squadCounter = new HashMap<>();

    static int squad_number_generator = 0;

    public static int registerNewSquad(int size)
    {
        squadCounter.put(++squad_number_generator, size);
        return squad_number_generator;
    }

    public static void enemyFromSquadKilled(int squad)
    {
        Integer ct = squadCounter.get(squad);
        ct--;
        if (ct == 0)
        {
            //To-do: Squad just lost the last guy! Do things
            System.out.println("Squad killed: "+squad);

            squadCounter.remove(ct);
        }
        else
        {
            squadCounter.replace(squad, ct);
        }

    }

}
