package com.gdxjam.lifeinspace.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gdxjam.lifeinspace.Constants;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.PlayerManager;
import com.gdxjam.lifeinspace.TextureManager;
import com.gdxjam.lifeinspace.Utils;

/**
 * Created by threpwood on 25/12/2015.
 */
public class MenuScreen implements Screen
{
    Gaem game;
    BitmapFont menu_font_big;
    BitmapFont menu_font;
    BitmapFont tip_font;

    Stage stage;

    String text_menu_title;

    Label label_title;
    Label label_title2;
    Label label_title3;

    public MenuScreen(Gaem game)
    {
        this.game = game;

        stage = new Stage(new FitViewport(Constants.RES_X, Constants.RES_Y));
        Gdx.input.setInputProcessor(stage);

        menu_font = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("bitDarling.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        menu_font = generator.generateFont(parameter);
        menu_font.getData().markupEnabled = true;



        FreeTypeFontGenerator.FreeTypeFontParameter parameter_big_title = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter_big_title.size = 90;
        parameter_big_title.color = Color.WHITE;
        parameter_big_title.borderWidth = 2;
        parameter_big_title.borderColor = Color.BLACK;
        menu_font_big = generator.generateFont(parameter_big_title);
        menu_font_big.getData().markupEnabled = true;
        Label.LabelStyle style_title = new Label.LabelStyle(menu_font_big, Color.WHITE);
        label_title = new Label("SPAEC GAEM", style_title);
        label_title.setAlignment(Align.center);
        label_title.setAlignment(Align.center, Align.center);
        label_title.setX(Constants.RES_X / 3.9f);
        label_title.setY(Constants.RES_Y*0.75f);
        stage.addActor(label_title);

        label_title2 = new Label("SPAEC GAEM", style_title);
        label_title2.setAlignment(Align.center);
        label_title2.setAlignment(Align.center, Align.center);
        label_title2.setX(Constants.RES_X / 3.9f);
        label_title2.setY(Constants.RES_Y*0.75f);
        stage.addActor(label_title2);

        label_title3 = new Label("SPAEC GAEM", style_title);
        label_title3.setAlignment(Align.center);
        label_title3.setAlignment(Align.center, Align.center);
        label_title3.setX(Constants.RES_X / 3.9f);
        label_title3.setY(Constants.RES_Y*0.75f);
        stage.addActor(label_title3);


        Label.LabelStyle style_intro = new Label.LabelStyle(menu_font, Color.WHITE);
        Label label_intro = new Label("PRESS THE [ORANGE]INTRO[]", style_intro);
        label_intro.setAlignment(Align.left);
        label_intro.setAlignment(Align.left, Align.left);
        label_intro.setX(Constants.RES_X / 3);
        label_intro.setY(Constants.RES_Y / 2);
        stage.addActor(label_intro);




        tip_font = new BitmapFont();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("bitDarling.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        tip_font = generator.generateFont(parameter);
        tip_font.getData().markupEnabled = true;


        Label.LabelStyle style_tip = new Label.LabelStyle(tip_font, Color.WHITE);
        Label label_tip = new Label(getRandomTip(), style_tip);
        label_tip.setAlignment(Align.left);
        label_tip.setAlignment(Align.left, Align.left);
        label_tip.setX(Constants.RES_X * 0.13f);
        label_tip.setY(Constants.RES_Y * 0.25f);
        stage.addActor(label_tip);


        Label.LabelStyle style_instructions = new Label.LabelStyle(tip_font, Color.WHITE);
        Label label_instructions = new Label("F1 - INSTRUCTIONS", style_instructions);
        label_instructions.setAlignment(Align.left);
        label_instructions.setAlignment(Align.left, Align.left);
        label_instructions.setX(Constants.RES_X * 0.60f);
        label_instructions.setY(Constants.RES_Y * 0.1f);
        stage.addActor(label_instructions);

    }



    static String[] tips = null;
    static int tips_count = 4;
    String getRandomTip()
    {
        if (tips == null)
        {
            tips = new String[tips_count];
            tips[0] = "TIP: YOU LOSE YOUR [YELLOW]SCORE[] WHEN YOU [RED]DIE[]";
            tips[1] = "TIP: IF IT [YELLOW]FLASHES[] IT [RED]DIES[]";
            tips[2] = "TIP: KEEP [BLUE]SHOOTING[] AS LONG AS YOU CAN";
            tips[3] = "TIP: [GREEN]ORBS[] ARE NICE";

        }

        return tips[MathUtils.random(0, tips_count-1)];
    }



    @Override
    public void show() {

    }

    float timer_flash_color_title = 0.0f;
    float timer_flash_color_title2 = 0.0f;
    float timer_flash_color_title3 = 0.0f;
    @Override
    public void render(float delta) {


        timer_flash_color_title += delta;
        if (timer_flash_color_title > 0.01f)
        {
            label_title.setColor(
                    0.33f*MathUtils.random(1,4),
                    0.33f*MathUtils.random(1,4),
                    0.33f*MathUtils.random(1,4),
                    0.33f);
            label_title.setX(Constants.RES_X * MathUtils.random(0.01f, 0.4f));
            label_title.setY(Constants.RES_Y * MathUtils.random(0.65f, 0.85f));
            timer_flash_color_title = 0.0f;
        }

        timer_flash_color_title2 += delta;
        if (timer_flash_color_title2 > 0.05f)
        {
            label_title2.setColor(
                    0.33f*MathUtils.random(1,4),
                    0.33f*MathUtils.random(1,4),
                    0.33f*MathUtils.random(1,4),
                    0.44f);
            label_title2.setX(Constants.RES_X * MathUtils.random(0.1f, 0.3f));
            label_title2.setY(Constants.RES_Y * MathUtils.random(0.70f, 0.80f));
            timer_flash_color_title2 = 0.0f;
        }

        timer_flash_color_title3 += delta;
        if (timer_flash_color_title3 > 0.15f)
        {
            label_title3.setColor(
                    0.33f*MathUtils.random(1,4),
                    0.33f*MathUtils.random(2,4),
                    0.33f*MathUtils.random(1,4),
                    1);
            timer_flash_color_title3 = 0.0f;
        }





        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            stage.dispose();
            game.setScreen(new PlayScreen(game));
            return;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

/*
        String text_title = "LIFE IN [RED]SPACE[]";

        Gaem.batch.begin();
        menu_font.draw(Gaem.batch,
                text_title,
                Constants.RES_X/2 -100,
                Constants.RES_Y/2);
        Gaem.batch.end();
*/
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
