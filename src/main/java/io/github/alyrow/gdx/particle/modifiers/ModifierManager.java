package io.github.alyrow.gdx.particle.modifiers;

import com.badlogic.gdx.utils.Array;

/**
 * @author alyrow
 */
public class ModifierManager {

    public Array<Modifier> modifiers = new Array<Modifier>();

    public ModifierManager() {
    }

    public void addModifier(Modifier... modifiers) {
        this.modifiers.addAll(modifiers);
    }

    public void removeModifier(Modifier modifier) {
        modifiers.removeValue(modifier, true);
    }

}
