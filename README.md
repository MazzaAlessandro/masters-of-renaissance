![Head](/deliverables/Misc/doc-head.jpg)
# Masters of Renaissance
Masters of the Renaissance is a new engine building game that recreates the atmosphere of Lorenzo il Magnifico with simple rules and a limited duration.
On your turn you may acquire a development card, take resources from the market or activate development cards on your board. Each development card allow you to transform resources by moving them from your limited depots to the unlimited bank space; in this way you may accumulate many resource to acquire the most powerful cards.
As in Lorenzo you will have to follow the requests of your leaders to have access to their interested powers.
The game is created by [Cranio Creations srl](https://www.craniointernational.com)
[Buy the board game](https://craniointernational.com/products/masters-of-renaissance/)
### COPYRIGHT DISCLAIMER
The images, names used, the rules of the game and all contents except the source code belong to [Cranio Creations srl](https://www.craniointernational.com)
### Implemented features
The project consists of the implementation of the board game Masters of Renaissance as a distributed system composed by a server, handling multiple matches, and multiple clients who can connect to the server to play matches either with other players or by themselves (facing an automated opponent and following different rules).
This was achieved by adopting the MVC pattern (Model-View-Controller) and using remote connection through sockets.
The game can be played either with command lines through a terminal (CLI) or a special graphic interface (GUI).
### Advanced Features
1. **Multiple matches**: the server is capable of handling different matches at the same time. Whenever a player joins, he can either create a new match or join an existing one.
2. **Local Match**: a client can run a match against the automated opponent even without the server.
3. **Disconnections Management**: if a client disconnects during the game it is possible for them to reconnect and continue the game. While a client is disconnected, their turn is skipped, and if every client get disconnected the game will resume once everyone reconnects.
### Compilation and packaging
You can build the game directly from the source. Clone the repository and run the following command.
```
mvn clean package
```
### How to run
#### Start the server
```
java -jar Server.jar -port PORT_NUMBER
```
This will set the server port to a PORT_NUMBER, for example running the command “java -jar Server.jar -port 2345” will set the server port to “2345”.
Typing the command without the “-port PORT_NUMBER” part will launch the server with the default port “1235”.
#### Start the Client (with server running)
**In GUI:**
```
java -jar ClientMultiplayer.jar
```
**In CLI:**
```
java -jar ClientMultiplayer.jar -cli
```
#### Start the Client for the local play FA (without the server)
**In GUI:** 
```
java -jar ClientSolo.jar
```
**In CLI:** 
``` 
java -jar ClientSolo.jar -cli
```
It should be noted that the CLI uses many Unicode characters and ANSI codes, as such it may not be displayed correctly on the Windows terminal.
### General notes
The structure of the whole system is displayed by different UMLs: beside the “complete.png”, which gives a detailed look to the entirety of the system, we also decided to split it in various UMLs to maintain its readability
### Group Members
* [Mazza Alessandro] (https://github.com/MazzaAlessandro)
* [Marinotto Davide] (https://github.com/mdavide99)
* [Gerometta Giulia] (https://github.com/giuliagerometta)

