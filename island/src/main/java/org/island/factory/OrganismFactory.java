package org.island.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import org.island.entity.EOrganisms;
import org.island.settings.AnimalParameters;
import org.island.entity.OrganismDTO;

import java.io.InputStream;

public class OrganismFactory {
    private static final AnimalParameters config;

    private OrganismFactory() {
    }

    static {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        InputStream inputStream = OrganismFactory.class.getClassLoader().getResourceAsStream("new_master_config.yaml");
        try {
            config = mapper.readValue(inputStream, AnimalParameters.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    @SneakyThrows
    public static OrganismDTO createOrganism(EOrganisms organism) {
        AnimalParameters.AnimalParams params = config.getLimits().get(organism.getType());
        return OrganismDTO.builder()
                .type(organism.getType())
                .groupId(organism.getGroupId())
                .icon(config.getIcons().get(organism.getType()))
                .maxWeight(params.getMaxWeight())
                .maxPopulation(params.getMaxPopulation())
                .speed(params.getSpeed())
                .build();
    }
}