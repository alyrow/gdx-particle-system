package io.github.alyrow.gdx.particle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import io.github.alyrow.gdx.particle.modifiers.ModifierManager;
import io.github.alyrow.gdx.particle.physics.PhysicManager;

/**
 * @author alyrow
 */
public class Jsonify implements Json.Serializable {

    ParticleSystem system;

    public String systemToJSON(ParticleSystem system) {
        Json json = new Json();
        this.system = system;
        return json.prettyPrint(this).replaceAll("com.badlogic.gdx.backends.lwjgl3.Lwjgl3FileHandle", "com.badlogic.gdx.files.FileHandle");
    }

    public ParticleSystem JSONtoSystem(String str) {
        Json json = new Json();
        Jsonify jsonify = json.fromJson(Jsonify.class, str);
        return jsonify.system;
    }


    @Override
    public void write(Json json) {
        json.writeValue("system.type", system.type, ParticleType.class);
        json.writeValue("rules", system.getRules(), ParticleRules.class);
        json.writeValue("physics", system.getPhysicManager(), PhysicManager.class);
        json.writeValue("modifiers", system.getModifierManager(), ModifierManager.class);
        json.writeObjectStart("position");
        json.writeValue("x", system.x);
        json.writeValue("y", system.y);
        json.writeObjectEnd();
        json.writeValue("version", system.version);
        json.writeValue("version_code", system.version_code);
        json.writeValue("min_version_code", system.min_version_code);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        //ParticleType type = json.fromJson(ParticleType.class, jsonData.getChild("system.type").asString());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        for (int i = 0; i < jsonData.get("system.type").size; i++) {
            stringBuilder.append(jsonData.get("system.type").get(i));
            stringBuilder.append("\n");
        }
        stringBuilder.append("}");

        //System.out.println(stringBuilder.toString()); //json.prettyPrint(jsonData.getChild("system.type"))
        //System.out.println("PARTICLE TYPE JSON = \n" + stringBuilder.toString());

        ParticleType type = json.fromJson(ParticleType.class, stringBuilder.toString());

        stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        for (int i = 0; i < jsonData.get("rules").size; i++) {
            stringBuilder.append(jsonData.get("rules").get(i));
            stringBuilder.append("\n");
        }
        stringBuilder.append("}");
        ParticleRules particleRules = json.fromJson(ParticleRules.class, stringBuilder.toString());

        stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        for (int i = 0; i < jsonData.get("physics").size; i++) {
            stringBuilder.append(jsonData.get("physics").get(i));
            stringBuilder.append("\n");
        }
        stringBuilder.append("}");
        PhysicManager physicManager = json.fromJson(PhysicManager.class, stringBuilder.toString());

        stringBuilder = new StringBuilder();
        stringBuilder.append("{\n");
        for (int i = 0; i < jsonData.get("modifiers").size; i++) {
            stringBuilder.append(jsonData.get("modifiers").get(i));
            stringBuilder.append("\n");
        }
        stringBuilder.append("}");
        ModifierManager modifierManager = json.fromJson(ModifierManager.class, stringBuilder.toString());

        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Rectangle rectangle = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ParticleView view = new ParticleView(camera, rectangle);
        camera.position.set(camera.viewportWidth / 2.0f, camera.viewportHeight / 2.0f, 1.0f);
        camera.update();

        system = new ParticleSystem(type, view, null);
        system.setRules(particleRules);
        system.setPhysicManager(physicManager);
        system.setModifierManager(modifierManager);

        system.setParticlesPosition(jsonData.get("position").getInt("x"), jsonData.get("position").getInt("y"));
    }
}
