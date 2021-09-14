package io.github.alyrow.gdx.particle.modifiers;

import com.badlogic.gdx.math.MathUtils;

/**
 * @author Minecraftian14
 */
public class RandomMassModifier extends Modifier {

    private final float min;
    private final float ran;

    public RandomMassModifier() {
        this(1, 10);
    }

    public RandomMassModifier(float min, float max) {
        this.min = min;
        this.ran = max - min;
    }

    @Override
    public void modify() {
        setMass(min + ran * MathUtils.random());
    }


}
