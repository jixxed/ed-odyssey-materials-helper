package nl.jixxed.eliteodysseymaterials.service.eddn;

import java.util.List;
import java.util.Optional;

abstract class EDDNMapper {
    static String nullIfBlank(final String value){
        if(value == null || value.isBlank()){
            return null;
        }
        return value;
    }

    static <T> Optional<List<T>> mapToNullIfEmptyList(final Optional<List<T>> optionalList){
        if(optionalList.map(List::isEmpty).orElse(true)){
            return Optional.empty();
        }
        return optionalList;
    }
}
