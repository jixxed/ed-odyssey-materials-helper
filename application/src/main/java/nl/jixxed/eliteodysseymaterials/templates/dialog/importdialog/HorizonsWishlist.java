/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.dialog.importdialog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.builder.FlowPaneBuilder;
import nl.jixxed.eliteodysseymaterials.domain.ClipboardHorizonsWishlist;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableFlowPane;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableTemplate;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableVBox;

@Slf4j
public class HorizonsWishlist extends DestroyableVBox implements DestroyableTemplate {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    private String json;

    public HorizonsWishlist(String json) {
        this.json = json;
        initComponents();
    }

    @Override
    public void initComponents() {
        this.getStyleClass().add("wishlist");
        try {
            final ClipboardHorizonsWishlist clipboardWishlist = OBJECT_MAPPER.readValue(json, ClipboardHorizonsWishlist.class);

            var list = clipboardWishlist.getWishlist().getItems().stream().map(Blueprint::new).toList();
            DestroyableFlowPane flowPane = FlowPaneBuilder.builder().withStyleClass("list").withNodes(list).build();
            this.getNodes().add(flowPane);
            HBox.setHgrow(flowPane, Priority.ALWAYS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
