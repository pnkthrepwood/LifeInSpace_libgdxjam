package com.gdxjam.lifeinspace;

import com.badlogic.ashley.core.ComponentMapper;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;

/**
 * Created by threpwood on 20/12/2015.
 */
public class Mappers
{
    public static ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static ComponentMapper<CollisionComponent> collision = ComponentMapper.getFor(CollisionComponent.class);

}
