package com.gdxjam.lifeinspace;

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
    public static float chance_shooter_enemy = 0;
    public static float enemy_snake_long = 3;
    public static float enemy_snake_long_max = 10;

    public static void reset()
    {
        score = 0;
        ship_speed = 2;
        red_orbs = 0;
        blue_orbs = 0;
        green_orbs = 0;
        is_game_over = false;

        chance_snake_enemy = 0.08f;
        chance_shooter_enemy = 0.04f;

        enemy_snake_long = 3;
        enemy_snake_long_max = 10;
    }

}
