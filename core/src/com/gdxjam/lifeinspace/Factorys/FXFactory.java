package com.gdxjam.lifeinspace.Factorys;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdxjam.lifeinspace.Components.AnimationComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.TextureManager;

/**
 * Created by threpwood on 25/12/2015.
 */
public class FXFactory {

    public static void makeExplosion(float x, float y)
    {

        Texture tex = TextureManager.getTexture("explosions.png");

        Animation anim = new Animation(0.2f/6,
                new TextureRegion(tex, 640, 58, 16 ,16),
                new TextureRegion(tex, 660, 58, 16 ,16),
                new TextureRegion(tex, 680, 58, 16 ,16),
                new TextureRegion(tex, 700, 58, 16 ,16),
                new TextureRegion(tex, 720, 58, 16 ,16),
                new TextureRegion(tex, 740, 58, 16 ,16)
        );
        anim.setPlayMode(Animation.PlayMode.NORMAL);

        Entity entity = new Entity();
        entity.add(new PositionComponent(x, y));
        entity.add(new AnimationComponent(anim));
        entity.add(new RenderComponent(new Sprite(new TextureRegion(tex, 640, 58, 16 ,16))));
        Gaem.engine.addEntity(entity);

    }

}
