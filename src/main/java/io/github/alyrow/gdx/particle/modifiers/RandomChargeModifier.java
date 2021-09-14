package io.github.alyrow.gdx.particle.modifiers;

import com.badlogic.gdx.math.MathUtils;

/**
 * @author Minecraftian14
 */
public class RandomChargeModifier extends Modifier {

    private final float min;
    private final float ran;

    public RandomChargeModifier() {
        this(-10, 10);
    }

    public RandomChargeModifier(float min, float max) {
        this.min = min;
        this.ran = max - min;
    }

    @Override
    public void modify() {
        setCharge(min + ran * MathUtils.random());
    }

}
