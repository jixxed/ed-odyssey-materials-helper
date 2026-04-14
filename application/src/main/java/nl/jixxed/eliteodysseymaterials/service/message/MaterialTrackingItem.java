/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.message;

import lombok.Builder;
import lombok.Data;
import nl.jixxed.eliteodysseymaterials.enums.OdysseyMaterial;

@Data
@Builder
public class MaterialTrackingItem {
    private String commander;
    private String timestamp;
    private OdysseyMaterial odysseyMaterial;
    private Integer amount;
    private String system;
    private String primaryEconomy;
    private String secondaryEconomy;
    private String government;
    private String security;
    private String state;
    private Double x;
    private Double y;
    private Double z;
    private Double latitude;
    private Double longitude;
    private String body;
    private String settlement;
    private String session;
    private String version;
}
