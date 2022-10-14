package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.function.Supplier;

@AllArgsConstructor
@Getter
public class SaveWishlistEvent implements Event {
    //    private final String text;
    private final Supplier<String> textSupplier;
    private final Supplier<String> csvSupplier;
    private final Supplier<XSSFWorkbook> xlsSupplier;
}
