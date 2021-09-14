package io.github.alyrow.gdx.particle.texture;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/**
 * @author alyrow
 */
public class ParticleTexture implements Json.Serializable {

    /**
     * Libgdx texture
     *
     * @see Texture
     */

    public boolean random = false;

    public boolean animated = false;

    public int fps = 0;

    public Array<Array<FileHandle>> paths2d = new Array<>();

    Array<Array<Texture>> textures2d = new Array<>();


    /**
     * Create particule texture
     *
     * @param path Path of the texture
     */
    public ParticleTexture(FileHandle path) {
        Array<FileHandle> paths = new Array<>();
        paths.add(path);
        paths2d.add(paths);
        Array<Texture> textures = new Array<>();
        textures.add(new Texture(path));
        textures2d.add(textures);
    }

    public ParticleTexture(Array<FileHandle> paths, boolean thisVarIsForGoodCompilation) {
        this.random = true;
        paths.forEach(path -> {
            Array<FileHandle> p = new Array<>();
            p.add(path);
            paths2d.add(p);
            Array<Texture> textures = new Array<>();
            textures.add(new Texture(path));
            textures2d.add(textures);
        });
    }

    public ParticleTexture(Array<FileHandle> paths, int fps) {
        this.animated = true;
        this.fps = fps;
        paths2d.add(paths);
        Array<Texture> textures = new Array<>();
        paths.forEach((path) -> textures.add(new Texture(path)));
    }

    public ParticleTexture(Array<Array<FileHandle>> paths2d) {
        this.random = true;
        this.animated = true;
        this.paths2d = paths2d;
        paths2d.forEach((paths) -> {
            Array<Texture> textures = new Array<>();
            paths.forEach(path -> textures.add(new Texture(path)));
            textures2d.add(textures);
        });
    }

    public ParticleTexture() {
        /*Array<FileHandle> paths = new Array<>();
        paths.add(null);
        paths2d.add(paths);
        Pixmap t = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        Array<Texture> textures = new Array<>();
        textures.add(new Texture(t));
        textures2d.add(textures);*/
    }

    /**
     * Intern usage only
     *
     * @return Return the texture
     */
    public Texture getTexture() {
        if (!random && !animated) return textures2d.first().first();
        else if (random && !animated) return textures2d.random().first();
        else if (!random && animated) return new AnimatedTexture(textures2d.first().items, fps);
        else if (random && animated) return new AnimatedTexture(textures2d.random().items, fps);
        return new Texture(new Pixmap(1, 1, Pixmap.Format.RGBA8888));
    }

    public void dispose() {
        textures2d.forEach(textures -> textures.forEach(Texture::dispose));
    }


    @Override
    public void write(Json json) {

        json.writeValue("random", random);
        json.writeValue("animated", animated);
        json.writeValue("fps", fps);
        json.writeValue("texture", paths2d);


    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        random = jsonData.getBoolean("random");
        animated = jsonData.getBoolean("animated");
        fps = jsonData.getInt("fps");
        for (int i = 0; i < jsonData.get("texture").size; i++) {
            Array<FileHandle> paths = new Array<>();
            Array<Texture> textures = new Array<>();
            for (int j = 0; j < jsonData.get("texture").get(i).size; j++) {
                String file = jsonData.get("texture").get(i).get(j).get("file").get("path").toString().substring(6);
                Files.FileType type = json.fromJson(Files.FileType.class, jsonData.get("texture").get(i).get(j).get("type").toString().substring(6));
                FileHandle path = Gdx.files.getFileHandle(file, type);
                paths.add(path);
                textures.add(new Texture(path));
            }
            paths2d.add(paths);
            textures2d.add(textures);
        }

    }
}
