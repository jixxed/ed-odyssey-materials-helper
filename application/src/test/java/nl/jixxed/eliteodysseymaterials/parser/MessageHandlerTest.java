/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.parser;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.jixxed.eliteodysseymaterials.schemas.capi.fleetcarrier.CapiFleetcarrier;
import nl.jixxed.eliteodysseymaterials.schemas.capi.squadron.BankItem;
import nl.jixxed.eliteodysseymaterials.schemas.capi.squadron.CapiSquadron;
import nl.jixxed.eliteodysseymaterials.schemas.capi.squadron.ShipItem;
import nl.jixxed.eliteodysseymaterials.service.ShipsDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.InputStream;

public class MessageHandlerTest {
    private static final ObjectMapper OBJECT_MAPPER1 = new ObjectMapper();
    private static final ObjectMapper OBJECT_MAPPER2 = new ObjectMapper();

    static {
        OBJECT_MAPPER1.registerModule(new JavaTimeModule());
        OBJECT_MAPPER1.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        SimpleModule shipsModule = new SimpleModule();
        shipsModule.setDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public JsonDeserializer<?> modifyMapDeserializer(
                    DeserializationConfig config,
                    MapType type,
                    BeanDescription beanDesc,
                    JsonDeserializer<?> deserializer
            ) {
                if (type.getKeyType().getRawClass() == String.class
                        && type.getContentType().getRawClass() == ShipItem[].class) {
                    return new ShipsDeserializer();
                }
                return deserializer;
            }
        });
        OBJECT_MAPPER1.registerModule(shipsModule);


        OBJECT_MAPPER2.registerModule(new JavaTimeModule());
        OBJECT_MAPPER2.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test1.json", "test2.json", "test3.json", "test4.json", "test5.json", "test6.json", "test7.json", "test8.json"})
    public void canMapFCFile(String file) {
        final InputStream resourceAsStream = MessageHandlerTest.class.getResourceAsStream("/parser/capifc/" + file);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Resource not found: " + file);
        }
        try (resourceAsStream) {
            final String json = new String(resourceAsStream.readAllBytes());
            OBJECT_MAPPER1.readValue(json, CapiFleetcarrier.class);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
    @ParameterizedTest
    @ValueSource(strings = {"squadron.json"})
    public void canMapSquadronFile(String file) {
        final InputStream resourceAsStream = MessageHandlerTest.class.getResourceAsStream("/parser/capisquadron/" + file);
        if (resourceAsStream == null) {
            throw new IllegalArgumentException("Resource not found: " + file);
        }
        try (resourceAsStream) {
            final String json = new String(resourceAsStream.readAllBytes());
            CapiSquadron capiSquadron = OBJECT_MAPPER1.readValue(json, CapiSquadron.class);
            Assertions.assertNotNull(capiSquadron);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}
