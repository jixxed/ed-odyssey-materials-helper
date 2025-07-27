package nl.jixxed.bootstrap.log;
import ch.qos.logback.core.rolling.TriggeringPolicyBase;

import java.io.File;

public class RollOncePerSessionTriggeringPolicy<E> extends TriggeringPolicyBase<E> {
    private static boolean doRolling = true;

    @Override
    public boolean isTriggeringEvent(File activeFile, E event) {
        // roll the first time when the event gets called
        if (doRolling) {
            doRolling = false;
            return true;
        }
        return false;
    }
}