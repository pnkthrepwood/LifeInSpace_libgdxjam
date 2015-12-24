package com.gdxjam.lifeinspace.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
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
        Rectangle rect_me = new Rectangle(pos_me.X(),
                                          pos_me.y,
                                          col_me.sizeX,
                                          col_me.sizeY);

        for (Entity other : entities)
        {
            if (other == entity) continue;

            PositionComponent pos_other = Mappers.position.get(other);
            CollisionComponent col_other = Mappers.collision.get(other);
            Rectangle rect_other = new Rectangle(pos_other.X(), pos_other.y, col_other.sizeX, col_other.sizeY);

            if (rect_me.overlaps(rect_other))
            {
                TypeComponent.TypeEntity type_me = Mappers.type.get(entity).type;
                TypeComponent.TypeEntity type_other = Mappers.type.get(other).type;

                if (type_me == TypeComponent.TypeEntity.BULLET
                    && type_other == TypeComponent.TypeEntity.ENEMY)
                {
                    engine.removeEntity(entity);

                    if (Mappers.squad.has(other))
                    {
                        int squad = Mappers.squad.get(other).squad;
                        SquadManager.enemyFromSquadKilled(squad);
                    }
                    engine.removeEntity(other);

                    PlayerManager.score += 10;

                    /*
                    EnemyFactory.spawnSnakeEnemy(
                            MathUtils.random(-Constants.RES_X/2, Constants.RES_X/2),
                            MathUtils.random(-Constants.RES_Y/2, Constants.RES_Y/2));
                    */
                }

            }
        }

    }
}