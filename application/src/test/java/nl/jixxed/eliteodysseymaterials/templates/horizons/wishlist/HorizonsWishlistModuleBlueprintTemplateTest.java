package nl.jixxed.eliteodysseymaterials.templates.horizons.wishlist;

import javafx.embed.swing.JFXPanel;
import nl.jixxed.eliteodysseymaterials.builder.BoxBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ButtonBuilder;
import nl.jixxed.eliteodysseymaterials.builder.LabelBuilder;
import nl.jixxed.eliteodysseymaterials.builder.ResizableImageViewBuilder;
import nl.jixxed.eliteodysseymaterials.constants.HorizonsBlueprintConstants;
import nl.jixxed.eliteodysseymaterials.constants.OsConstants;
import nl.jixxed.eliteodysseymaterials.constants.PathConfiguration;
import nl.jixxed.eliteodysseymaterials.domain.ApplicationState;
import nl.jixxed.eliteodysseymaterials.domain.HorizonsModuleWishlistBlueprint;
import nl.jixxed.eliteodysseymaterials.domain.PathItem;
import nl.jixxed.eliteodysseymaterials.domain.ships.ShipModule;
import nl.jixxed.eliteodysseymaterials.enums.*;
import nl.jixxed.eliteodysseymaterials.helper.OsCheck;
import nl.jixxed.eliteodysseymaterials.service.LocaleService;
import nl.jixxed.eliteodysseymaterials.service.WishlistService;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableButton;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableHBox;
import nl.jixxed.eliteodysseymaterials.templates.destroyables.DestroyableLabel;
import nl.jixxed.eliteodysseymaterials.templates.generic.ShortestPathFlow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.ref.WeakReference;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.fail;

class HorizonsWishlistModuleBlueprintTemplateTest {

    private static JFXPanel fxPanel;

    @BeforeAll
    static void beforeAll() {
        final List<? extends ShipModule> allModules = ShipModule.ALL_MODULES.stream().flatMap(List::stream).toList();
        fxPanel = new JFXPanel();
        Locale.setDefault(Locale.ENGLISH);
        LocaleService.setCurrentLocale(LocaleService.getCurrentLocale());
        OsConstants.setPathConfiguration(new PathConfiguration() {
            @Override
            public String getConfigDirectory() {
//                try {
                String path = "src/test/resources";

                File file = new File(path);
                String absolutePath = file.getAbsolutePath();
                return absolutePath + getOsSlash() + "config" + getOsSlash() + "f0000000" + getOsSlash() + "settings";
//                } catch (URISyntaxException e) {
//                    throw new RuntimeException(e);
//                }
            }

            @Override
            public String getDefaultWatchedFolder() {
                String path = "src/test/resources";

                File file = new File(path);
                String absolutePath = file.getAbsolutePath();
                return absolutePath + getOsSlash() + "config" + getOsSlash() + "f0000000" + getOsSlash() + "journal";
            }

            @Override
            public String getOsSlash() {
                return (OsCheck.isWindows()) ? "\\" : "/";
            }
        });
    }

    @Test
    public void testMemoryLeakHorizonsWishlistModuleBlueprintTemplate() {

        ApplicationState.getInstance().addCommander("unittest", "F0000000", GameVersion.LIVE, LocalDateTime.of(2024,12,12,12,12,12));
        HorizonsWishlistModuleBlueprintTemplate subject = ApplicationState.getInstance().getPreferredCommander()
                .map(commander -> WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getItems().stream()
                        .findFirst()
                        .map(wishlistRecipe -> new HorizonsWishlistModuleBlueprintTemplate(WishlistService.getHorizonsWishlists(commander).getSelectedWishlist().getUuid(), (HorizonsModuleWishlistBlueprint) wishlistRecipe))
                        .orElse(null))
                .orElse(null);
        Assertions.assertNotNull(subject, "HorizonsWishlistModuleBlueprintTemplate failed to initialize");

        WeakReference<?> weakRef = new WeakReference<>(subject);
        subject.destroyTemplate();
        subject = null;
        attemptGC(weakRef);
        Assertions.assertNull(weakRef.get(), "Unused HorizonsWishlistModuleBlueprintTemplate must be gc'ed");
    }

    @Test
    public void testMemoryLeakDestroyableHBox() {

        ApplicationState.getInstance().addCommander("unittest", "F0000000", GameVersion.LIVE, LocalDateTime.of(2024,12,12,12,12,12));
        DestroyableHBox subject = BoxBuilder.builder()
                .withNodes(LabelBuilder.builder().withText("blank").build())
                .buildHBox();
        Assertions.assertNotNull(subject, "HorizonsWishlistModuleBlueprintTemplate failed to initialize");

        WeakReference<?> weakRef = new WeakReference<>(subject);
        subject.destroy();
        subject = null;
        attemptGC(weakRef);
        Assertions.assertNull(weakRef.get(), "Unused HorizonsWishlistModuleBlueprintTemplate must be gc'ed");
    }

    @Test
    public void testMemoryLeakDestroyableShortestPathItem() {

        ApplicationState.getInstance().addCommander("unittest", "F0000000", GameVersion.LIVE, LocalDateTime.of(2024,12,12,12,12,12));
        final PathItem<HorizonsBlueprintName> pathItem = new PathItem<>(List.of(Engineer.MARSHA_HICKS, Engineer.TOD_THE_BLASTER_MCQUINN, Engineer.ZACARIAH_NEMO), List.of(HorizonsBlueprintConstants.getRecipe(HorizonsBlueprintName.FRAGMENT_CANNON, HorizonsBlueprintType.DOUBLE_SHOT, HorizonsBlueprintGrade.GRADE_3)));
        pathItem.setEngineer(Engineer.UNKNOWN);
        pathItem.setDistance(100D);
//        ShortestPathItem subject = new ShortestPathItem(pathItem, 1, Expansion.HORIZONS);
//        Assertions.assertNotNull(subject, "ShortestPathItem failed to initialize");

//        WeakReference<?> weakRef = new WeakReference<>(subject);
        ShortestPathFlow<HorizonsBlueprintName> flow = new ShortestPathFlow<>(Expansion.HORIZONS);
        flow.setItems(List.of(pathItem));
        WeakReference<?> weakRef = new WeakReference<>(flow.getNodes().get(0));
        flow.setItems(List.of());
//        subject.destroy();
//        subject = null;
        attemptGC(weakRef);
        Assertions.assertNull(weakRef.get(), "Unused ShortestPathItem must be gc'ed");
    }

    @Test
    public void testMemoryLeakLabel() {

        ApplicationState.getInstance().addCommander("unittest", "F0000000", GameVersion.LIVE, LocalDateTime.of(2024,12,12,12,12,12));
        DestroyableLabel subject = LabelBuilder.builder()
                .withText("blank")
                .withOnMouseClicked(event -> {
                    // do nothing
                })
                .build();
        Assertions.assertNotNull(subject, "HorizonsWishlistModuleBlueprintTemplate failed to initialize");

        WeakReference<?> weakRef = new WeakReference<>(subject);
        subject.destroy();
        subject = null;
        attemptGC(weakRef);
        Assertions.assertNull(weakRef.get(), "Unused HorizonsWishlistModuleBlueprintTemplate must be gc'ed");
    }

    @Test
    public void testMemoryLeakButton() {
        AtomicInteger somevar = new AtomicInteger(1);
        ApplicationState.getInstance().addCommander("unittest", "F0000000", GameVersion.LIVE, LocalDateTime.of(2024,12,12,12,12,12));
        var graphic = ResizableImageViewBuilder.builder()
                .withStyleClass("market-copy-icon")
                .withImage("/images/other/copy.png")
                .build();
        DestroyableButton subject = ButtonBuilder.builder()
                .withText("blank")
                .withGraphic(graphic)
                .withOnAction(event -> {
                    somevar.getAndIncrement();
                })
                .build();
        Assertions.assertNotNull(subject, "HorizonsWishlistModuleBlueprintTemplate failed to initialize");

        WeakReference<?> weakRef = new WeakReference<>(subject);
        subject.destroy();
        subject = null;
        attemptGC(weakRef);
        Assertions.assertNull(weakRef.get(), "Unused HorizonsWishlistModuleBlueprintTemplate must be gc'ed");
    }

    /**
     * Tries to let the weakRef be gc'ed.
     *
     * @param weakRef the weakRef to be gc'ed
     */
    public static void attemptGC(WeakReference<?> weakRef) {
        for (int i = 0; i < 10; i++) {
            System.gc();

            if (weakRef.get() == null) {
                break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                fail("InterruptedException occurred during Thread.sleep()");
            }
        }
    }
}