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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.enums.JournalEventType;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.MessageProcessor;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.batch.CarrierJumpBatchMessageProcessor;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.batch.CommunityGoalBatchMessageProcessor;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.batch.FSDJumpBatchMessageProcessor;
import nl.jixxed.eliteodysseymaterials.parser.messageprocessor.batch.LocationBatchMessageProcessor;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Event;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BatchMessageHandler {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String EVENT = "event";

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    private static final Map<JournalEventType, MessageProcessor<? extends Event>> messageProcessors = Map.ofEntries(
            Map.entry(JournalEventType.COMMUNITYGOAL, new CommunityGoalBatchMessageProcessor()),
            Map.entry(JournalEventType.CARRIERJUMP, new CarrierJumpBatchMessageProcessor()),
            Map.entry(JournalEventType.FSDJUMP, new FSDJumpBatchMessageProcessor()),
            Map.entry(JournalEventType.LOCATION, new LocationBatchMessageProcessor())


    );

    @SuppressWarnings("unchecked")
    public static synchronized void handleMessages(final JournalEventType journalEventType, final List<String> messages, final File file) {
        final MessageProcessor<Event> messageProcessor = (MessageProcessor<Event>) messageProcessors.get(journalEventType);
        final Class<? extends Event> messageClass = messageProcessor.getMessageClass();
        List<Event> events = (List<Event>) messages.stream()
                .map(message -> {
                    try {
                        return OBJECT_MAPPER.readValue(message, messageClass);
                    } catch (final JsonProcessingException e) {
                        log.error("Error processing json message from: {}", file.getName(), e);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
        messageProcessor.process(events);
    }
}
