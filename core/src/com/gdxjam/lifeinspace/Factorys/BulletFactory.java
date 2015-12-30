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


    public static void shootTripleBullet(float x, float y, float angle, WeaponComponent weapon)
    {
        shootBullet(x, y, angle, weapon);
        shootBullet(x, y, angle+45, weapon);
        shootBullet(x, y, angle-45, weapon);
    }

    public static void fireWeapon(float x, float y, float angle, WeaponComponent weapon)
    {
        shootBullet(x,y,angle,weapon);
    }


    public static void shootBullet(float x, float y, float angle, WeaponComponent weapon)
    {
        Entity bullet = new Entity();

        angle += MathUtils.random(-weapon.accuracy/2, weapon.accuracy/2);

        Texture tex = TextureManager.getTexture("bullets.png");
        RenderComponent rc = new RenderComponent(new Sprite(new TextureRegion(tex, 2+((weapon.friendly) ?0:16), 0, 14, 16)));
        rc.rotation = angle;
        bullet.add(rc);

        bullet.add(new PositionComponent(x, y));
        bullet.add(new VelocityComponent(
                weapon.bulletSpeed* MathUtils.cosDeg(angle + 90),
                weapon.bulletSpeed* MathUtils.sinDeg(angle + 90))
        );

        bullet.add(new CollisionComponent(14, 16));
        bullet.add(new TypeComponent(TypeComponent.TypeEntity.BULLET));

        BulletComponent bc = new BulletComponent();
        bc.lifeTime = weapon.bulletLifetime;
        bc.friendly = weapon.friendly;
        bullet.add(bc);

        gaem.engine.addEntity(bullet);
    }
}
