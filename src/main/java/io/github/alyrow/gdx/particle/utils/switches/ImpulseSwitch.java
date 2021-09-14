package io.github.alyrow.gdx.particle.utils.switches;

/**
 * @author Minecraftian14
 */
public class ImpulseSwitch extends SwitchAdaptor {

    boolean update = false;

    public void activate() {
        update = true;
    }

    @Override
    public int getState() {
        if (update) {
            update = false;
            return 1;
        }
        return 0;
    }

    @Override
    public int getStatesCount() {
        return 2;
    }


}
