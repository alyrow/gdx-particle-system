package io.github.alyrow.gdx.particle.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.piro.bezier.BezierPath;

import java.util.UUID;

/**
 * @author alyrow
 */
public class PathFollow extends PhysicForce {

    private final String uid = UUID.randomUUID().toString();
    BezierPath path;
    float strength;

    public PathFollow() {
    }

    public PathFollow(String str, float strength) {
        path = new BezierPath();
        path.parsePathString(str);
        this.strength = strength / 1000f;
    }

    @Override
    public Vector2 getForce(PhysicParticle particle) {
        particle.data_float.putIfAbsent("time_" + uid, 0f);
        float time = particle.data_float.get("time_" + uid);
        particle.data_long.putIfAbsent("timesince_" + uid, TimeUtils.millis());
        long millis = particle.data_long.get("timesince_" + uid);
        if (time >= 1) return Vector2.Zero;
//        System.out.println(time);
        Vector2 point1 = path.eval(time);
        Vector2 point2 = path.eval(time + 0.0000001f);
        if (time + 0.0000001f > 1) {
            Vector2 p = new Vector2(point1);
            point1 = new Vector2(point2);
            point2 = new Vector2(p);
        }

        particle.data_float.put("time_" + uid, time += strength - strength * TimeUtils.timeSinceMillis(millis) / 1000f);
        particle.data_long.put("timesince_" + uid, TimeUtils.millis());
        Vector2 vector2 = new Vector2().add(point2).sub(point1);
        vector2.y = -vector2.y;

        return vector2.scl(1000000);
    }
}
