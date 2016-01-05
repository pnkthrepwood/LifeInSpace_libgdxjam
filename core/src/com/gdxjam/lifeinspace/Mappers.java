package com.gdxjam.lifeinspace;

import com.badlogic.ashley.core.ComponentMapper;
import com.gdxjam.lifeinspace.Components.AnimationComponent;
import com.gdxjam.lifeinspace.Components.BulletComponent;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.CircleShapeComponent;
import com.gdxjam.lifeinspace.Components.LifeComponent;
import com.gdxjam.lifeinspace.Components.MineComponent;
import com.gdxjam.lifeinspace.Components.MonsterComponent;
import com.gdxjam.lifeinspace.Components.PowerUpComponent;
import com.gdxjam.lifeinspace.Components.RenderEffectComponent;
import com.gdxjam.lifeinspace.Components.ShieldComponent;
import com.gdxjam.lifeinspace.Components.ShooterBehaviourComponent;
import com.gdxjam.lifeinspace.Components.SinusBehaviourComponent;
import com.gdxjam.lifeinspace.Components.SquadComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;
import com.gdxjam.lifeinspace.Components.WeaponSpecialComponent;

/**
 * Created by threpwood on 20/12/2015.
 */
public class Mappers
{
    public static ComponentMapper<AnimationComponent> animation = ComponentMapper.getFor(AnimationComponent.class);

    public static ComponentMapper<BulletComponent> bullet = ComponentMapper.getFor(BulletComponent.class);

    public static ComponentMapper<CircleShapeComponent> shape = ComponentMapper.getFor(CircleShapeComponent.class);
    public static ComponentMapper<CollisionComponent> collision = ComponentMapper.getFor(CollisionComponent.class);

    public static ComponentMapper<LifeComponent> lifes = ComponentMapper.getFor(LifeComponent.class);

    public static ComponentMapper<MineComponent> mines = ComponentMapper.getFor(MineComponent.class);
    public static ComponentMapper<MonsterComponent> monster = ComponentMapper.getFor(MonsterComponent.class);

    public static ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
    public static ComponentMapper<PowerUpComponent> powerup = ComponentMapper.getFor(PowerUpComponent.class);

    public static ComponentMapper<RenderComponent> render = ComponentMapper.getFor(RenderComponent.class);
    public static ComponentMapper<RenderEffectComponent> render_effect = ComponentMapper.getFor(RenderEffectComponent.class);

    public static ComponentMapper<ShieldComponent> shield = ComponentMapper.getFor(ShieldComponent.class);
    public static ComponentMapper<ShooterBehaviourComponent> shoot_behaviour = ComponentMapper.getFor(ShooterBehaviourComponent.class);
    public static ComponentMapper<SinusBehaviourComponent> sinus_behaviour = ComponentMapper.getFor(SinusBehaviourComponent.class);
    public static ComponentMapper<SquadComponent> squad = ComponentMapper.getFor(SquadComponent.class);

    public static ComponentMapper<TypeComponent> type = ComponentMapper.getFor(TypeComponent.class);

    public static ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);

    public static ComponentMapper<WeaponComponent> weapon = ComponentMapper.getFor(WeaponComponent.class);
    public static ComponentMapper<WeaponSpecialComponent> weapon_special = ComponentMapper.getFor(WeaponSpecialComponent.class);

}
