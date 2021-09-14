package io.github.alyrow.gdx.particle;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;

/**
 * @author alyrow
 */
public class ParticleView {
    public Camera camera;
    public Rectangle visibleRectangle;

    public ParticleView(Camera camera, Rectangle visibleRectangle) {
        this.camera = camera;
        this.visibleRectangle = visibleRectangle;
    }
}
