Island Simulation v1.0

The purpose of this project is to program a model of an island with adjustable parameters, 
consisting of an array of locations (for example, 100x20 cells). 
These locations will be filled with vegetation and animals. Animals can:

- Eat plants and/or other animals (if suitable food is available in their location),
- Move to neighboring locations,
- Reproduce (if a pair is present in their location),
- Die from hunger or be eaten.

OOP

Creating diversity among animals, it's essential to maximize the use of OOP (Object-Oriented Programming): 
all species will derive from a single abstract class Animal, which will contain behavior common to all animals. 
If specific animals have unique feeding habits, reproduction, movement, etc., then methods of the Animal class 
will need to be overridden for them.

Here's what needs to be done:

1. Create a class hierarchy:
   - Carnivores: Wolf, Python, Fox, Bear, Eagle
   - Herbivores: Horse, Deer, Rabbit, Mouse, Goat, Sheep, Boar, Buffalo, Duck, Caterpillar
   - Plants: Herb

2. The animal should have methods: eat, reproduce, choose a direction for movement.

3. In the herbivore and carnivore classes, you can implement the eat method. However, note that there is a herbivore, the duck, that eats caterpillars.

4. In specific classes of each species, all methods can be further developed according to the animal's characteristics.

5. At least 10 species of herbivores and 5 species of carnivores (listed in item 1) should be created.

Concurrency

Certainly, you could write the entire program in a single thread using loops. However, we need to practically 
work with concurrency, so it's essential to use threads and thread pools. One scheduled pool will be used 
to run tasks such as scheduling plant growth, managing animal life cycles, and outputting system statistics. 
Alternatively, you can integrate statistics into tasks with plants or animals. Within the animal life cycle task, 
you can use another thread pool—this one regular. Decide autonomously which tasks to assign to this pool.

Mandatory task components:

- Animal hierarchy (OOP)
- Animal behavior
- Concurrency
- Island state statistics output at each timestamp (to the console)

Optional task components:

- Centralize parameters for easy simulation management
- Graphics instead of console statistics, which could be pseudo-graphics in the console or JavaFX, Swing...
- Introduce other factors influencing the simulation:
    - More animal species
    - Different plant species
    - Custom behavior for animal groups (e.g., wolves hunting and moving in packs)
    - Terrain features, including rivers that restrict certain animal movements

About parameters (if you choose to implement):

To conveniently adjust various simulation parameters (island size, maximum number of plants/animals per cell, 
probability of movement for different animal species, offspring count for different animal species, etc.) 
upon program launch, these parameters should be consolidated into a separate class or location. 

You should be able to modify the following parameters:

- Island size
- Simulation timestamp duration
- Initial number of each animal species at simulation start
- Simulation stop condition (e.g., all animals have died)
- Offspring count for each animal species

Unicode (if implementing pseudo-graphics):

For depicting animals, you can use Unicode symbols: 🐃, 🐻, 🐎, 🦌, 🐗, 🐑, 🐐, 🐺, 🐍, 🦊, 🦅, 🐇, 🦆, 🐁, 🐛.

Additional requirements

- Architecture through Services
- Apply Lombok
- Apply Jackson (serialization/deserialization (YAML/JSON))
- The suggested hierarchy from the task is not mandatory 
- Apply Factory Pattern
- Do not implement GUI (creating an interface; simply console output for statistics is sufficient).
- The simulation duration can be implemented flexibly (at minimum, iterate through the entire game field
and ensure all animals perform eating, reproduction, movement).