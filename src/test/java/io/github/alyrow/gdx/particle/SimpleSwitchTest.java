package io.github.alyrow.gdx.particle;

import com.badlogic.gdx.Input;
import io.github.alyrow.gdx.particle.physics.BrownianForce;
import io.github.alyrow.gdx.particle.physics.PathFollow;
import io.github.alyrow.gdx.particle.physics.powerups.SwitchableForce;
import io.github.alyrow.gdx.particle.tester.Tester;
import io.github.alyrow.gdx.particle.utils.switches.SimpleSwitch;

/**
 * @author Minecraftian14
 */
public class SimpleSwitchTest {
    public static void main(String[] args) {

        SimpleSwitch swt = new SimpleSwitch(2);

        new Tester()
                .forForce(() -> new SwitchableForce(
                        swt,
                        new BrownianForce(50, 1, 0.1),
                        new PathFollow("M93.9,46.4c9.3,9.5,13.8,17.9,23.5,17.9s17.5-7.8,17.5-17.5s-7.8-17.6-17.5-17.5c-9.7,0.1-13.3,7.2-22.1,17.1 c-8.9,8.8-15.7,17.9-25.4,17.9s-17.5-7.8-17.5-17.5s7.8-17.5,17.5-17.5S86.2,38.6,93.9,55.4z", 1f)
                ))
                .setPc(1)
                .setPosition(300, 400)
                .clearScreen(false)
                .addKey(Input.Keys.SPACE, swt::stepUp)
                .Test();
    }
}
