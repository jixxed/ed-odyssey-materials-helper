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

import nl.jixxed.eliteodysseymaterials.service.LocaleService;

public enum ColonisationLayout {

    NO_TRUSS,
    DUAL_TRUSS,
    QUAD_TRUSS,
    QUINT_TRUSS,
    DEC_TRUSS,
    ASTEROID,
    OCELLUS,
    APOLLO,
    ARTEMIS,
    PLUTUS,
    VULCAN,
    DYSNOMIA,
    VESTA,
    PROMETHEUS,
    NEMESIS,
    HERMES,
    ANGELIA,
    EIRENE,
    PISTIS,
    SOTER,
    ALETHEIA,
    DEMETER,
    APATE,
    LAVERNA,
    EUTHENIA,
    PHORCYS,
    ENODIA,
    ICHNAEA,
    VACUNA,
    ALASTOR,
    DICAEOSYNE,
    POENA,
    EUNOMIA,
    NOMOS,
    HARMONIA,
    ASCLEPIUS,
    EUPRAXIA,
    ASTRAEUS,
    COEUS,
    DODONA,
    DIONE,
    HEDONE,
    OPORA,
    PASITHEA,
    DIONYSUS,
    BACCHUS,
    HESTIA,
    DECIMA,
    ATROPOS,
    NONA,
    LACHESIS,
    CLOTHO,
    HEPHAESTUS,
    OPIS,
    PONOS,
    TETHYS,
    BIA,
    MEFITIS,
    NECESSITAS,
    ANANKE,
    FAUNA,
    PROVIDENTIA,
    ANTEVORTA,
    PORRIMA,
    ZEUS,
    HERA,
    POSEIDON,
    APHRODITE,
    CONSUS,
    PICUMNUS,
    ANNONA,
    CERES,
    FORNAX,
    OUREA,
    MANTUS,
    ORCUS,
    EREBUS,
    AERECURA,
    FONTUS,
    METEOPE,
    PALICI,
    MINTHE,
    GAEA,
    IOKE,
    BELLONA,
    ENYO,
    POLEMOS,
    MINERVA,
    PHEOBE,
    ASTERIA,
    CAERUS,
    CHRONOS,
    AERGIA,
    COMOS,
    GELOS,
    FUFLUNS,
    TARTARUS,
    AEGLE,
    TELLUS,
    IO,
    ATHENA,
    CAELUS,
    ALALA,
    ARES,
    SILENUS,
    JANUS,
    MOLAE,
    //    TELLUS,duplicate???
    EUNOSTUS;

    public String getLocalizationKey() {
        return "colonisation.layout." + this.name().toLowerCase();
    }

    @Override
    public String toString() {
        return LocaleService.getLocalizedStringForCurrentLocale(getLocalizationKey());
    }

}
