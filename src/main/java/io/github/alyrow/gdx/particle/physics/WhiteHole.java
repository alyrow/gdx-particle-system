package io.github.alyrow.gdx.particle.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Minecraftian14
 */
public class WhiteHole extends PhysicForce {

    private float effect;
    private Vector2 center;
    private final Vector2 cache = new Vector2();
    private float rs;

    public WhiteHole() {
    }

    public WhiteHole(float x, float y, float mass, float G) {
        center = new Vector2(x, y);
        effect = -G * mass;
    }
    public WhiteHole(float x, float y, float mass) {
        this(x, y, mass, 40000);
    }

    @Override
    public Vector2 getForce(PhysicParticle particle) {
        cache.set(center.x - particle.x, center.y - particle.y);
        rs = cache.len2();
        return cache.nor().scl(effect * particle.mass / rs);
    }
/*
    @Override
    public void write(Json json) {
        json.writeValue("center_x", center.x);
        json.writeValue("center_y", center.y);
        json.writeValue("effect", effect);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
    }*/
}
