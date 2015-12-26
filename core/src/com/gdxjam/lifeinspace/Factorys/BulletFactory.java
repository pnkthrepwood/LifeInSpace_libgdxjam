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
import com.gdxjam.lifeinspace.Components.WeaponComponent;
import com.gdxjam.lifeinspace.Gaem;
import com.gdxjam.lifeinspace.TextureManager;

/**
 * Created by Jesús Atalaya on 20/12/2015.
 */
public class BulletFactory
{
    public static Gaem gaem;

    /*
    public static void shootTripleBullet(float x, float y, float angle, BulletType type)
    {
        shootBullet(x, y, angle, type);
        shootBullet(x, y, angle+45, type);
        shootBullet(x, y, angle-45, type);
    }
    */

    public enum BulletType{
        PLAYER,
        ENEMY
    }

    public static void shootBullet(float x, float y, float angle, WeaponComponent weapon)
    {
        Entity bullet = new Entity();
        Texture tex = TextureManager.getTexture("BulletCollection.png");
        TextureRegion texreg =  new TextureRegion(tex);

        BulletComponent bc = new BulletComponent();

        switch (type){
            case PLAYER:
                texreg.setRegion(0, 0, 8, 13);
                bc.lifeTime = 0.20f;
                bc.friendly = true;
                break;
            case ENEMY:
                texreg.setRegion(8, 0, 8, 13);
                bc.lifeTime = 2.0f;
                bc.friendly = false;
                break;
        }

        RenderComponent rc = new RenderComponent(new Sprite(texreg));
        rc.rotation = angle;

        bullet.add(bc);
        bullet.add(new PositionComponent(x, y));
        bullet.add(new VelocityComponent(700* MathUtils.cosDeg(angle + 90), 700*MathUtils.sinDeg(angle + 90)));
        bullet.add(rc);
        bullet.add(new CollisionComponent(8, 13));
        bullet.add(new TypeComponent(TypeComponent.TypeEntity.BULLET));

        gaem.engine.addEntity(bullet);
    }
}
