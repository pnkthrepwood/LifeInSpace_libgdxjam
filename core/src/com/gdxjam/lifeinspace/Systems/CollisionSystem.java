package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.LifeComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;
import com.gdxjam.lifeinspace.Factorys.FXFactory;
import com.gdxjam.lifeinspace.Factorys.PowerupFactory;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.Mappers;
import com.gdxjam.lifeinspace.PlayerManager;
import com.gdxjam.lifeinspace.SquadManager;

/**
 * Created by threpwood on 20/12/2015.
 */
public class CollisionSystem extends IteratingSystem
{

    private Engine engine;

    public CollisionSystem(Engine engine)
    {
        super(Family.all(CollisionComponent.class).get());
        this.engine = engine;
    }

    public void processEntity(Entity entity, float deltaTime)
    {
        Family family = Family.all(CollisionComponent.class).get();
        ImmutableArray<Entity> entities = engine.getEntitiesFor(family);

        PositionComponent pos_me = Mappers.position.get(entity);
        CollisionComponent col_me = Mappers.collision.get(entity);
        Rectangle rect_me = new Rectangle(pos_me.X() - col_me.sizeX*0.5f,
                                          pos_me.y - col_me.sizeY*0.5f,
                                          col_me.sizeX,
                                          col_me.sizeY);

        for (Entity other : entities)
        {
            if (other == entity) continue;

            PositionComponent pos_other = Mappers.position.get(other);
            CollisionComponent col_other = Mappers.collision.get(other);
            Rectangle rect_other = new Rectangle(
                    pos_other.X() - col_other.sizeX*0.5f,
                    pos_other.y - col_other.sizeY*0.5f,
                    col_other.sizeX,
                    col_other.sizeY);

            if (rect_me.overlaps(rect_other))
            {
                TypeComponent.TypeEntity type_me = Mappers.type.get(entity).type;
                TypeComponent.TypeEntity type_other = Mappers.type.get(other).type;


                //// BULLET -> ENEMY ////
                if (type_me == TypeComponent.TypeEntity.BULLET
                    && type_other == TypeComponent.TypeEntity.ENEMY
                    && Mappers.bullet.get(entity).friendly )
                {
                    doFriendlyBulletHitsEnemy(entity, other);
                }


                //// BULLET -> PLAYER ////
                if (type_me == TypeComponent.TypeEntity.BULLET
                        && type_other == TypeComponent.TypeEntity.SHIP
                        && !Mappers.bullet.get(entity).friendly )
                {
                    engine.removeEntity(entity);
                    FXFactory.makeExplosion(pos_other.X(), pos_other.y);

                    doShipGetHurt(other);
                }

                //// PLAYER -> POWERUP ////
                if (type_me == TypeComponent.TypeEntity.SHIP
                        && type_other == TypeComponent.TypeEntity.POWERUP )
                {
                    PowerupFactory.PowerUpType type = Mappers.powerup.get(other).type;
                    switch (type)
                    {
                        case FIRE_RANGE:
                        {
                            WeaponComponent wc = Mappers.weapon.get(entity);
                            wc.bulletLifetime += 0.05f;
                            PlayerManager.red_orbs++;
                        } break;
                        case FIRE_SPEED:
                        {
                            WeaponComponent wc = Mappers.weapon.get(entity);
                            wc.coolDown -= 0.05f;
                            if (wc.coolDown < 0.5f) wc.coolDown = 0.5f;
                            PlayerManager.green_orbs++;
                        } break;
                        case FLY_SPEED:
                        {
                            PlayerManager.ship_speed += 0.1f;
                            PlayerManager.blue_orbs++;
                        } break;
                    }

                    FXFactory.makeCatchpowerup(pos_other.X(), pos_other.y, type);

                    engine.removeEntity(other);
                }

                // PLAYER -> ENEMY //
                if (type_me == TypeComponent.TypeEntity.SHIP
                    && type_other == TypeComponent.TypeEntity.ENEMY )
                {
                    doShipGetHurt(entity);

                }



            }
        }

    }


    void doFriendlyBulletHitsEnemy(Entity bullet, Entity enemy)
    {
        PositionComponent pos_bullet = Mappers.position.get(bullet);
        PositionComponent pos_enemy = Mappers.position.get(enemy);

        boolean is_killed = false;
        if (Mappers.lifes.has(enemy))
        {
            LifeComponent lc = Mappers.lifes.get(enemy);

            lc.lifes -= Mappers.bullet.get(bullet).damage;

            if (lc.lifes <= 0)
            {
                is_killed = true;
            }

        }
        else is_killed = true;

        if (is_killed)
        {
            if (Mappers.squad.has(enemy))
            {
                int squad = Mappers.squad.get(enemy).squad;

                SquadManager.enemyFromSquadKilled(squad,
                        pos_enemy.X(),
                        pos_enemy.y);
            }
            engine.removeEntity(enemy);

            FXFactory.makeExplosion(pos_enemy.X(), pos_enemy.y);

            //*

            PlayerManager.score += 10;
        }
        else
        {
            FXFactory.makeExplosion(pos_bullet.X(), pos_bullet.y);
        }


        if (Mappers.monster.has(enemy))
        {
            FXFactory.makeDissapearEnemy(
                    pos_enemy.X(), pos_enemy.y,
                    Mappers.monster.get(enemy),
                    is_killed ? 0.1f : 0.01f);
        }

        if (!Mappers.bullet.get(bullet).indestructible)
            engine.removeEntity(bullet);
    }


    public void doShipGetHurt(Entity ship)
    {
        PositionComponent pos = Mappers.position.get(ship);
        FXFactory.makeExplosion(pos.X(), pos.y);
        Gaem.engine.removeEntity(ship);
        PlayerManager.is_game_over = true;
    }

}