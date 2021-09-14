package io.github.alyrow.gdx.particle;

import io.github.alyrow.gdx.particle.rules.ParticleEmissionDuration;
import io.github.alyrow.gdx.particle.rules.ParticleEmissionNumber;
import io.github.alyrow.gdx.particle.rules.ParticleLife;

/**
 * @author alyrow
 * This class defines the rules for the particle system and particles
 */
public class ParticleRules/* implements Json.Serializable*/ {
    /**
     * Define life of particles
     *
     * @see ParticleLife
     */
    public ParticleLife life;

    /**
     * Define the number of particles emitted
     *
     * @see ParticleEmissionNumber
     */
    public ParticleEmissionNumber number;

    /**
     * Define the duration of emission
     *
     * @see ParticleEmissionDuration
     */
    public ParticleEmissionDuration duration;

    /**
     * Well, that is the constructor...
     */
    public ParticleRules() {
        /*this.life = new ParticleLife(5f);
        this.number = new ParticleEmissionNumber(ParticleEmissionNumber.INNER_SCREEN, 15);
        this.duration = new ParticleEmissionDuration(true);*/
    }

    /**
     * @param life Set life of particles
     * @see ParticleLife
     */
    public void setLife(ParticleLife life) {
        this.life = life;
    }

    /**
     * @param number Set number of particles emitted
     * @see ParticleEmissionNumber
     */
    public void setNumber(ParticleEmissionNumber number) {
        this.number = number;
    }

    /**
     * @param duration Set duration of emission
     * @see ParticleEmissionDuration
     */
    public void setDuration(ParticleEmissionDuration duration) {
        this.duration = duration;
    }
}
