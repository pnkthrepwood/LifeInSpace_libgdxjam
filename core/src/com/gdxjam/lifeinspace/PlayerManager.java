package com.gdxjam.lifeinspace;

import com.badlogic.gdx.math.MathUtils;

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
        private static final int SIZE = VALUES.size();

        public static LevelUpgrade roll()  {
            return VALUES.get( MathUtils.random(SIZE-1) );
        }
    }

    static LevelUpgrade player_level_event_choice_1, player_level_event_choice_2;
    public static void levelUp()
    {
        player_level++;

        player_level_event = true;

        rollLevelUpgrade();

        System.out.println("LEVEL UP!");
    }

    public static void rollLevelUpgrade()
    {
        player_level_event_choice_1 = LevelUpgrade.roll();
        do
        {
            player_level_event_choice_2 = LevelUpgrade.roll();
        } while (player_level_event_choice_2 == player_level_event_choice_1);
    }

}
