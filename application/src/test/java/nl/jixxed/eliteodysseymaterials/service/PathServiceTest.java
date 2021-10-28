package nl.jixxed.eliteodysseymaterials.service;

import de.saxsys.mvvmfx.testingutils.JfxToolkitExtension;
import de.saxsys.mvvmfx.testingutils.jfxrunner.TestInJfxThread;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ExecutionException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(JfxToolkitExtension.class)
class PathServiceTest {
    @Test
    @TestInJfxThread
    void calculateShortestPath() throws ExecutionException, InterruptedException {
//        final JFXPanel jfxPanel = new JFXPanel(); // Scrollable JCompenent
//        final List<WishlistBlueprint> wishlistBlueprints = List.of(
//                new WishlistBlueprint("TEST", new WishlistRecipe(RecipeName.GREATER_RANGE_PLASMA, true)),
//                new WishlistBlueprint("TEST", new WishlistRecipe(RecipeName.MAGAZINE_SIZE, true)),
//                new WishlistBlueprint("TEST", new WishlistRecipe(RecipeName.SCOPE, true)),
//                new WishlistBlueprint("TEST", new WishlistRecipe(RecipeName.STABILITY, true))
//        );
////
////        final CompletableFuture<List<PathItem>> isInApplicationThread = new CompletableFuture<>();
////
////        Platform.runLater(() -> {
//
//        final List<PathItem> pathItems = PathService.calculateShortestPath(wishlistBlueprints);
//            isInApplicationThread.complete(pathItems);
//        });
//
//        Assert.assertEquals(isInApplicationThread.get().size(), 2);
    }
}