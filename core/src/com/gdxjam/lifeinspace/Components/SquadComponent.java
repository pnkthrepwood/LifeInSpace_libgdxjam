package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by threpwood on 24/12/2015.
 */
public class SquadComponent implements Component
{
    public int squad = 0;

    public SquadComponent(int squad)
    {
        this.squad = squad;
    }
}
