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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ImportResult {
    private final ResultType resultType;
    private LocaleService.LocaleString message = LocaleService.LocaleString.of("blank");

    public enum ResultType {
        SUCCESS_ODYSSEY_WISHLIST,
        ERROR_ODYSSEY_WISHLIST,
        SUCCESS_HORIZONS_WISHLIST,
        ERROR_HORIZONS_WISHLIST,
        SUCCESS_LOADOUT,
        ERROR_LOADOUT,
        SUCCESS_SLEF,
        ERROR_SLEF,
        SUCCESS_EDSY_WISHLIST,
        ERROR_EDSY_WISHLIST,
        SUCCESS_CORIOLIS_WISHLIST,
        ERROR_CORIOLIS_WISHLIST,
        UNKNOWN_TYPE,
        OTHER_ERROR,
        SUCCESS_HORIZONS_SHIP,
        ERROR_HORIZONS_SHIP,
        SUCCESS_PINCONFIG,
        ERROR_PINCONFIG,
        CAPI_OAUTH_TOKEN
    }
}
