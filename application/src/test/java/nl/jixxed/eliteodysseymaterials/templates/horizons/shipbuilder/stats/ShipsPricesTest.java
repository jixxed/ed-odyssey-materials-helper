package nl.jixxed.eliteodysseymaterials.templates.horizons.shipbuilder.stats;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import nl.jixxed.eliteodysseymaterials.domain.ships.Origin;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Outfitting.Item;
import nl.jixxed.eliteodysseymaterials.schemas.journal.Outfitting.Outfitting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ShipsPricesTest {
    @Test
    public void test() throws IOException {
        final List<? extends ShipModule> allModules = ShipModule.ALL_MODULES.stream().flatMap(List::stream).toList();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());
        Map<String, BigInteger> prices = new HashMap<>();

        addToPrices(prices, "/ships/prices/galti.json");
        addToPrices(prices, "/ships/prices/sherbo.json");
        addToPrices(prices, "/ships/prices/sherbo2.json");
        addToPrices(prices, "/ships/prices/zibal.json");
        addToPrices(prices, "/ships/prices/14eridani.json");
        addToPrices(prices, "/ships/prices/furuhjelm.json");
        addToPrices(prices, "/ships/prices/nanabozho.json");
        addToPrices(prices, "/ships/prices/sharru.json");
        addToPrices(prices, "/ships/prices/sunwen.json");
        addToPrices(prices, "/ships/prices/cherets.json");
      //Assertions.assertAll(
        allModules.stream().filter(shipModule -> shipModule.getOrigin().equals(Origin.HUMAN)  &&!shipModule.isCGExclusive() && !shipModule.isPreEngineered() && !shipModule.isLegacy()).forEach(module -> {
            final BigInteger price = prices.get(module.getInternalName().toLowerCase());

            final Long moduleprice = module.getBasePrice();
            if (price == null || moduleprice != price.longValue()) {
                log.error(module.getId() + " - " + module.getInternalName() + " price mismatch: moduleprice=" + moduleprice + ", gameprice=" + price);
            }
            //return (Executable) () -> Assertions.assertEquals(moduleprice, gameprice < 0 ? moduleprice : gameprice, module.getId() + " - " + module.getInternalName() + " price mismatch: moduleprice=" + moduleprice + ", gameprice=" + gameprice);
        });//.collect(Collectors.toList())
        // );

    }
void addToPrices(Map<String, BigInteger> prices, String file)throws IOException{
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.registerModule(new Jdk8Module());
    Outfitting outfitting = objectMapper.readValue(ShipsPricesTest.class.getResourceAsStream(file), Outfitting.class);
    Map<String, BigInteger> pricesOutfitting = outfitting.getItems().map(list -> list.stream().collect(Collectors.toMap(Item::getName, Item::getBuyPrice))).orElse(Collections.emptyMap());
    prices.putAll(pricesOutfitting);
}
    @Test
    public void testRounding() {
        for (int x = 0; x < 100000; x++) {
            int y = (int) Math.round(1.2 * x);
            int xRev = reverse(y);
            Assertions.assertEquals(x, xRev);
        }
    }

    int reverse(int y) {
        return (int) Math.round((double) y / 1.2);
    }

    @Test
    public void testchange() throws IOException {
        final List<? extends ShipModule> allModules = ShipModule.ALL_MODULES.stream().flatMap(List::stream).toList();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());
        Outfitting outfitting = objectMapper.readValue(ShipsPricesTest.class.getResourceAsStream("/ships/prices/isolaprospect.json"), Outfitting.class);
        outfitting.getItems().stream().forEach(list -> list.forEach(item -> {
            item.setBuyPrice(BigInteger.valueOf(Math.round(item.getBuyPrice().doubleValue() / 1.2)));
        }));
        System.out.println(objectMapper.writeValueAsString(outfitting));

    }

    float discount(float price, float discount) {
        return price * (1f - discount);
    }

    double discountReverse(double price, double discount) {
        return price  / (1f - discount);
    }

    @Test
    public void testshin() throws IOException {
        final List<? extends ShipModule> allModules = ShipModule.ALL_MODULES.stream().flatMap(List::stream).toList();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());
        Outfitting outfittingIsola = objectMapper.readValue(ShipsPricesTest.class.getResourceAsStream("/ships/prices/isolaprospect.json"), Outfitting.class);
        Outfitting outfittingJameson = objectMapper.readValue(ShipsPricesTest.class.getResourceAsStream("/ships/prices/jameson_memorial.json"), Outfitting.class);
        Outfitting outfittingRay = objectMapper.readValue(ShipsPricesTest.class.getResourceAsStream("/ships/prices/ray_gateway.json"), Outfitting.class);
        Outfitting outfittingGalti = objectMapper.readValue(ShipsPricesTest.class.getResourceAsStream("/ships/prices/galti.json"), Outfitting.class);
        Outfitting outfittingSherbo = objectMapper.readValue(ShipsPricesTest.class.getResourceAsStream("/ships/prices/sherbo.json"), Outfitting.class);
        Map<String, BigInteger> pricesIsola = outfittingIsola.getItems().map(list -> list.stream().collect(Collectors.toMap(Item::getName, Item::getBuyPrice))).orElse(Collections.emptyMap());
        Map<String, BigInteger> pricesJameson = outfittingJameson.getItems().map(list -> list.stream().collect(Collectors.toMap(Item::getName, Item::getBuyPrice))).orElse(Collections.emptyMap());
        Map<String, BigInteger> pricesRay = outfittingRay.getItems().map(list -> list.stream().collect(Collectors.toMap(Item::getName, Item::getBuyPrice))).orElse(Collections.emptyMap());
        Map<String, BigInteger> pricesGalti = outfittingGalti.getItems().map(list -> list.stream().collect(Collectors.toMap(Item::getName, Item::getBuyPrice))).orElse(Collections.emptyMap());
        Map<String, BigInteger> pricesSherbo = outfittingSherbo.getItems().map(list -> list.stream().collect(Collectors.toMap(Item::getName, Item::getBuyPrice))).orElse(Collections.emptyMap());
        Assertions.assertAll(pricesIsola.entrySet().stream().map((entry) -> {
           return (Executable)() -> {
               if (pricesJameson.containsKey(entry.getKey())) {
                   long isolaPrice = Math.round(BigDecimal.valueOf(entry.getValue().longValue()).divide(BigDecimal.valueOf(1.2d),2, RoundingMode.HALF_UP).doubleValue());
                   double discount1 = 0.025d;
                   double discount2 = 0.1d;
                   //long jamesonPrice = Math.round(BigDecimal.valueOf(pricesJameson.get(entry.getKey()).longValue()).divide(BigDecimal.valueOf(0.8775d),2, RoundingMode.HALF_UP).doubleValue());
                   long jamesonPrice = Math.round(discountReverse(discountReverse(pricesJameson.get(entry.getKey()).doubleValue(), discount1), discount2));
                   Assertions.assertTrue(Math.abs(isolaPrice - jamesonPrice) <= 2, entry.getKey() + " - " + isolaPrice + " - " + jamesonPrice);
               }else{
                   Assertions.assertTrue(true);
               }
           };
        }).toArray(Executable[]::new));
//        Assertions.assertAll(
//                pricesSherbo.keySet().stream().map(module -> {
//                    long isolaPrice = Math.round(discountReverse(pricesIsola.getOrDefault(module, BigInteger.ZERO).floatValue(), -0.2f));
//                    float discount1 = 0.025f;
//                    float discount2 = 0.1f;
//                    long jamesonPrice = Math.round(discountReverse(discountReverse(pricesJameson.getOrDefault(module, BigInteger.ZERO).floatValue(), discount1), discount2));
//                    long rayPrice = Math.round(discountReverse(pricesRay.getOrDefault(module, BigInteger.ZERO).floatValue(), 0.15f));
//                    long galtiPrice = pricesGalti.getOrDefault(module, BigInteger.ZERO).longValue();
//                    long sherboPrice = pricesSherbo.getOrDefault(module, BigInteger.ZERO).longValue();
//                    return (Executable) () -> Assertions.assertTrue(galtiPrice == 0L || Math.abs(isolaPrice - galtiPrice) < 1, module + " - isolaPrice:" + isolaPrice + " - galti:" + galtiPrice);
//
//                }).toArray(Executable[]::new)
//        );
        final String module = "hpt_dumbfiremissilerack_fixed_small";
        //Assertions.assertAll(
//        allModules.stream().filter(shipModule -> !shipModule.isCGExclusive() && !shipModule.isPreEngineered() && !shipModule.isLegacy()).forEach(module -> {
//            final BigInteger price = prices.get(module.getInternalName().toLowerCase());
//            long gameprice = Math.round((price != null ? price.doubleValue() : -1D) / 1.2);//-1 = not in data
//
//            final Long moduleprice = module.getBasePrice();
//            if(gameprice == -1 /*&& Math.abs(moduleprice-gameprice) > 20*/){
//                log.error(module.getId() + " - " + module.getInternalName() + " price mismatch: moduleprice=" + moduleprice + ", gameprice=" + gameprice);
//            }
//            //return (Executable) () -> Assertions.assertEquals(moduleprice, gameprice < 0 ? moduleprice : gameprice, module.getId() + " - " + module.getInternalName() + " price mismatch: moduleprice=" + moduleprice + ", gameprice=" + gameprice);
//        });//.collect(Collectors.toList())
        // );

    }
    @Test
    public void testComplete() throws IOException {
        final List<String> allModules = ShipModule.ALL_MODULES.stream().flatMap(List::stream).filter(shipModule -> shipModule.getOrigin() == Origin.HUMAN && !shipModule.isCGExclusive() && !shipModule.isPreEngineered() && !shipModule.isLegacy()).map(ShipModule::getInternalName).map(String::toLowerCase).collect(Collectors.toList());
        Map<String, BigInteger> prices = new HashMap<>();

//        deduct(prices, allModules, "/ships/prices/galti.json");
//        deduct(prices, allModules, "/ships/prices/sherbo.json");
//        deduct(prices, allModules, "/ships/prices/sherbo2.json");
//        deduct(prices, allModules, "/ships/prices/zibal.json");
//        deduct(prices, allModules, "/ships/prices/14eridani.json");
//        deduct(prices, allModules, "/ships/prices/furuhjelm.json");
//        deduct(prices, allModules, "/ships/prices/nanabozho.json");
//        deduct(prices, allModules, "/ships/prices/sharru.json");
//        deduct(prices, allModules, "/ships/prices/sunwen.json");
//        deduct(prices, allModules, "/ships/prices/cherets.json");
        deduct(prices, allModules, "/ships/prices/isolaprospect.json");
//Arque
        //UGP 485
        System.out.println("Missing modules: " + allModules.size());
        allModules.forEach(System.out::println);
        System.out.println("Pricelist: " );
        prices.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry-> System.out.println(entry.getKey() + ", " + entry.getValue()));
    }

    private static void deduct(Map<String, BigInteger> prices, List<String> allModules, String file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());
        Outfitting outfittingSherbo2 = objectMapper.readValue(ShipsPricesTest.class.getResourceAsStream(file), Outfitting.class);
        Map<String, BigInteger> pricesSherbo2 = outfittingSherbo2.getItems().map(list -> list.stream().collect(Collectors.toMap(Item::getName, Item::getBuyPrice))).orElse(Collections.emptyMap());
        pricesSherbo2.keySet().forEach(allModules::remove);
        pricesSherbo2.entrySet().forEach(entry -> {
            if (prices.keySet().contains(entry.getKey())) {
                if (prices.get(entry.getKey()).compareTo(entry.getValue()) != 0) {
                    System.out.println(entry.getKey() + " - " + prices.get(entry.getKey()) + " - " + entry.getValue());
                }
            }else{
                prices.put(entry.getKey(), entry.getValue());
            }
        });
    }
}
