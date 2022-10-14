package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.function.Supplier;

@AllArgsConstructor
@Getter
public class SaveInventoryEvent implements Event {
    private final Supplier<String> textSupplier;
    private final Supplier<String> csvSupplier;
    private final Supplier<XSSFWorkbook> xlsSupplier;
}
