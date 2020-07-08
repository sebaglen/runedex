# Setting up RuneLite
First, clone the RuneLite.net repository.

`git clone https://github.com/runelite/runelite`

### Building the runelite client
In this example we will be using Maven. 
Make sure you have Maven installed and set to your `%PATH%` variable. 

`cd runelite/runelite-client`

`mvn install -DSkipTests`

### Launching the runelite client
Navigate to the `target` directory by running
`cd runelite-client/target` 
from the `runelite` project root.

Execute `client-x.x.xx-SNAPSHOT-shaded.jar` to launch runelite.

# Installing RuneDex
Navigate to the `plugins` directory by running
`cd runelite-client/src/main/java/net/runelite/client/plugins` 
from the `runelite` project root.

Clone this repository into the `plugins` folder.
`git clone git@github.com:sebaglen/runedex.git`

Build the RuneLite client, and launch it!

