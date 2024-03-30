package nl.jixxed.eliteodysseymaterials.constants.horizons.hardpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
@Ignore
public class GeneratecodeShipsTest {
    private
    String template = """
                public static final Ship %s = new Ship(
                        ShipType.%s,
                        %d,
                        %d,
                        Map.ofEntries(
                        %s
                        ),
                        List.of(
                        %s
                        ),
                        List.of(
                        %s
                        ),
                        List.of(
                        %s
                        ),
                        List.of(
                        %s
                        ));
            """;
    String entries = """
                                Map.entry(HorizonsModifier.TOP_SPEED,%s),
                                Map.entry(HorizonsModifier.BOOST_SPEED,%s),
            //                    Map.entry(HorizonsModifier.MNV,%s),
                                Map.entry(HorizonsModifier.SHIELDS,%s),
                                Map.entry(HorizonsModifier.ARMOUR,%s),
                                Map.entry(HorizonsModifier.MASS,%s),
            //                    Map.entry(HorizonsModifier.FWDACC,%s),
            //                    Map.entry(HorizonsModifier.REVACC,%s),
            //                    Map.entry(HorizonsModifier.LATACC,%s),
            //                    Map.entry(HorizonsModifier.MINTHRUST,%s),
                                Map.entry(HorizonsModifier.BOOST_COST,%s),
                                Map.entry(HorizonsModifier.PITCH_SPEED,%s),
                                Map.entry(HorizonsModifier.YAW_SPEED,%s),
                                Map.entry(HorizonsModifier.ROLL_SPEED,%s),
            //                    Map.entry(HorizonsModifier.PITCH_ACCELERATION,%s),
            //                    Map.entry(HorizonsModifier.YAW_ACCELERATION,%s),
            //                    Map.entry(HorizonsModifier.ROLL_ACCELERATION,%s),
                                Map.entry(HorizonsModifier.MIN_PITCH_SPEED,%s),
                                Map.entry(HorizonsModifier.HEAT_CAPACITY,%s),
                                Map.entry(HorizonsModifier.HEAT_DISSIPATION_MIN,%s),
                                Map.entry(HorizonsModifier.HEAT_DISSIPATION_MAX,%s),
                                Map.entry(HorizonsModifier.FUEL_COST,%s),
                                Map.entry(HorizonsModifier.FUEL_RESERVE,%s),
                                Map.entry(HorizonsModifier.ARMOUR_HARDNESS,%s),
                                Map.entry(HorizonsModifier.MASS_LOCK,%s),
                                Map.entry(HorizonsModifier.CREW,%s)
            """;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test() throws IOException {
        String superjson = new String(GeneratecodeShipsTest.class.getResourceAsStream("/ships.txt").readAllBytes());
        final String[] split = superjson.split("(\\s\\d{1,2}\\s+:+)");
        Arrays.stream(split).filter(json -> !json.isBlank()).forEach(json -> {
            DecimalFormat format = new DecimalFormat("0.0#####", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
            objectMapper.enable(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature());
            objectMapper.enable(JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature());
            objectMapper.enable(JsonReadFeature.ALLOW_TRAILING_COMMA.mappedFeature());
            objectMapper.enable(JsonReadFeature.ALLOW_JAVA_COMMENTS.mappedFeature());
            objectMapper.enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature());

            final JsonNode jsonNode;
            try {
                jsonNode = objectMapper.readTree(json.substring(json.indexOf("{"), json.length()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            final String fdid = jsonNode.get("fdid").asText();
            String params = String.format(Locale.ENGLISH, entries,
                    format.format((Double) Double.parseDouble(jsonNode.get("topspd").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("bstspd").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("mnv").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("shields").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("armour").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("mass").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("fwdacc").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("revacc").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("latacc").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("minthrust").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("boostcost").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("pitch").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("yaw").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("roll").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("pitchacc").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("yawacc").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("rollacc").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("minpitch").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("heatcap").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("heatdismin").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("heatdismax").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("fuelcost").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("fuelreserve").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("hardness").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("masslock").asText())),
                    format.format((Double) Double.parseDouble(jsonNode.get("crew").asText()))
            );
//        slots:{
//            hardpoint:[1,1],
//            utility  :[0,0],
//            component:[1,2,2,2,1,1,1,1],
//            military :[],
//            internal :[2,2,1,1,1,1],
//        },
            StringBuilder hp = new StringBuilder();
            AtomicInteger count = new AtomicInteger(0);
            final Iterator<JsonNode> elements = jsonNode.get("stock").get("hardpoint").elements();
            jsonNode.get("slots").get("hardpoint").elements().forEachRemaining(jsonNode1 -> hp.append("Slot.builder().slotType(SlotType.HARDPOINT).index(" + count.getAndIncrement() + ")" + ".slotSize(" + jsonNode1.asInt() + ")"+ (elements.next().asInt() != 0 ? ".shipModule(PulseLaser.PULSE_LASER_1_F_F)" : "") +".build(),\n"));
            hp.deleteCharAt(hp.length() - 1);
            hp.deleteCharAt(hp.length() - 1);
            count.set(0);
            StringBuilder util = new StringBuilder();
            final Iterator<JsonNode> elements2 = jsonNode.get("stock").get("utility").elements();
            jsonNode.get("slots").get("utility").elements().forEachRemaining(jsonNode1 -> util.append("Slot.builder().slotType(SlotType.UTILITY).index(" + count.getAndIncrement() + ")" + ".slotSize(" + jsonNode1.asInt() + ")"+ (elements2.next().asInt() != 0 ? ".shipModule(PulseLaser.PULSE_LASER_1_F_F)" : "") +".build(),\n"));
            util.deleteCharAt(util.length() - 1);
            util.deleteCharAt(util.length() - 1);
            count.set(0);
//            StringBuilder core = new StringBuilder();
//            final Iterator<JsonNode> elements3 = jsonNode.get("stock").get("component").elements();
            final Iterator<JsonNode> elements1 = jsonNode.get("slots").get("component").elements();
            int value0 = elements1.next().asInt();
            int value1 = elements1.next().asInt();
          int value2 =         elements1.next().asInt();
          int value3 =         elements1.next().asInt();
          int value4 =         elements1.next().asInt();
          int value5 =         elements1.next().asInt();
          int value6 =         elements1.next().asInt();
          int value7 =         elements1.next().asInt();

            final String coreslots = String.format("""
                                        Slot.builder().slotType(SlotType.CORE_ARMOUR).index(0).slotSize(%d).shipModule(Armour.%s_ARMOUR_GRADE_1).build(),
                                        Slot.builder().slotType(SlotType.CORE_POWER_PLANT).index(1).slotSize(%d).shipModule(PowerPlant.POWER_PLANT_%d_E).build(),
                                        Slot.builder().slotType(SlotType.CORE_THRUSTERS).index(2).slotSize(%d).shipModule(Thrusters.THRUSTERS_%d_E).build(),
                                        Slot.builder().slotType(SlotType.CORE_FRAME_SHIFT_DRIVE).index(3).slotSize(%d).shipModule(FrameShiftDrive.FRAME_SHIFT_DRIVE_%d_E).build(),
                                        Slot.builder().slotType(SlotType.CORE_LIFE_SUPPORT).index(4).slotSize(%d).shipModule(LifeSupport.LIFE_SUPPORT_%d_E).build(),
                                        Slot.builder().slotType(SlotType.CORE_POWER_DISTRIBUTION).index(5).slotSize(%d).shipModule(PowerDistributor.POWER_DISTRIBUTOR_%d_E).build(),
                                        Slot.builder().slotType(SlotType.CORE_SENSORS).index(6).slotSize(%d).shipModule(Sensors.SENSORS_%d_E).build(),
                                        Slot.builder().slotType(SlotType.CORE_FUEL_TANK).index(7).slotSize(%d).shipModule(FuelTank.FUEL_TANK_%d_C).build()
                    """,
                    value0,jsonNode.get("fdname").asText().toUpperCase(),
                    value1,
                    value1,
                    value2,
                    value2,
                    value3,
                    value3,
                    value4,
                    value4,
                    value5,
                    value5,
                    value6,
                    value6,
                    value7,
                    value7
            );

            //jsonNode.get("slots").get("component").elements().forEachRemaining(jsonNode1 -> core.append("Slot.builder().slotType(SlotType." + coreslots[count.get()] + ").index(" + count.getAndIncrement() + ")" + ".slotSize(" + jsonNode1.asInt() + ")"+ (elements3.next().asInt() != 0 ? ".shipModule(PulseLaser.PULSE_LASER_1_F_F)" : "") +".build(),\n"));
//            core.deleteCharAt(core.length() - 1);
//            core.deleteCharAt(core.length() - 1);
//            count.set(0);
            StringBuilder opt = new StringBuilder();
            final Iterator<JsonNode> elements4 = jsonNode.get("stock").get("internal").elements();
            jsonNode.get("slots").get("internal").elements().forEachRemaining(jsonNode1 -> opt.append("Slot.builder().slotType(SlotType.OPTIONAL).index(" + count.getAndIncrement() + ")" + ".slotSize(" + jsonNode1.asInt() + ")"+ (elements4.next().asInt() != 0 ? ".shipModule(PulseLaser.PULSE_LASER_1_F_F)" : "") +".build(),\n"));
            final Iterator<JsonNode> elements5 = jsonNode.get("stock").get("military").elements();
            jsonNode.get("slots").get("military").elements().forEachRemaining(jsonNode1 -> opt.append("Slot.builder().slotType(SlotType.OPTIONAL).index(" + count.getAndIncrement() + ")"+ ".slotSize(" + jsonNode1.asInt() + ")" + (elements5.next().asInt() != 0 ? ".shipModule(PulseLaser.PULSE_LASER_1_F_F)" : "") +".build(),\n"));
            opt.deleteCharAt(opt.length() - 1);
            opt.deleteCharAt(opt.length() - 1);
            count.set(0);

            System.out.println(String.format(template, jsonNode.get("fdname").asText().toUpperCase(), jsonNode.get("fdname").asText().toUpperCase(), jsonNode.get("cost").asLong(), jsonNode.get("retail").asLong(), params, hp, util, coreslots, opt));

        });
    }
}
