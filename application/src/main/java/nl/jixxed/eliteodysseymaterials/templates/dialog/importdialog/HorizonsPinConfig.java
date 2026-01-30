package nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsBlueprintJson;
import nl.jixxed.eliteodysseymaterials.enums.Engineer;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class HorizonsPinConfig extends DestroyableVBox implements DestroyableTemplate {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    private String json;

    public HorizonsPinConfig(String json) {
        this.json = json;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("pin-config");
        try {
            final TypeReference<HashMap<Engineer, HorizonsBlueprintJson>> typeRef = new TypeReference<HashMap<Engineer, HorizonsBlueprintJson>>() {
            };

            final HashMap<Engineer, HorizonsBlueprintJson> pinnedBlueprintsMap = OBJECT_MAPPER.readValue(json, typeRef);
            List<PinEntry> list = Arrays.stream(Engineer.values())
                    .filter(Engineer::isHorizons)
                    .sorted(Comparator.comparing(Engineer::name))
                    .map(engineer -> new PinEntry(engineer, pinnedBlueprintsMap.get(engineer)))
                    .toList();
            this.getNodes().addAll(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
