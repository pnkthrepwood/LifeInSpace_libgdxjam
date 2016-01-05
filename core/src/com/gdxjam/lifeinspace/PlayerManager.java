package com.gdxjam.lifeinspace;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.lifeinspace.Components.MonsterComponent;
import com.gdxjam.lifeinspace.Components.ShieldComponent;
import com.gdxjam.lifeinspace.Components.WeaponComponent;
import com.gdxjam.lifeinspace.Components.WeaponSpecialComponent;
import com.gdxjam.lifeinspace.Factorys.BulletFactory;
import com.gdxjam.lifeinspace.Factorys.EnemyFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by threpwood on 24/12/2015.
 */
public class PlayerManager
{
    public static int score = 0;
    public static float ship_speed = 2;
    public static int red_orbs = 0;
    public static int blue_orbs = 0;
    public static int green_orbs = 0;
    public static boolean is_game_over = false;

    public static float chance_snake_enemy = 0;
    public static float chance_snake_enemy_max = 0.7f;
    public static float enemy_snake_long = 3;
    public static float enemy_snake_long_min = 3;
    public static float enemy_snake_long_max = 7;

    public static float chance_shooter_enemy = 0;

    public static int stage = 0;

    public static int player_level = 0;
    public static boolean player_level_event = false;

    public static void reset()
    {
        score = 0;
        ship_speed = 2;
        red_orbs = 0;
        blue_orbs = 0;
        green_orbs = 0;
        is_game_over = false;

        chance_snake_enemy = 0.5f;
        chance_snake_enemy_max = 0.75f;
        chance_shooter_enemy = 0.25f;

        enemy_snake_long = 3;
        enemy_snake_long_min = 3;
        enemy_snake_long_max = 10;

        stage = 0;

        player_level = 0;
    }


    ////////////SPAWNER//////////////
    public static float timeToSpawnInStage(int stage)
    {
        switch (stage)
        {
            case 0: return 6.0f;
            case 1: return 6.0f;
            case 2: return 6.0f;
            case 3: return 6.0f;
            case 4: return 11.0f;
            case 5: return 11.0f;
            case 6: return 6.0f;
            case 7: return 11.0f;
            case 8: return 11.0f;
            case 9: return 10.0f;
        }
        return 14.0f;
    }

    public static float timeToNextStage(int stage)
    {
        switch (stage)
        {
            case 0: return 11.0f;
            case 1: return 11.0f;
            case 2: return 15.0f;
            case 3: return 15.0f;
            case 4: return 35.0f;
            case 5: return 11.0f;
            case 6: return 35.0f;
            case 7: return 50.0f;
            case 8: return 10.0f;
            case 9: return 35.0f;
        }
        return 75.0f;
    }

    public static void spawnInStage(int stage)
    {
        switch (stage)
        {
            case 0:
            {
                EnemyFactory.spawnSnakeEnemy(3);
            } break;
            case 1:
            {
                EnemyFactory.spawnSnakeEnemy(4);
                EnemyFactory.spawnSnakeEnemy(4);
            } break;
            case 2:
            {
               int snakes = MathUtils.random(1,3);
                for (int i = 0; i < snakes; i++)
                    EnemyFactory.spawnSnakeEnemy(MathUtils.random(3,5));
            } break;
            case 3:
            {
                EnemyFactory.spawnShooterEnemy();
            } break;
            case 4:
            {
                int snakes = MathUtils.random(1,3);
                for (int i = 0; i < snakes; i++)
                    EnemyFactory.spawnSnakeEnemy(MathUtils.random(4,6));
                int monsters = MathUtils.random(0,1);
                for (int i = 0; i < monsters; i++)
                    EnemyFactory.spawnShooterEnemy();
            } break;
            case 5:
            {

            } break;
            case 6:
            {
                int monsters = MathUtils.random(1,2);
                for (int i = 0; i < monsters; i++)
                    EnemyFactory.spawnUltraShooterEnemy();
            } break;
            case 7:
            {
                int snakes = MathUtils.random(1,3+stage-6);
                for (int i = 0; i < snakes; i++)
                    EnemyFactory.spawnSnakeEnemy(MathUtils.random(4+stage-4,6+stage-4));
                int monsters = MathUtils.random(0,1+stage-6);
                for (int i = 0; i < monsters; i++)
                    EnemyFactory.spawnShooterEnemy();
                int ultra = MathUtils.random(0,1);
                for (int i = 0; i < ultra; i++)
                    EnemyFactory.spawnUltraShooterEnemy();
            } break;
            case 8:
            {

            } break;
            case 9:
            {
                int octopus = MathUtils.random(1,2);
                for (int i = 0; i < octopus; i++)
                    EnemyFactory.spawnOctopusEnemy();
            } break;
            default:
            {
                int snakes = MathUtils.random(1,3+stage-6);
                for (int i = 0; i < snakes; i++)
                    EnemyFactory.spawnSnakeEnemy(MathUtils.random(4+stage-4,6+stage-4));
                int monsters = MathUtils.random(1,1+stage-6);
                for (int i = 0; i < monsters; i++)
                    EnemyFactory.spawnShooterEnemy();
                int ultra = MathUtils.random(1,1+stage-8);
                for (int i = 0; i < ultra; i++)
                    EnemyFactory.spawnUltraShooterEnemy();
                int octopus = MathUtils.random(0,1+stage-8);
                for (int i = 0; i < octopus; i++)
                    EnemyFactory.spawnOctopusEnemy();
            } break;
        }
    }



    ////////EXP/////////////

    public static int exp_before()
    {
        return exp(player_level-1);
    }

    public static int exp_next()
    {
        return exp(player_level);
    }

    public static int exp(int level)
    {
        switch (level)
        {
            case -1:
                return 0;
            case 0:
                return 55;
            case 1:
                return 150;
            case 2:
                return 320;
            case 3:
                return 775;
            case 4:
                return 1925;
            case 5:
                return 3815;
            case 6:
                return 7937;
            case 7:
            default:
                return 15555 * (player_level-6);
        }
    }

    public static void addScore(int plus)
    {
        score += plus;
        if (score > exp_next())
        {
            levelUp();
        }
    }

    public static void addExp(MonsterComponent mc)
    {
        if (mc.type == MonsterComponent.MonsterType.OCTOPUS)
        {
            addScore(17);
        }

        if (mc.type == MonsterComponent.MonsterType.INVADER)
        {
            addScore(9);
        }
        if (mc.type == MonsterComponent.MonsterType.SNAKE)
        {
            addScore(1);
        }
    }


    ///////////////LEVELUP////////////////////////////


    enum LevelUpgrade
    {
        FIRE_RATE,
        FIRE_DIST,
        SPEED,
        DOBLE_ATK,
        LATERAL_SHOOT,
        CONVERT_ORBS,
        LUCKY;

        private static final List<LevelUpgrade> VALUES =
                Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = 3;//VALUES.size();

        public static LevelUpgrade roll()  {
            return VALUES.get( MathUtils.random(SIZE-1) );
        }
    }

    public static LevelUpgrade player_level_event_choice_1, player_level_event_choice_2;
    public static void levelUp()
    {
        player_level++;

        player_level_event = true;

        rollLevelUpgrade();

        Gaem.engine.clearPools();
    }

    public static void rollLevelUpgrade()
    {
        player_level_event_choice_1 = LevelUpgrade.roll();
        do
        {
            player_level_event_choice_2 = LevelUpgrade.roll();
        } while (player_level_event_choice_2 == player_level_event_choice_1);
    }

    public static String stringForSkill(LevelUpgrade upgrade)
    {

        switch (upgrade)
        {
            case FIRE_DIST:
                return "LONG SHOOT";
            case CONVERT_ORBS:
                return "ORBS";
            case DOBLE_ATK:
                return "DOUBLE FIRE";
            case LATERAL_SHOOT:
                return "LATERAL SHOOT";
            case FIRE_RATE:
                return "FIRE RATE";
            case LUCKY:
                return "LUCK";
            case SPEED:
                return "SPEED";
            default:
                return "BONUS";
        }

    }

    public static void applySkillChoice(LevelUpgrade upgrade, Entity ship)
    {
        switch (upgrade)
        {
            case FIRE_DIST:
            {
                WeaponComponent wc = Mappers.weapon.get(ship);
                wc.bulletLifetime += 0.05f;
            } break;
            case FIRE_RATE:
            {
                WeaponComponent wc = Mappers.weapon.get(ship);
                wc.coolDown -= 0.20f;
                if (wc.coolDown < 0.15f) wc.coolDown = 0.15f;
            } break;
            case LATERAL_SHOOT:
            {
               //Todo
                PlayerManager.ship_speed += 0.25f;
            } break;
            case SPEED:
            {
                PlayerManager.ship_speed += 0.25f;
            } break;
            case LUCKY:
            {
                //Todo
                PlayerManager.ship_speed += 0.25f;
            } break;
            case DOBLE_ATK:
            {
                //Todo
                PlayerManager.ship_speed += 0.25f;
            } break;
            case CONVERT_ORBS:
            {
                //Todo
                PlayerManager.ship_speed += 0.25f;
            } break;
    }
    }

    /////////////////////////////////////

    public static void useSpecial(Entity ship)
    {
        WeaponSpecialComponent shipWeapon =  Mappers.weapon_special.get(ship);
        if (shipWeapon.timer > shipWeapon.coolDown
                && (PlayerManager.red_orbs >= shipWeapon.red_cost)
                && (PlayerManager.blue_orbs >= shipWeapon.blue_cost)
                && (PlayerManager.green_orbs >= shipWeapon.green_cost)
                )
        {

            switch (shipWeapon.type)
            {
                case MINE:
                {
                    BulletFactory.dropMine(
                            Mappers.position.get(ship).X(),
                            Mappers.position.get(ship).y
                    );
                    PlayerManager.red_orbs -= shipWeapon.red_cost;
                    PlayerManager.blue_orbs -= shipWeapon.blue_cost;
                    PlayerManager.green_orbs -= shipWeapon.green_cost;

                } break;
                case SHIELD:
                {

                    if (!Mappers.shield.has(ship))
                    {
                        ship.add(new ShieldComponent());

                        PlayerManager.red_orbs -= shipWeapon.red_cost;
                        PlayerManager.blue_orbs -= shipWeapon.blue_cost;
                        PlayerManager.green_orbs -= shipWeapon.green_cost;
                    }

                } break;
                default:break;
            }

            shipWeapon.timer = 0;
        }
    }

}
