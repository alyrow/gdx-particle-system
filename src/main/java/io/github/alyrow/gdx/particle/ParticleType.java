package io.github.alyrow.gdx.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import io.github.alyrow.gdx.particle.texture.ParticleTexture;

/**
 * @author alyrow
 */
public class ParticleType implements Json.Serializable {

    public Type type;
    public ParticleTexture particleTexture;

    public ParticleType() {
        this(Type.HALO, Gdx.files.internal("halo.png"));
    }

    public ParticleType(Type particleType) {
        this(particleType, Gdx.files.internal("halo.png"));
    }

    public ParticleType(Type particleType, FileHandle path) {
        type = particleType;
        particleTexture = new ParticleTexture(path);
    }

    @Override
    public void write(Json json) {
        json.writeValue("particle_type", type, Type.class);
        json.writeValue("texture", particleTexture, ParticleTexture.class);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        //System.out.println("TYPE JSON = \n" + jsonData.get("particle_type").toString().substring(15));
        //type = json.fromJson(Type.class, stringBuilder.toString());
        type = json.fromJson(Type.class, jsonData.get("particle_type").toString().substring(15));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (int i = 0; i < jsonData.get("texture").size; i++) {
            stringBuilder.append(jsonData.get("texture").get(i));
            stringBuilder.append("\n");
        }
        stringBuilder.append("}");
        //System.out.println("TEXTURE JSON = \n" + stringBuilder.toString());
        particleTexture = json.fromJson(ParticleTexture.class, stringBuilder.toString());
    }

    public enum Type {HALO, TEXTURE, NOTHING}

}
