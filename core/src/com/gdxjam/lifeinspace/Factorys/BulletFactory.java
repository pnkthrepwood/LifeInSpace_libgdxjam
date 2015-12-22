package com.gdxjam.lifeinspace.Factorys;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdxjam.lifeinspace.Components.BulletComponent;
import com.gdxjam.lifeinspace.Components.CollisionComponent;
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

    public static void createBullet(float x, float y)
    {

        //Bullet Position Parameter
        PositionComponent pc = new PositionComponent();
        pc.x = x;
        pc.y = y;

        //Bullet Velocity Component
        VelocityComponent vc = new VelocityComponent();
        vc.y = 500;

        //Bullet Render Component
        Texture tex = TextureManager.getTexture("bulletcollection.png");
        TextureRegion texreg =  new TextureRegion();
        texreg.setRegion(tex);
        texreg.setRegion(0, 0, 8, 13);
        RenderComponent rc = new RenderComponent(new Sprite(texreg), gaem.batch);

        //Bullet Component
        BulletComponent bc = new BulletComponent();
        bc.lifeTime = 0.5f;

        //Adding components to Bullet
        Entity bullet = new Entity();
        bullet.add(pc);
        bullet.add(vc);
        bullet.add(rc);
        bullet.add(bc);

        bullet.add(new CollisionComponent(8, 13));

        bullet.add(new TypeComponent(TypeComponent.TypeEntity.BULLET));

        //Adding Bullet Entity to Engine
        gaem.engine.addEntity(bullet);
    }
}
