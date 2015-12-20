package com.gdxjam.lifeinspace;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdxjam.lifeinspace.Components.BulletComponent;
import com.gdxjam.lifeinspace.Components.PositionComponent;
import com.gdxjam.lifeinspace.Components.RenderComponent;
import com.gdxjam.lifeinspace.Components.VelocityComponent;

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
        RenderComponent rc = new RenderComponent();

        Texture tex = TextureManager.getTexture("bulletcollection.png");
        TextureRegion texreg =  new TextureRegion();
        texreg.setRegion(tex);
        texreg.setRegion(20*0 + 0, 20*0 + 0, 20, 20);

        rc.spr = new Sprite(texreg);
        rc.batch = gaem.batch;

        //Bullet Component
        BulletComponent bc = new BulletComponent();
        bc.lifeTime = 0.5f;

        //Adding components to Bullet
        Entity bullet = new Entity();
        bullet.add(pc);
        bullet.add(vc);
        bullet.add(rc);
        bullet.add(bc);

        //Adding Bullet Entity to Engine
        gaem.engine.addEntity(bullet);
    }
}
