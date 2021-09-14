package io.github.alyrow.gdx.particle.modifiers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

/**
 * @author alyrow
 */
public class GradientColors extends Modifier {

    Color color1;
    Color color2;

    public GradientColors() {
    }

    public GradientColors(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
    }

    @Override
    public void modify() {
        setColor(new Color().set(color1).lerp(color2, MathUtils.random()));
    }

}
