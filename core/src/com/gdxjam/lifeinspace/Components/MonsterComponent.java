package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;

/**
 * Created by threpwood on 31/12/2015.
 */
public class MonsterComponent implements Component {

    public enum MonsterType
    {
        SNAKE,
        INVADER,
        ULTRA
    }

    public MonsterType type;

    public MonsterComponent(MonsterType type)
    {
        this.type = type;
    }

}
