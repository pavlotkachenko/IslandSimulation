package org.island.factory;

import lombok.Getter;
import org.island.entity.EOrganisms;
import org.island.entity.OrganismDTO;
import org.island.settings.Config;
import org.island.settings.OrganismParameters;

import java.util.UUID;


@Getter
public class OrganismFactory {
    private static final OrganismParameters config;

    static {
        Config configuration = Config.initialize();
        config = configuration.getOrganismParameters();
    }

    public static OrganismDTO createOrganism(EOrganisms organism) {
        OrganismParameters.AnimalParams params = config.getLimits().get(organism.getType());

        return OrganismDTO.builder()
                .uuid(UUID.randomUUID())
                .type(organism.getType())
                .groupId(organism.getGroupId())
                .icon(config.getIcons().get(organism.getType()))
                .maxWeight(params.getMaxWeight())
                .maxPopulation(params.getMaxPopulation())
                .speed(params.getSpeed())
                .build();
    }
}
