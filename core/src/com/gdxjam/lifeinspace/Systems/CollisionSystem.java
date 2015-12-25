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
import com.gdxjam.lifeinspace.Factorys.PowerupFactory;
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

                if (type_me == TypeComponent.TypeEntity.BULLET
                    && type_other == TypeComponent.TypeEntity.ENEMY
                    && Mappers.bullet.get(entity).friendly )
                {
                    engine.removeEntity(entity);

                    boolean is_killed = false;
                    if (Mappers.lifes.has(other))
                    {
                        LifeComponent lc = Mappers.lifes.get(other);
                        if (--lc.lifes == 0)
                        {
                           is_killed = true;
                        }
                    }
                    else is_killed = true;

                    if (is_killed)
                    {
                        if (Mappers.squad.has(other))
                        {
                            int squad = Mappers.squad.get(other).squad;
                            SquadManager.enemyFromSquadKilled(squad);
                        }
                        engine.removeEntity(other);

                        PowerupFactory.spawnPowerup(pos_other.X(), pos_other.y);

                        PlayerManager.score += 10;
                    }
                }



                if (type_me == TypeComponent.TypeEntity.BULLET
                        && type_other == TypeComponent.TypeEntity.SHIP
                        && !Mappers.bullet.get(entity).friendly )
                {
                    engine.removeEntity(entity);
                    System.out.println("HURT!");
                }

            }
        }

    }
}