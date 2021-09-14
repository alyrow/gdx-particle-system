package io.github.alyrow.gdx.particle.physics;

import com.badlogic.gdx.graphics.Color;
import io.github.alyrow.gdx.particle.Particle;
import io.github.alyrow.gdx.particle.ParticleView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alyrow
 */
public class PhysicParticle {
    public float x, x_start, y, y_start, r, width, height, mass = 1, charge = 1;
    @Deprecated
    public Map<String, Float> data = new HashMap<String, Float>();
    public Map<String, Float> data_float = new HashMap<String, Float>();
    public Map<String, Double> data_double = new HashMap<String, Double>();
    public Map<String, Integer> data_int = new HashMap<String, Integer>();
    public Map<String, Long> data_long = new HashMap<String, Long>();
    public Map<String, String> data_string = new HashMap<String, String>();
    private Particle _particle;
    private final ParticleView view;

    public PhysicParticle(float x, float y, ParticleView view) {
        this.x = x;
        this.y = y;
        x_start = x;
        y_start = y;
        this.view = view;
    }

    /**
     * Is it clear? :-3
     *
     * @param particle The particle
     */
    public void setThisNoSenseSetterForParticleThisIsALittleReferenceToTheOrganisationOfThisLibDOnTTakeNotePlease(Particle particle) {
        _particle = particle;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Check if a particle is inner screen
     *
     * @return {@code true} if inner screen else {@code false}
     */
    public boolean getIfInnerScreen() {
        return _particle.isInnerScreen;
    }

    /**
     * Delete the particle
     */
    public void deleteParticle() {
        _particle.dispose();
    }

    /**
     * Get particle light color
     *
     * @return {@link Color} of the particle light
     */
    public Color getColor() {
        return _particle.color;
    }

    /**
     * Set particle light color
     *
     * @param color The {@link Color} of the particle light
     */
    public void setColor(Color color) {
        _particle.color = color;
    }
}
