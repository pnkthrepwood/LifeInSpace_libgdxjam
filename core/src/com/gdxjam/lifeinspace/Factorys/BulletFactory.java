package com.gdxjam.lifeinspace.Factorys;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.lifeinspace.Components.BulletComponent;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.SinusBehaviourComponent;
import com.gdxjam.lifeinspace.Components.TypeComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.TextureManager;

/**
 * Created by Jesús Atalaya on 20/12/2015.
 */
public class BulletFactory
{
    public static Gaem gaem;

    public static void shootTripleBullet(float x, float y, float angle)
    {
        shootBullet(x, y, angle);
        shootBullet(x, y, angle+45);
        shootBullet(x, y, angle-45);
    }

    public static void shootBullet(float x, float y, float angle)
    {
        Texture tex = TextureManager.getTexture("bulletcollection.png");
        TextureRegion texreg =  new TextureRegion();
        texreg.setRegion(tex);
        texreg.setRegion(0, 0, 8, 13);
        RenderComponent rc = new RenderComponent(new Sprite(texreg));
        rc.rotation = angle;

        BulletComponent bc = new BulletComponent();
        bc.lifeTime = 0.5f;

        Entity bullet = new Entity();
        bullet.add(new PositionComponent(x, y));
        bullet.add(new VelocityComponent(500* MathUtils.cosDeg(angle + 90), 500*MathUtils.sinDeg(angle + 90)));
        bullet.add(rc);
        bullet.add(bc);
        bullet.add(new CollisionComponent(8, 13));
        bullet.add(new TypeComponent(TypeComponent.TypeEntity.BULLET));

        gaem.engine.addEntity(bullet);
    }
}
