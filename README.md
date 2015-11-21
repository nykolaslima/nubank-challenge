Nubank Challenge
=====

The project was built in Scala using [Finatra](http://twitter.github.io/finatra/index.html) in the HTTP layer and [SBT](http://www.scala-sbt.org/) as build tool.  

The shortest path algorithm used was [Floyd–Warshall](https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm). I preferred this algorithm over the [Dijkstra](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm) because it is more efficient when we need to find shortest path between all pairs.

### Running the project

`sbt run` and server will start in port `8888`

### REST endpoints

There are 3 defined endpoints:  
 `GET     /social-network/rank` - list the social network rank based on closeness centrality  
 `POST    /social-network/edges` - add a new edge into social network  
 `POST    /social-network/vertexes/:id/actions/fraudulent` - mark a vertex as fraudulent

### Running tests

`sbt test`
