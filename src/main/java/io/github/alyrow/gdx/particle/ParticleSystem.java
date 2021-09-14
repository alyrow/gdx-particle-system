package io.github.alyrow.gdx.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.alyrow.gdx.particle.modifiers.ModifierManager;
import io.github.alyrow.gdx.particle.physics.PhysicManager;
import io.github.alyrow.gdx.particle.physics.PhysicParticle;
import io.github.alyrow.gdx.particle.rules.ParticleEmissionNumber;
import io.github.alyrow.gdx.particle.texture.ParticleTexture;


/**
 * @author alyrow
 * Create a particle system
 */
public class ParticleSystem {
    public String version = "2.0.0-snapshot";
    public long version_code = 200000000000L;
    public long min_version_code = 200000000000L;


    /**
     * Define particles type
     *
     * @see ParticleType
     */
    public ParticleType type;

    /**
     * Define Texture of particles
     *
     * @see ParticleTexture
     */
    public ParticleTexture texture;
    /**
     * Array of all existing particles in the system
     */
    public Array<Particle> particles = new Array<>();
    /**
     * Time of emission
     */
    public long time;
    /**
     * Draw particles on this
     */
    public SpriteBatch batch;
    public ParticleView view;
    /**
     * Initial position of particles
     */
    float x, y;
    /**
     * Define rules as particles life, emission duration....
     * For more details :
     *
     * @see ParticleRules
     */
    private ParticleRules rules;
    private long test_long; //For check time elapsed or number of particles inner the screen
    /**
     * Manage physics on particles
     *
     * @see PhysicManager
     */
    private PhysicManager physicManager;

    /**
     * Manage modifier on particles
     *
     * @see ModifierManager
     */
    private ModifierManager modifierManager;

    /**
     * Constructor that create a particle system
     *
     * @param type Particles type (Halo, Texture, No texture)
     *             {@link ParticleType}
     * @param view For display particles on the screen
     */
    public ParticleSystem(ParticleType type, ParticleView view, SpriteBatch batch) {
        this.type = type;
        this.batch = batch;
        time = TimeUtils.millis();

        texture = this.type.particleTexture;

        modifierManager = new ModifierManager();

        this.view = view;
    }

    /**
     * Set texture of particles if `type` is set to `TEXTURE` or `HALO`
     *
     * @param texture Set texture with ParticleTexture object
     *                {@link ParticleTexture}
     * @see ParticleType
     */
    public void setTexture(ParticleTexture texture) {
        if (type.type == ParticleType.Type.NOTHING) {
            try {
                throw new Exception("Particle type is set to NOTHING, you cannot set texture for NOTHING.");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        this.texture = texture;
    }

    /**
     * For get the rules
     *
     * @return Return the rules in one single object
     * @see ParticleRules
     */
    public ParticleRules getRules() {
        return rules;
    }

    /**
     * Set the rules of particle system and particles.
     * /!\ THIS IS VERY IMPORTANT TO SET RULES
     *
     * @param rules All rules in one object ;)
     *              {@link ParticleRules}
     */
    public void setRules(ParticleRules rules) {
        this.rules = rules;
        if (rules.number.mode == ParticleEmissionNumber.PER_SECONDS) test_long = TimeUtils.millis();
    }

    /**
     * For get the physic manager
     *
     * @return the physic manager
     */
    public PhysicManager getPhysicManager() {
        return physicManager;
    }

    /**
     * Well, set physics rules to particles.
     *
     * @param physicManager The physic manager contains all physics rules in a single object
     *                      {@link PhysicManager}
     */
    public void setPhysicManager(PhysicManager physicManager) {
        this.physicManager = physicManager;
    }

    /**
     * Set the first position of particles. If you modify this, it won't affect existing particles but futures.
     *
     * @param x Set x position of futures particles
     * @param y Set y position of futures particles
     */
    public void setParticlesPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Free memory
     */
    public void dispose() {
        batch.dispose();
        particles.forEach(Particle::dispose);
        texture.dispose();
    }

    /**
     * Oh, if you want to see your particles, you need to call this in your render method...
     */
    public void render() {
        if (!rules.duration.infinite)
            if (TimeUtils.timeSinceMillis(time) >= rules.duration.duration * 1000) return;

        if (rules.number.mode == ParticleEmissionNumber.INNER_SCREEN) {
            test_long = 0;
            for (int i = 0; i < particles.size; i++) if (particles.get(i).isInnerScreen) test_long++;
            if (test_long < rules.number.getNumber())
                particles.add(applyModifiers(new Particle(rules.life.getLife(), rules.life.outer, texture.getTexture(), view, this, x, y, physicManager.getParticleForces(x, y, view), Color.WHITE, type)));
        } else {
            if (TimeUtils.timeSinceMillis(test_long) >= rules.number.seconds * 1000) {
                for (int j = 0; j < rules.number.getNumber(); j++)
                    particles.add(applyModifiers(new Particle(rules.life.getLife(), rules.life.outer, texture.getTexture(), view, this, x, y, physicManager.getParticleForces(x, y, view), Color.WHITE, type)));
                test_long = TimeUtils.millis();
            }
        }

        particles.forEach(particle -> particle.render(batch, view, physicManager.forces));

    }

    private Particle createParticle(float life, boolean outer, Texture texture, ParticleView view, ParticleSystem system, float x, float y, PhysicParticle physicParticle, Color color, ParticleType type) {
        /*if (particles_gc.size > 0) {
            Particle particle = particles_gc.get(particles_gc.size);
            particle.init(light, life, outer, texture, camera, system, x, y, physicParticle, worldPhysic, type);
            particles_gc.removeValue(particle, true);
            return particle;
        }*/
        return new Particle(life, outer, texture, view, system, x, y, physicParticle, color, type);
    }

    /**
     * Get the Modifier Manager in order to apply modifiers on particles
     *
     * @return {@link ModifierManager}
     */
    public ModifierManager getModifierManager() {
        return modifierManager;
    }

    /**
     * Set the Modifier Manager in order to apply modifiers on particles
     *
     * @param modifierManager The modifier manager
     */
    public void setModifierManager(ModifierManager modifierManager) {
        this.modifierManager = modifierManager;
    }

    private Particle applyModifiers(Particle particle) {
        for (int i = 0; i < modifierManager.modifiers.size; ++i)
            modifierManager.modifiers.get(i)._applyModifier(particle);
        return particle;
    }


    public void setSpriteBatch(SpriteBatch batch) {
        this.batch = batch;
    }
}
