package io.github.alyrow.gdx.particle.physics;

import io.github.alyrow.gdx.particle.utils.Line;

/**
 * @author Minecraftian14
 */
public class WhiteLine extends BlackLine {

    public WhiteLine(Line line, float mass, float G) {
        super(line, -mass, G);
    }

    public WhiteLine(Line line, float mass) {
        super(line, -mass);
    }

}
