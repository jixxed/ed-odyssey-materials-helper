package nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ClipboardLoadout;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

@Slf4j
public class OdysseyLoadout extends DestroyableVBox implements DestroyableTemplate {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    private String json;

    public OdysseyLoadout(String json) {
        this.json = json;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("loadout");
        try {
            final ClipboardLoadout clipboardLoadout = OBJECT_MAPPER.readValue(json, ClipboardLoadout.class);

            var list = clipboardLoadout.getLoadoutSet().getLoadouts().stream().map(LoadoutItem::new).toList();
            DestroyableFlowPane flowPane = FlowPaneBuilder.builder().withStyleClass("list").withNodes(list).build();
            this.getNodes().add(flowPane);
            HBox.setHgrow(flowPane, Priority.ALWAYS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
