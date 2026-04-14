/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.enums;

public enum MineableLocation {
    ROCKY_CORE,
    ROCKY_SURFACE,
    ROCKY_SURFACE_DEPOSIT,
    ROCKY_SUB_SURFACE_DEPOSIT,
    METAL_RICH_CORE,
    METAL_RICH_SURFACE,
    METAL_RICH_SURFACE_DEPOSIT,
    METAL_RICH_SUB_SURFACE_DEPOSIT,
    METALLIC_CORE,
    METALLIC_SURFACE,
    METALLIC_SURFACE_DEPOSIT,
    METALLIC_SUB_SURFACE_DEPOSIT,
    ICY_CORE,
    ICY_SURFACE,
    ICY_SURFACE_DEPOSIT,
    ICY_SUB_SURFACE_DEPOSIT;

    public String getLocalizationKey() {
        return "material.mineable.location." + this.name().toLowerCase();
    }

}
