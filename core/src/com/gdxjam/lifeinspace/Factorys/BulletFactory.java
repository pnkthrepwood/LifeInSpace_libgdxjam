package com.gdxjam.lifeinspace.Factorys;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.gdxjam.lifeinspace.Components.AnimationComponent;
import com.gdxjam.lifeinspace.Components.BulletComponent;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
import com.gdxjam.lifeinspace.Components.MineComponent;
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

    public static void fireWeapon(float x, float y, float angle, WeaponComponent weapon)
    {
        if (weapon.type == WeaponComponent.WeaponType.ENEMY_OCTOPUS)
        {
            shootBullet(x,y,angle-40,weapon);
            shootBullet(x,y,angle-80,weapon);
            shootBullet(x,y,angle,weapon);
            shootBullet(x,y,angle+40,weapon);
            shootBullet(x,y,angle+80,weapon);
        }
        else if (weapon.type == WeaponComponent.WeaponType.PLAYER_DOUBLE_WEAPON)
        {
            shootBullet(x+10,y,angle-5,weapon);
            shootBullet(x-10,y,angle+5,weapon);
        }
        else if (weapon.type == WeaponComponent.WeaponType.PLAYER_TRIPLE_WEAPON)
        {
            shootTripleBullet(x, y, angle, weapon);
        }
        else
        {
            shootBullet(x,y,angle,weapon);
        }
    }

    public static void shootTripleBullet(float x, float y, float angle, WeaponComponent weapon)
    {
        shootBullet(x, y, angle, weapon);
        shootBullet(x, y, angle+60, weapon);
        shootBullet(x, y, angle-60, weapon);
    }

    public static void shootBullet(float x, float y, float angle, WeaponComponent weapon)
    {
        Entity bullet = Gaem.engine.createEntity();

        angle += MathUtils.random(-weapon.accuracy/2, weapon.accuracy/2);

        Texture tex = TextureManager.getTexture("bullets.png");
        RenderComponent rc = new RenderComponent(new Sprite(new TextureRegion(tex, ((weapon.friendly) ?0:16), 0, 16, 16)));
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

    public static void dropMine(float x, float y)
    {
        Entity mine = Gaem.engine.createEntity();

        Texture tex = TextureManager.getTexture("mine.png");
        RenderComponent rc = new RenderComponent(
                new Sprite(
                        new TextureRegion(tex,0,0,16,16)
                )
        );
        mine.add(rc);

        Animation anim = new Animation(3.0f/5,
                new TextureRegion(tex, 0, 0, 16 ,16),
                new TextureRegion(tex, 16, 0, 16 ,16),
                new TextureRegion(tex, 16, 16, 16 ,16),
                new TextureRegion(tex, 16, 0, 16 ,16),
                new TextureRegion(tex, 0, 16, 16 ,16)
        );
        anim.setPlayMode(Animation.PlayMode.LOOP);
        mine.add(new AnimationComponent(anim));

        mine.add(new PositionComponent(x, y));

        mine.add(new MineComponent(3));

        gaem.engine.addEntity(mine);
    }

    public static void shootExplosion(float x, float y)
    {
        Entity bullet = Gaem.engine.createEntity();
        bullet.add(new PositionComponent(x, y));
        bullet.add(new CollisionComponent(128, 128));
        bullet.add(new TypeComponent(TypeComponent.TypeEntity.BULLET));
        BulletComponent bc = new BulletComponent();
        bc.lifeTime = 0.6f;
        bc.friendly = true;
        bc.damage = 10;
        bc.indestructible = true;
        bullet.add(bc);
        gaem.engine.addEntity(bullet);
    }
}
