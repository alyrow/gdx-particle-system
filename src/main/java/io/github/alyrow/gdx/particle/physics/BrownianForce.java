package io.github.alyrow.gdx.particle.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * @author alyrow
 * Brownian force
 */
public class BrownianForce extends PhysicForce {
    /**
     * Raise if you want to make it smoother
     */
    public static final int octaves = 4; // change to 5 or higher to make it smoother and have smaller extremes
    public float strength_x, strength_y;
    public long seed;
    public double frequency;
    private final Vector2 cache = new Vector2();

    public BrownianForce() {
    }

    /**
     * Brownian force with equals strength on x and y axis
     *
     * @param strength  Strength of the force
     * @param seed      Seed of the force
     * @param frequency Lower means more quiet
     */
    public BrownianForce(float strength, long seed, double frequency) {
        this.strength_x = strength;
        this.strength_y = strength;
        this.seed = seed;
        this.frequency = frequency;
    }

    /**
     * @param strength_x Strength of the force for x axis
     * @param strength_y Strength of the force for y axis
     * @param seed       Seed of the force
     * @param frequency  Lower means more quiet
     */
    public BrownianForce(float strength_x, float strength_y, long seed, double frequency) {
        this.strength_x = strength_x;
        this.strength_y = strength_y;
        this.seed = seed;
        this.frequency = frequency;
    }

    @Override
    public Vector2 getForce(PhysicParticle particle) {
        float dx, dy;
        //if (Math.random()<0.5) dx = -strength_x;
        //else dx = strength_x;
        //dx = (float) ((Math.random()*2-1)*strength_x);
        //if (Math.random()<0.5) dy = -strength_y;
        //else dy = strength_y;
        //dy = (float) ((Math.random()*2-1)*strength_y);

        // TODO Call a init function for each particle to call these two lines one time
        particle.data_float.computeIfAbsent("seedy", k -> (float) (Math.random() * seed * 100));
        particle.data_float.computeIfAbsent("seedx", k -> (float) (Math.random() * seed * 100));


        dx = (float) layeredNoise(particle.y + strength_y, Math.round(particle.data_float.get("seedx")), frequency) * strength_x;
        dy = (float) layeredNoise(particle.x + strength_x, Math.round(particle.data_float.get("seedy")), frequency) * strength_y;
        //Gdx.app.log("y", String.valueOf(dy));

        return cache.set(dx, dy);
    }

    /**
     * @param x         the time component or whatever is changing in 1D
     * @param seed      a long that should be different for different sequences of noise; when this changes, it won't smoothly transition
     * @param frequency usually between 0.01 and 0.1; 0.03 can be a good default, but some usage will need much larger or smaller frequencies
     * @author TEttinger
     */
    public double layeredNoise(double x, long seed, double frequency) {
        x *= frequency;
        double n = 0.0, i_s = 2.0;
        for (int o = 0; o < octaves; o++) {
            n += noise(x * (i_s *= 0.5), /*(seed += 0x9E3779B97F4A7C15L)*/seed) * (1 << o);
        }
        return n / ((1 << octaves) - 1.0);
    }

    public double noise(double x, final long seed) {
        x += ((seed & 0xFFFFFFFFL) ^ (seed >>> 32)) * 0x1p-24; // offset x by between 0.0 and almost 256.0
        final long xFloor = x >= 0 ? (long) x : (long) x - 1L, // floor of x as a long
                rise = 1L - ((x >= 0 ? (long) (x * 2) : (long) (x * 2) - 1L) & 2L); // either 1 or -1
        x -= xFloor;
        // and now we flip the switch from "magic" to "more magic..."
        // this directly sets the bits that describe a double. this might seem like it should be slow; it is not.
        // seed and xFloor are XORed to roughly mix them together; adding would work too probably.
        // the two huge longs don't really matter except for their last digits:
        // the one that uses ^ must end in 5 or D (both hex)
        // the one that uses * must end in 3 or B (both hex)
        // and that makes this a valid "XLCG," a cousin of the commonly used LCG random number generator.
        // it improves the randomness in the upper bits more than the lower ones, where the upper bits will become
        // more significant decimal places in the resulting double.
        // we unsigned-right-shift by 12, which puts the random bits all in the double's mantissa (which is how far
        // it is between the previous power of two and next power of two, roughly).
        // we bitwise OR with the exponent section for a double between 16.0 and 32.0.
        // we work our magic and convert the bits to double.
        // if you target GWT you should use libGDX's NumberUtils.longBitsToDouble() instead of Double's version.
        // subtracting 24.0 takes the range to -8.0 to 8.0, where we want it.
        final double h = Double.longBitsToDouble((seed ^ xFloor ^ 0x9E3779B97F4A7C15L) * 0xD1B54A32D192ED03L >>> 12 | 0x4030000000000000L) - 24.0;
        // Quilez' quartic curve; uses the "rise" calculated earlier to determine if this is on the rising or
        // the falling side. Uses that complicated "h" as the height of the peak or valley in the middle.
        return rise * x * (x - 1) * (h * x * (x - 1) - 1);
    }
}
