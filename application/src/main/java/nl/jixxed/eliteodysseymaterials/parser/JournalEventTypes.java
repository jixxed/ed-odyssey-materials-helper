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

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Event;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JournalEventTypes {
    public static final Map<String, Class<? extends Event>> EVENT_TYPES = loadEventTypes();

    private static Map<String, Class<? extends Event>> loadEventTypes() {
        Map<String, Class<? extends Event>> eventTypes = new ConcurrentHashMap<>();
        
        try {
            // Use ClassGraph to dynamically discover all classes that extend Event
            // in the journal package
            try (ScanResult scanResult = new ClassGraph()
                    .acceptPackages("nl.jixxed.eliteodysseymaterials.schemas.journal")
                    .enableClassInfo()
                    .scan()) {
                
                // Find all classes that extend Event
                for (ClassInfo classInfo : scanResult.getSubclasses(Event.class.getName())) {
                    try {
                        Class<?> clazz = classInfo.loadClass();
                        if (Event.class.isAssignableFrom(clazz)) {
                            @SuppressWarnings("unchecked")
                            Class<? extends Event> eventClass = (Class<? extends Event>) clazz;
                            
                            // Extract the event name from the class name
                            // The class name should be something like "AfmuRepairs" 
                            // and we want to map it to "AfmuRepairs"
                            String className = clazz.getSimpleName();
                            eventTypes.put(className, eventClass);
                        }
                    } catch (Exception e) {
                        // Skip classes that can't be loaded
                        System.err.println("Warning: Could not load class " + classInfo.getName() + ": " + e.getMessage());
                    }
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to load journal event types dynamically", e);
        }
        
        return Collections.unmodifiableMap(eventTypes);
    }
}