/*
 * Copyright (c) 2026 Jixxed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.jixxed.eliteodysseymaterials.service.ships;

import nl.jixxed.eliteodysseymaterials.domain.ShipConfiguration;
import nl.jixxed.eliteodysseymaterials.helper.ClipboardHelper;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Loadout.Loadout;
import nl.jixxed.eliteodysseymaterials.schemas.slef.Header;
import nl.jixxed.eliteodysseymaterials.schemas.slef.Slef;
import nl.jixxed.eliteodysseymaterials.service.LocationService;
import nl.jixxed.eliteodysseymaterials.service.VersionService;

import java.math.BigInteger;

public class SlefMapper {
    public static Slef map(ShipConfiguration shipConfiguration) {
        return new Slef.SlefBuilder()
                .withHeader(buildHeader(shipConfiguration))
                .withData(buildData(shipConfiguration))
                .build();
    }

    private static Header buildHeader(ShipConfiguration shipConfiguration) {
        return new Header.HeaderBuilder()
                .withAppName("EDOMH")
                .withAppVersion(VersionService.getBuildVersion())
                .withAppUrl(ClipboardHelper.createClipboardShipConfiguration())
                .withAppCustomProperties(new CustomProperties(LocationService.getCurrentSystemAddress(), BigInteger.valueOf((long) shipConfiguration.getCurrentCargo())))
                .build();
    }

    private static Loadout buildData(ShipConfiguration shipConfiguration) {
        return LoadoutMapper.toLoadout(shipConfiguration);
    }

    record CustomProperties(BigInteger location, BigInteger cargo) {

    }
}
