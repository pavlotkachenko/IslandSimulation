package org.island.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.InputStream;

@Getter
@Setter
public final class Config {
    public static final String SETTING_YAML = "new_master_config.yaml";

    private IslandSimulationConfig islandSimulationConfig;
    private AnimalParameters animalParameters;

    private static Config instance;

    private Config() {}

    public static synchronized Config initialize() {
        if (instance == null) {
            instance = loadConfig();
        }
        return instance;
    }

    @SneakyThrows
    private static Config loadConfig() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(SETTING_YAML)) {
            if (inputStream != null) {
                return mapper.readValue(inputStream, Config.class);
            } else {
                throw new IllegalStateException("Could not find the YAML configuration file.");
            }
        }
    }
}
