package nl.jixxed.eliteodysseymaterials.service.eddn;

abstract class EDDNMapper {
    static String nullIfBlank(final String value){
        if(value == null || value.isBlank()){
            return null;
        }
        return value;
    }
}
