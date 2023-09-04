package nl.jixxed.eliteodysseymaterials.domain.ships;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
public class ImageSlot extends Slot{
    @Getter
    private  int imageIndex;
    @Getter
    private  int x;
    @Getter
    private  int y;

    public ImageSlot(ImageSlot slot) {
        super(slot);
        imageIndex = slot.getImageIndex();
        x = slot.getX();
        y = slot.getY();
    }
}
