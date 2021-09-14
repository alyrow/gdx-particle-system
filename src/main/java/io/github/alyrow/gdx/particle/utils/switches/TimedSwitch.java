package io.github.alyrow.gdx.particle.utils.switches;

import com.badlogic.gdx.Gdx;

/**
 * @author Minecraftian14
 */
public class TimedSwitch extends SwitchAdaptor {

    private final float timeMilliSec;
    private final int statesCount;
    private float lastTime = 0;
    private int ind = 0;

    public TimedSwitch(float timeMilliSec, int statesCount) {
        this.timeMilliSec = timeMilliSec;
        this.statesCount = statesCount;
    }

    @Override
    public int getState() {
        lastTime += Gdx.graphics.getDeltaTime();
        if (lastTime > timeMilliSec) {
            lastTime = 0;
            ind++;
        }
        return ind % statesCount;
    }

    @Override
    public int getStatesCount() {
        return statesCount;
    }

}
