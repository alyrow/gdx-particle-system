package io.github.alyrow.gdx.particle;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.alyrow.gdx.particle.physics.PhysicForce;
import io.github.alyrow.gdx.particle.physics.PhysicParticle;
import io.github.alyrow.gdx.particle.texture.AnimatedTexture;

/**
 * @author alyrow
 * This class represents a particle
 * Well, you will never use it because particles systems do it for you :)
 * @see ParticleSystem
 */
public class Particle {
    public static float ratio = 1;
    public float life; //in seconds
    public boolean outer; //If false --> Survive `life` seconds then die
    public Texture texture;
    public Sprite sprite;
    public boolean isInnerScreen;
    public Color color;
    public PhysicParticle physicParticle;
    private AnimatedTexture anTex;
    private ParticleSystem system;
    private float x;
    private float y;
    private long timeEllapsed;
    private ParticleType type;

    public Particle(float life, boolean outer, Texture texture, ParticleView view, ParticleSystem system, float x, float y, PhysicParticle physicParticle, Color color, ParticleType type) {
        if (!(texture instanceof AnimatedTexture))
            sprite = new Sprite(texture);
        init(life, outer, texture, view, system, x, y, physicParticle, color, type);
    }

    public void init(float life, boolean outer, Texture texture, ParticleView view, ParticleSystem system, float x, float y, PhysicParticle physicParticle, Color color, ParticleType type) {
        this.life = life * 1000; //s to ms
        this.outer = outer;
        this.texture = texture;
        this.system = system;
        this.x = x;
        this.y = y;
        this.physicParticle = physicParticle;
        this.physicParticle.setThisNoSenseSetterForParticleThisIsALittleReferenceToTheOrganisationOfThisLibDOnTTakeNotePlease(this);
        this.color = color;
        this.type = type;

        if (texture instanceof AnimatedTexture) {
            //Gdx.app.log("AnimatedTexture", "true");
            anTex = (AnimatedTexture) texture;
            sprite = new Sprite(anTex.textures[0]);
        }
        if (type.type == ParticleType.Type.HALO)
            sprite.setColor(color);
        sprite.setPosition(x, y);
        sprite.setSize(sprite.getWidth() * ratio, sprite.getHeight() * ratio);
        physicParticle.setSize(sprite.getWidth(), sprite.getHeight());

        isInnerScreen = _calculateIfInnerScreen(view);
        timeEllapsed = TimeUtils.millis();
    }

    public void render(SpriteBatch batch, ParticleView view, Array<PhysicForce> forces) {
        if (life <= 0) return;

        if (anTex != null) {
            anTex.render(Gdx.graphics.getDeltaTime());
            sprite.setTexture(anTex.textures[anTex.frame]);
        }

        float delta = Gdx.graphics.getDeltaTime() * 1000;
        isInnerScreen = _calculateIfInnerScreen(view);
        if (outer && !isInnerScreen) life = life - delta;
        else if (!outer) life = life - delta;
        //Gdx.app.log("inner", String.valueOf(isInnerScreen));

        //Put all physics here
//        Array<Vector2> vectors = new Array<>();
//        forces.forEach(force -> vectors.add(force.getForce(physicParticle)));
//        float vx = 0, vy = 0;
//        for (int i = 0; i < vectors.size; i++) {
//            vx += vectors.get(i).x;
//            vy += vectors.get(i).y;
//        }
        Vector2 netForce = new Vector2(0, 0);
        forces.forEach(force -> netForce.add(force.getForce(physicParticle)));
        netForce.scl(1 / physicParticle.mass); // now it's not force, it's acceleration


        physicParticle.x += netForce.x / (delta + 1);
        physicParticle.y += netForce.y / (delta + 1);

        x = physicParticle.x;
        y = physicParticle.y;
        //Gdx.app.log("position", x+", "+y);
        sprite.setPosition(physicParticle.x, physicParticle.y);

        if (type.type == ParticleType.Type.HALO && !sprite.getColor().equals(color)) {
            sprite.setColor(color);
        }


        if (life < 500) {
            sprite.setAlpha(life / 500);
        }
        if (life <= 0) {
            dispose();
        } else sprite.draw(batch);
    }

    public boolean _calculateIfInnerScreen(ParticleView view) {
        float w = view.visibleRectangle.width;//batch.getProjectionMatrix().getScaleX();
        float h = view.visibleRectangle.height;//batch.getProjectionMatrix().getScaleY();
        float x = view.visibleRectangle.x;//camera.position.x;//batch.getProjectionMatrix().getTranslation(new Vector3()).x;
        float y = view.visibleRectangle.y;//camera.position.y;//batch.getProjectionMatrix().getTranslation(new Vector3()).y;
        //Gdx.app.log("w, h, x, y", w +", "+h+", "+", "+x+", "+y);

        return this.x + this.texture.getWidth() / 2f >= x && this.x + this.texture.getWidth() / 2f < x + w && this.y + this.texture.getHeight() / 2f >= y && this.y + this.texture.getHeight() / 2f < y + h;
    }

    public void dispose() {
        system.particles.removeValue(this, true);
    }
}
