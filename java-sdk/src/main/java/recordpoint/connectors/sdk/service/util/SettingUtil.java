package recordpoint.connectors.sdk.service.util;

import recordpoint.connectors.sdk.json.JsonMapperException;
import recordpoint.connectors.sdk.json.jackson.JacksonMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class SettingUtil {

    private static final JacksonMapper mapper = new JacksonMapper();

    private SettingUtil() {
    }

    public static Map<String, String> getSettingsFromJsonFile(Path jsonPath) throws JsonMapperException {
        if (Files.exists(jsonPath) && !Files.isDirectory(jsonPath)) {
            try (InputStream stream = Files.newInputStream(jsonPath)) {
                return mapper.parseAsMap(stream);
            } catch (IOException e) {
                throw new JsonMapperException(e, Map.class);
            }
        } else {
            throw new JsonMapperException(
                    new IOException(String.format("The %s doesn't exists or is a directory", jsonPath)),
                    Map.class);
        }
    }

}
