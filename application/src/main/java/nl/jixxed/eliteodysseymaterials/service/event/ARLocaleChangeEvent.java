package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.enums.ApplicationLocale;

@RequiredArgsConstructor
@Getter
public class ARLocaleChangeEvent implements Event {
    private final ApplicationLocale locale;
}
