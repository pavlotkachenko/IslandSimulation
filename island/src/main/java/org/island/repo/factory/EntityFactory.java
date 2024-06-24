package org.island.repo.factory;

import lombok.AllArgsConstructor;
import org.island.entity.Group;
import org.island.entity.Organism;
import org.island.repo.factory.animal_factories.grasseaters.*;
import org.island.repo.factory.animal_factories.predators.*;
import org.island.repo.factory.plant_factories.HerbFactory;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
public class EntityFactory implements Factory{
    private static EntityFactory FACTORY;

    private final Map<String, OrganismFactory> factoryMap;
    private final Map<String, Organism> prototypes;

    public EntityFactory(){
        factoryMap = createFactoryMap();
        prototypes = createPrototypes();
    }

    public static EntityFactory getFactory(){
        if (FACTORY == null){
            FACTORY = new EntityFactory();
        }
        return FACTORY;
    }

    @Override
    public Map<String, Organism> getPrototypes() {
        return prototypes;
    }

    @Override
    public Organism create(String type) {
        OrganismFactory factory = factoryMap.get(type);
        Organism organism = factory.create(type);
        if (organism != null){
            return organism;
        } else throw new IllegalArgumentException("Wrong type of creation - " + type);
    }

    private Map<String, OrganismFactory> createFactoryMap(){
        Map<String, OrganismFactory> factories = new LinkedHashMap<>(){{
            put(Group.WOLF.getType(), new WolfFactory());
            put(Group.PYTHON.getType(), new PythonFactory());
            put(Group.FOX.getType(), new FoxFactory());
            put(Group.BEAR.getType(), new BearFactory());
            put(Group.EAGLE.getType(), new EagleFactory());
            put(Group.HORSE.getType(), new HorseFactory());
            put(Group.DEER.getType(), new DeerFactory());
            put(Group.RABBIT.getType(), new RabbitFactory());
            put(Group.MOUSE.getType(), new MouseFactory());
            put(Group.GOAT.getType(), new GoatFactory());
            put(Group.SHEEP.getType(), new SheepFactory());
            put(Group.BOAR.getType(), new BoarFactory());
            put(Group.BUFFALO.getType(), new BuffaloFactory());
            put(Group.DUCK.getType(), new DuckFactory());
            put(Group.CATERPILLAR.getType(), new CaterpillarFactory());
            put(Group.HERB.getType(), new HerbFactory());
        }};
        return Collections.unmodifiableMap(factories);
    }
    private Map<String, Organism> createPrototypes(){
        Map<String, Organism> result = new LinkedHashMap<>();

        for (Map.Entry<String, OrganismFactory> pair : factoryMap.entrySet()){
            String type = pair.getKey();
            Organism organism = pair.getValue().create(type);
            result.put(type, organism);
        }
        return Collections.unmodifiableMap(result);
    }

}
