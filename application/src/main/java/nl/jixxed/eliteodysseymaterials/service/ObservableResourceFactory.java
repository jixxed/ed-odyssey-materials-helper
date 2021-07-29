package nl.jixxed.eliteodysseymaterials.service;

import javafx.beans.binding.ListBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ResourceBundle;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObservableResourceFactory {

    private static final ObjectProperty<ResourceBundle> RESOURCES = new SimpleObjectProperty<>();

    private static ObjectProperty<ResourceBundle> resourcesProperty() {
        return RESOURCES;
    }

    static ResourceBundle getResources() {
        return resourcesProperty().get();
    }

    static void setResources(final ResourceBundle resources) {
        resourcesProperty().set(resources);
    }

    protected static StringBinding getStringBinding(final Supplier<String> stringSupplier) {
        return new StringBinding() {
            {
                bind(resourcesProperty());
            }

            @Override
            public String computeValue() {
                return stringSupplier.get();
            }
        };
    }

    @SafeVarargs
    static <T> ListBinding<T> getListBinding(final T... items) {
        return new ListBinding<>() {

            {
                bind(resourcesProperty());
            }

            @Override
            protected ObservableList<T> computeValue() {
                return FXCollections.observableArrayList(
                        items
                );
            }
        };
    }
}