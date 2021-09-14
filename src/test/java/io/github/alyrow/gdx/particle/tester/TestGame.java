package io.github.alyrow.gdx.particle.tester;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.alyrow.gdx.particle.ParticleRules;
import io.github.alyrow.gdx.particle.ParticleSystem;
import io.github.alyrow.gdx.particle.ParticleType;
import io.github.alyrow.gdx.particle.ParticleView;
import io.github.alyrow.gdx.particle.modifiers.Modifier;
import io.github.alyrow.gdx.particle.modifiers.ModifierManager;
import io.github.alyrow.gdx.particle.physics.PhysicForce;
import io.github.alyrow.gdx.particle.physics.PhysicManager;
import io.github.alyrow.gdx.particle.rules.ParticleEmissionDuration;
import io.github.alyrow.gdx.particle.rules.ParticleEmissionNumber;
import io.github.alyrow.gdx.particle.rules.ParticleLife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Minecraftian14
 */
public class TestGame extends Game {
    private final boolean clearScreen;
    ArrayList<Function<TestGame, PhysicForce>> forces_wg;
    ArrayList<Function<TestGame, Modifier>> modifiers_wg;
    private ParticleSystem system;
    private OrthographicCamera camera;
    private PhysicManager physicManager;
    private SpriteBatch batch;
    private final ArrayList<Supplier<PhysicForce>> forces;
    private final ArrayList<Supplier<Modifier>> modifiers;
    private final HashMap<Integer, Runnable> inputKeys;
    private final int pc;
    private final ParticleType type;
    private final int emissionNumberMode;
    private final float emissionSecondsDelay;
    private long time;
    private final float x;
    private final float y;

    public TestGame(ArrayList<Supplier<PhysicForce>> forces, ArrayList<Supplier<Modifier>> modifiers, ArrayList<Function<TestGame, PhysicForce>> forces_wg, ArrayList<Function<TestGame, Modifier>> modifiers_wg, HashMap<Integer, Runnable> inputKeys, int pc, ParticleType type, int emissionNumberMode, float emissionSecondsDelay, boolean clearScreen, float x, float y) {
        this.forces = forces;
        this.modifiers = modifiers;
        this.forces_wg = forces_wg;
        this.modifiers_wg = modifiers_wg;
        this.inputKeys = inputKeys;
        this.pc = pc;
        //if (type == null) this.type = new ParticleType();
        this.type = type;
        this.emissionNumberMode = emissionNumberMode;
        this.emissionSecondsDelay = emissionSecondsDelay;
        this.clearScreen = clearScreen;
        this.x = x;
        this.y = y;
    }

    @Override
    public void create() {
        ParticleRules rules = new ParticleRules();
        ParticleEmissionNumber ps = new ParticleEmissionNumber(emissionNumberMode, pc);
        ps.setDelay(emissionSecondsDelay);
        rules.setNumber(ps); //One particle emitted per seconds
        rules.setLife(new ParticleLife(5, true)); //Particles life : 5s. Life will decrease when particles are outside the screen.
        rules.setDuration(new ParticleEmissionDuration(true)); //Infinite duration for the emission

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Rectangle rectangle = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ParticleView view = new ParticleView(camera, rectangle);
        camera.position.set(camera.viewportWidth / 2.0f, camera.viewportHeight / 2.0f, 1.0f);
        camera.update();
        batch = new SpriteBatch();

        system = new ParticleSystem(new ParticleType(), view, batch);
        system.setRules(rules);
        system.setParticlesPosition(x, y);

        ModifierManager manager = system.getModifierManager();
        modifiers.forEach(sup -> manager.addModifier(sup.get()));
        modifiers_wg.forEach(sup -> manager.addModifier(sup.apply(this)));

        physicManager = new PhysicManager();
        forces.forEach(sup -> physicManager.addForce(sup.get()));
        forces_wg.forEach(sup -> physicManager.addForce(sup.apply(this)));

        system.setPhysicManager(physicManager);

    }

    @Override
    public void render() {
        super.render();

        if (clearScreen) {
            Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }

        batch.begin();
        system.render();
        batch.end();

        inputKeys.forEach((key, act) -> {
            if (Gdx.input.isKeyJustPressed(key))
                act.run();
        });

        if (TimeUtils.timeSinceMillis(time) >= 1000) {
            Gdx.graphics.setTitle("Integrated Tests - " + Gdx.graphics.getFramesPerSecond() + " fps - " + system.particles.size + " particles");
            time = TimeUtils.millis();
        }
    }


    public ParticleSystem getSystem() {
        return system;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public PhysicManager getPhysicManager() {
        return physicManager;
    }
}
