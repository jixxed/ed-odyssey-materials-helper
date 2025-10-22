package nl.jixxed.eliteodysseymaterials.service.ar;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.geom.Rectangle2D;

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

    public java.awt.Rectangle.Double getAwtRectangle() {
        return new Rectangle2D.Double(this.x,  this.y, getWidth(), getHeight());
    }
}
