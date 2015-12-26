package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;
import com.gdxjam.lifeinspace.Factorys.PowerupFactory;

/**
 * Created by threpwood on 26/12/2015.
 */
public class PowerUpComponent implements Component {
    public PowerupFactory.PowerUpType type;

    public PowerUpComponent(PowerupFactory.PowerUpType type)
    {
        this.type = type;
    }
}
