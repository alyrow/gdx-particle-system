package io.github.alyrow.gdx.particle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.alyrow.gdx.particle.modifiers.GradientColors;
import io.github.alyrow.gdx.particle.physics.LinearForce;
import io.github.alyrow.gdx.particle.physics.PathFollow;
import io.github.alyrow.gdx.particle.physics.PhysicManager;
import io.github.alyrow.gdx.particle.rules.ParticleEmissionDuration;
import io.github.alyrow.gdx.particle.rules.ParticleEmissionNumber;
import io.github.alyrow.gdx.particle.rules.ParticleLife;

/**
 * @author alyrow
 */
public class JSONtest extends Game {

    PhysicManager physicManager;
    SpriteBatch batch;
    boolean g = true;
    private ParticleSystem system;
    private OrthographicCamera camera;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("NebulaDemo");
        configuration.setWindowedMode(1024, 768);
        new Lwjgl3Application(new JSONtest(), configuration);
    }

    @Override
    public void create() {

        ParticleRules rules = new ParticleRules();
        ParticleEmissionNumber ps = new ParticleEmissionNumber(ParticleEmissionNumber.INNER_SCREEN, 8000);
        ps.setDelay(0.05f);
        rules.setNumber(ps); //One particle emitted per seconds
        rules.setLife(new ParticleLife(30, true)); //Particles life : 5s. Life will decrease when particles are outside the screen.
        rules.setDuration(new ParticleEmissionDuration(true)); //Infinite duration for the emission

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Rectangle rectangle = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ParticleView view = new ParticleView(camera, rectangle);
        camera.position.set(camera.viewportWidth / 2.0f, camera.viewportHeight / 2.0f, 1.0f);
        camera.update();
        ParticleType type = new ParticleType();
        batch = new SpriteBatch();
        system = new ParticleSystem(type, view, batch);
        system.setRules(rules);
        system.setParticlesPosition(-50, 600);
//        system.disableBlending();

        try {
            system.getModifierManager().addModifier(
                    //                new RandomPositionRectangle(camera.viewportWidth/2, camera.viewportHeight)
                    //                new RandomColors()
                    new GradientColors(new Color(0.01f, 0.00391666666f, 0.001125f, 0.1f), new Color(0.94117647058f, 0.36862745098f, 0.10588235294f, 0.8f))
                    //                new RandomMassModifier(10,30),
                    //                new RandomChargeModifier(),
//                                    new MassProportionalLightRadius(2f)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        physicManager = new PhysicManager();

        //physicManager.addForce(new BlackHole(3, 5, 25));
        //physicManager.addForce(new BlackLine(Line.fromTwoPoints(1,1,5,5), 5, 25));
//        physicManager.addForce(new BrownianForce(400, 2, 0.01));
        physicManager.addForce(new PathFollow("M10 80 C 40 10, 65 10, 95 80 S 150 150, 180 80", 0.000f));
//        physicManager.addForce(new Drain(500, 400, 30, 10));
        //physicManager.addForce(new ImageColour(Gdx.files.internal("test2.png")));
//        physicManager.addForce(new Fan(50, 50, 6));
        physicManager.addForce(new LinearForce(0, 5));
//        physicManager.addForce(new PointAttract(41, 65, 1));
//        physicManager.addForce(new Revolution(15, 65, 2));
        //physicManager.addForce(new WhiteHole(600, 600, 0.001f));

        system.setPhysicManager(physicManager);

        String str1 = new Jsonify().systemToJSON(system);
        System.out.println(str1);
        Gdx.files.local("original.txt").writeString(str1, false);
//        System.out.println(system.type.type);
        system = new Jsonify().JSONtoSystem(str1);
        system.setSpriteBatch(batch);
        String str2 = new Jsonify().systemToJSON(system);
        System.out.println("\n");
        System.out.println(str2);
        Gdx.files.local("converted.txt").writeString(str2, false);

        System.out.println(str1.compareTo(str2));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        //camera.update();
        //system.setParticlesPosition(Math.round(Math.random() * Gdx.graphics.getWidth()), Math.round(Math.random() * Gdx.graphics.getHeight())); //Random position
        batch.begin();
        system.render();
        batch.end();
/*
        if(g) {
            physicManager.addForce(new Fan(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 20));
            g = false;
        }

        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();
         */
    }
}
