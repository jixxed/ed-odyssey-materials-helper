/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.templates.components.segmentbar;

public enum SegmentType {
    RANK_COMPLETE, RANK_NOT_COMPLETE, RANK_NOT_DONE, RANK_DONE, PRESENT, PRESENT_SHIP, PRESENT_SQUADRON_CARRIER, PRESENT_FLEET_CARRIER, MISSING_FOR_MINIMUM, MISSING_FOR_REQUIRED, MISSING_FOR_MAXIMUM, NOT_PRESENT, POWER_GROUP_P, POWER_GROUP_1, POWER_GROUP_2, POWER_GROUP_3, POWER_GROUP_4, POWER_GROUP_5, POWER_GROUP_NONE, POWER_OVERPOWER, POWER_POTENTIAL_OVERPOWER, HARDPOINT_NONE, HARDPOINT_THERMAL, HARDPOINT_KINETIC, HARDPOINT_EXPLOSIVE, HARDPOINT_ABSOLUTE, HARDPOINT_CAUSTIC, HARDPOINT_ANTIXENO;


    public String getLocalizationKey() {
        return "segment.type." + this.name().toLowerCase();
    }
}