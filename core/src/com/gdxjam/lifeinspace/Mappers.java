package com.gdxjam.lifeinspace;

import com.badlogic.ashley.core.ComponentMapper;
import com.gdxjam.lifeinspace.Components.BulletComponent;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.CircleShapeComponent;
import com.gdxjam.lifeinspace.Components.LifeComponent;
import com.gdxjam.lifeinspace.Components.ShooterBehaviourComponent;
import com.gdxjam.lifeinspace.Components.SinusBehaviourComponent;
import com.gdxjam.lifeinspace.Components.SquadComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;

/**
 * Created by threpwood on 20/12/2015.
 */
public class Mappers
{
    public static ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static ComponentMapper<CollisionComponent> collision = ComponentMapper.getFor(CollisionComponent.class);
    public static ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
    public static ComponentMapper<WeaponComponent> weapon = ComponentMapper.getFor(WeaponComponent.class);
    public static ComponentMapper<RenderComponent> render = ComponentMapper.getFor(RenderComponent.class);
    public static ComponentMapper<BulletComponent> bullet = ComponentMapper.getFor(BulletComponent.class);
    public static ComponentMapper<TypeComponent> type = ComponentMapper.getFor(TypeComponent.class);
    public static ComponentMapper<CircleShapeComponent> shape = ComponentMapper.getFor(CircleShapeComponent.class);
    public static ComponentMapper<SinusBehaviourComponent> sinus_behaviour = ComponentMapper.getFor(SinusBehaviourComponent.class);
    public static ComponentMapper<SquadComponent> squad = ComponentMapper.getFor(SquadComponent.class);
    public static ComponentMapper<ShooterBehaviourComponent> shoot_behaviour = ComponentMapper.getFor(ShooterBehaviourComponent.class);
    public static ComponentMapper<LifeComponent> lifes = ComponentMapper.getFor(LifeComponent.class);
}
