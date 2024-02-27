package nl.jixxed.eliteodysseymaterials.service.eddn;

public class EDDNAllMessagesTest {

//    private static Stream<Arguments> messages() {
//        return Stream.of(
//                Arguments.of("approachsettlement.json", ApproachSettlement.class, (Consumer<Event>) (event) -> EDDNService.approachSettlement((ApproachSettlement) event)),
//                Arguments.of("carrierjump.json", CarrierJump.class, (Consumer<Event>) (event) -> EDDNService.carrierjump((CarrierJump) event)),
//                Arguments.of("codexentry.json", CodexEntry.class, (Consumer<Event>) (event) -> EDDNService.codexEntry((CodexEntry) event)),
//                Arguments.of("commodity.json", Market.class, (Consumer<Event>) (event) -> EDDNService.commodity((Market) event)),
//                Arguments.of("docked.json", Docked.class, (Consumer<Event>) (event) -> EDDNService.docked((Docked) event)),
//                Arguments.of("dockingdenied.json", DockingDenied.class, (Consumer<Event>) (event) -> EDDNService.dockingDenied((DockingDenied) event)),
//                Arguments.of("dockinggranted.json", DockingGranted.class, (Consumer<Event>) (event) -> EDDNService.dockingGranted((DockingGranted) event)),
//                Arguments.of("fcmaterials.json", FCMaterials.class, (Consumer<Event>) (event) -> EDDNService.fcmaterialsjournal((FCMaterials) event)),
//                Arguments.of("fsdjump.json", FSDJump.class, (Consumer<Event>) (event) -> EDDNService.fsdjump((FSDJump) event)),
//                Arguments.of("fssallbodiesfound.json", FSSAllBodiesFound.class, (Consumer<Event>) (event) -> EDDNService.fssallbodiesfound((FSSAllBodiesFound) event)),
//                Arguments.of("fssbodysignals.json", FSSBodySignals.class, (Consumer<Event>) (event) -> EDDNService.fssbodysignals((FSSBodySignals) event)),
//                Arguments.of("fssdiscoveryscan.json", FSSDiscoveryScan.class, (Consumer<Event>) (event) -> EDDNService.fssdiscoveryscan((FSSDiscoveryScan) event)),
//                Arguments.of("fsssignalsdiscovered.json", FSSSignalDiscovered.class, (Consumer<Event>) (event) -> {
//                    EDDNService.fsssignaldiscovered((FSSSignalDiscovered) event);
//                    EDDNService.anyEvent(JournalEventType.BACKPACK,LocalDateTime.now());
//                }),
//                Arguments.of("location.json", Location.class, (Consumer<Event>) (event) -> EDDNService.location((Location) event)),
//                Arguments.of("navbeaconscan.json", NavBeaconScan.class, (Consumer<Event>) (event) -> EDDNService.navbeaconscan((NavBeaconScan) event)),
//                Arguments.of("navroute.json", NavRoute.class, (Consumer<Event>) (event) -> EDDNService.navroute((NavRoute) event)),
//                Arguments.of("outfitting.json", Outfitting.class, (Consumer<Event>) (event) -> EDDNService.outfitting((Outfitting) event)),
//                Arguments.of("saasignalsfound.json", SAASignalsFound.class, (Consumer<Event>) (event) -> EDDNService.saasignalsfound((SAASignalsFound) event)),
//                Arguments.of("scan.json", Scan.class, (Consumer<Event>) (event) -> EDDNService.scan((Scan) event)),
//                Arguments.of("scanbarycentre.json", ScanBaryCentre.class, (Consumer<Event>) (event) -> EDDNService.scanbarycentre((ScanBaryCentre) event)),
//                Arguments.of("shipyard.json", Shipyard.class, (Consumer<Event>) (event) -> EDDNService.shipyard((Shipyard) event))
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("messages")
//    void testMessages(String filename, Class clazz, Consumer<Event> consumer) throws URISyntaxException, IOException, InterruptedException {
//        final ApplicationState applicationState = Mockito.mock(ApplicationState.class);
////        Mockito.when(ApplicationState.getInstance()).thenReturn(instance);
////        LocationService.getCurrentStarPos(scanBaryCentre.getSystemAddress())
//        String body = "Maia";
//        LocationService.setStatusBodyName(Optional.of(body));
//        Location location = Location.builder().withBody(body).withBodyID(BigInteger.ONE).withStarSystem("Sol").withBodyType("type").withSystemAddress(BigInteger.ZERO).withLongitude(BigDecimal.ONE).withLatitude(BigDecimal.ONE).withStarPos(List.of(BigInteger.ZERO,BigInteger.ZERO,BigInteger.ZERO)).build();
//        StarSystem starSystem= new StarSystem("Sol",0,0,0);
//        String stationName ="Fort Knox";
//        Boolean first = true;
//        EventService.publish(new LocationJournalEvent(location,starSystem,body,stationName,first));
//        try (MockedStatic<ApplicationState> applicationStateMock = mockStatic(ApplicationState.class)) {
//            applicationStateMock.when(ApplicationState::getInstance).thenReturn(applicationState);
//            EDDNService.init();
//            when(applicationState.getPreferredCommander()).thenReturn(Optional.of(new Commander("test", "test", GameVersion.LIVE)));
//            when(applicationState.getExpansion()).thenReturn(Expansion.ODYSSEY);
//            //{ "timestamp":"2024-02-27T20:31:19Z", "event":"Fileheader", "part":1, "language":"English/UK", "Odyssey":true, "gameversion":"4.0.0.1800", "build":"r300110/r0 " }
//            final Fileheader fileHeader = Fileheader.builder().withBuild("r300110/r0 ").withGameversion("4.0.0.1800").withLanguage("English/UK").withOdyssey(true).withPart(BigInteger.ONE).build();
//            fileHeader.setEvent("Fileheader");
//            fileHeader.setTimestamp(LocalDateTime.now());
//            when(applicationState.getFileheader()).thenReturn(fileHeader);
//            applicationStateMock.when(ApplicationState::getGameVersion).thenReturn(GameVersion.LIVE);
//            System.out.println(System.getProperty("app.version"));
//            String file = Files.readString(Path.of(this.getClass().getResource("/eddn/transmission/" + filename).toURI()));
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
//            objectMapper.registerModule(new JavaTimeModule());
//            objectMapper.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
//            Event event = (Event) objectMapper.readValue(file, clazz);
//            consumer.accept(event);
//            Thread.sleep(1000);
////            Mockito.verify(productDAO, times(0)).getProduct(any());
//        }
////        when(ApplicationState.getInstance().getPreferredCommander()
//
//    }
}
