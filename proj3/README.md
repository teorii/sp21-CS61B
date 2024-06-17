# Design Document

### Classes & Data Structures
**Player.class:**

**Rooms.class:** instance variables: WIDTH, HEIGHT; uses top left corner for placement on the empty field.

**Other Variables:** Starting point: Randomly generated coordinate in order to signify where the start of the hallways, also allows us to have  a base point for room construction;

**Room List:** a list of Room objects

### Algorithms & Persistance
**Room generation algorithm:** randomly create a room with random width and height and a random placement.

**Room placement method:** given a Room object, makes a room within the world. 

**tile placement method:** given x and y coordinates and a tile, places that tile in the given coordinates as long as the given tile is not a wall that will overwrite a floor

**Hallway making algorithm:** makes a hallway given x and y coordinates, a direction, and length

**Hallway placement algorithm:** given a list of Room objects, connects them with hallways

**Parsing algorithm:** from the "N#####S" given to us, we need an algorithm to correctly parse this seed for the world.

**N L Q algorithm:** Determines whether the first letter is N, L, or Q in order to determine what the given input is trying to do.


### Other stuff
**Interact with keyboard will call a function that renders the GUI.
**When new world, prompt user to enter seed, then call WorldGeneration.main
**Whatevet the mouse is hovering over should be 