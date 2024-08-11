package org.island.factory;

import lombok.Getter;
import lombok.SneakyThrows;
import org.island.entity.EOrganisms;
import org.island.entity.OrganismDTO;
import org.island.settings.Config;
import org.island.settings.OrganismParameters;

import java.util.HashMap;
import java.util.Map;

@Getter
public class OrganismFactory {
    private static final OrganismParameters config;
    private final Map<String, OrganismDTO> ORGANISMS = new HashMap<>();

    static {
        Config configuration = Config.initialize();
        config = configuration.getOrganismParameters();
    }

    public OrganismFactory() {
        for (EOrganisms value : EOrganisms.values()) {
            ORGANISMS.put(value.getType(), createOrganism(value));
        }
    }

    @SneakyThrows
    public static OrganismDTO createOrganism(EOrganisms organism) {
        OrganismParameters.AnimalParams params = config.getLimits().get(organism.getType());
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
