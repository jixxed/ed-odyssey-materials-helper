/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;
import nl.jixxed.eliteodysseymaterials.enums.Operation;

/**
 * Event indicating a backpack change message was received in the journal
 */
@AllArgsConstructor
@Builder
@Getter
public class BackpackChangeEvent implements Event {
    private final String timestamp;
    private final OdysseyMaterial odysseyMaterial;
    private final Integer amount;
    private final Operation operation;
    private final String commander;
    private final String system;
    private final String primaryEconomy;
    private final String secondaryEconomy;
    private final String government;
    private final String security;
    private final String state;
    private final Double x;
    private final Double y;
    private final Double z;
    private final Double latitude;
    private final Double longitude;
    private final String body;
    private final String settlement;
}
