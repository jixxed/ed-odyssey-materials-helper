import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

abstract class UpdatePOI extends DefaultTask {

    @Input
    String type
    @Input
    String[] materials

    @OutputFile
    abstract RegularFileProperty getOutputFile()

    @TaskAction
    void update() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        logger.info("Running script");
        def file = outputFile.get().asFile
        file.parentFile.mkdirs()
        file.text = ""

        for (String material : materials) {

            //Crystalline Shards
            try (HttpClient httpClient = HttpClient.newHttpClient()) {
                //iterate over pages
                int page = 0;
                int size = 500;
                int totalPages = 1;
                String query = String.format("""
                {"filters":{"genuses":{"logic":"AND","value":["%s"]},"materials":[{"comparison":"<=>","share":["0.0001",999.9],"name":"%s"}]},"sort":[{"name":{"direction":"asc"}}],"size":%d,"page":%d}
                """, type, material, size, page)

                final HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://www.spansh.co.uk/api/bodies/search/save"))
                        .POST(HttpRequest.BodyPublishers.ofString(query))
                        .build();
                final HttpResponse<String> save = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                while (page < totalPages) {
//                    logger.info(save.body())
                    SaveResponse saveResponse = mapper.readValue(save.body(), SaveResponse.class);
//                    logger.info(saveResponse.search_reference)
                    String recallUrl = "https://www.spansh.co.uk/api/bodies/search/recall/" + saveResponse.search_reference + ((page > 0) ? "/" + (page) : "");
                    final HttpRequest request2 = HttpRequest.newBuilder()
                            .uri(URI.create(recallUrl))
                            .GET()
                            .build();
                    HttpResponse<String> recall = httpClient.send(request2, HttpResponse.BodyHandlers.ofString());
//                    logger.info(recall.body())
                    RecallResponse recallResponse = mapper.readValue(recall.body(), RecallResponse.class);
                    // 57 -> 5.7 -> 6 pages
                    totalPages = (int) Math.ceil(recallResponse.count / size)

                    for (Result result : recallResponse.results) {
                        Output output = new Output()
                        output.body = result.name.substring(result.system_name.length() + 1).toUpperCase()
                        output.system = result.system_name
                        output.material = material.toLowerCase()
                        output.distance = result.distance_to_arrival
                        output.x = result.system_x
                        output.y = result.system_y
                        output.z = result.system_z
                        String outputString = mapper.writeValueAsString(output);
//                        logger.info(outputString);
                        // write to file
                        file.text += outputString + "\n"
                    }
                    page++
                    logger.info("Page " + page + " of " + recallResponse.count + " " + material + " " + type + " results over " + totalPages + " pages");
                    Thread.sleep(2000)
                }
            }
        }
    }
}

class SaveResponse {
    String search_reference
}

class RecallResponse {
    int count
    Result[] results
    Search search
}

class Result {
    String system_name
    Double distance_to_arrival
    String name
    Double system_x
    Double system_y
    Double system_z
}

class Search {
    int page
    int size
}


class Output {
    String system
    Double distance
    String body
    String material
    Double x
    Double y
    Double z
}