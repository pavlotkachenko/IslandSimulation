package org.island.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import org.island.settings.CreatureConfig;
import org.island.entity.Group;
import org.island.entity.Organism;

import java.io.InputStream;

public class OrganismFactory {
    private static final CreatureConfig config;

    private OrganismFactory() {
    }

    static {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream inputStream = OrganismFactory.class.getClassLoader().getResourceAsStream("new_master_config.yaml");
        try {
            config = mapper.readValue(inputStream, CreatureConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    @SneakyThrows
    public static Organism createOrganism(Group group) {
        CreatureConfig.AnimalParams params = config.getLimits().get(group.getType());
        return Organism.builder()
                .type(group.getType())
                .groupId(group.getGroupId())
                .icon(config.getIcons().get(group.getType()))
                .maxWeight(params.getMaxWeight())
                .maxPopulation(params.getMaxPopulation())
                .speed(params.getSpeed())
                .build();

    }
}