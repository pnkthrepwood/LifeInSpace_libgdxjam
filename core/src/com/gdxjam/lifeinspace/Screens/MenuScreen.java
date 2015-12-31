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
    BitmapFont menu_font;

    Stage stage;

    String text_menu_title;

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


        Label.LabelStyle style_title = new Label.LabelStyle(menu_font, Color.WHITE);
        Label label_title = new Label("PRESS THE [ORANGE]INTRO[]", style_title);
        label_title.setAlignment(Align.left);
        label_title.setAlignment(Align.left, Align.left);
        label_title.setX(Constants.RES_X / 3);
        label_title.setY(Constants.RES_Y / 2);
        stage.addActor(label_title);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
