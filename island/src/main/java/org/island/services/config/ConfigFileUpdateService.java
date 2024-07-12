package org.island.services.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import org.island.settings.Config;

import java.net.URL;
import java.util.Objects;

public class ConfigFileUpdateService {
    private static final String SETTING_YAML = "new_master_config.yaml";

    @SneakyThrows
    public void updateFromFile(Config config) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ObjectReader readerForUpdating = mapper.readerForUpdating(config);
        URL resource = Config.class.getClassLoader().getResource(SETTING_YAML);
        if (Objects.nonNull(resource)) {
            readerForUpdating.readValue(resource.openStream());
        }
    }
}
