package nl.jixxed.eliteodysseymaterials.service.ar;

import com.sun.jna.Structure;

import java.util.ArrayList;
import java.util.List;

public class RECT extends Structure {
    public int left, top, right, bottom;

    @Override
    protected List<String> getFieldOrder() {
        final List<String> order = new ArrayList<>();
        order.add("left");
        order.add("top");
        order.add("right");
        order.add("bottom");
        return order;
    }
}
