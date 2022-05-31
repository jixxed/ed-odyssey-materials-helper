package nl.jixxed.eliteodysseymaterials.service.ar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Rectangle {
    private double x;
    private double y;
    private double maxX;
    private double maxY;

    public double getWidth() {
        return this.maxX - this.x;
    }

    public double getHeight() {
        return this.maxY - this.y;
    }

    public java.awt.Rectangle getAwtRectangle() {
        return new java.awt.Rectangle((int) this.x, (int) this.y, (int) getWidth(), (int) getHeight());
    }
}
