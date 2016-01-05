package com.gdxjam.lifeinspace.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by threpwood on 25/12/2015.
 */
public class AnimationComponent implements Component{

    public Animation animation;
    public float timer = 0.0f;

    public AnimationComponent(Animation animation)
    {
        this.animation = animation;
        this.timer = 0.0f;
    }

}
