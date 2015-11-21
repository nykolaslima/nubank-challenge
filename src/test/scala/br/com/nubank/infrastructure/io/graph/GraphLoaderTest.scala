package br.com.nubank.infrastructure.io.graph

import br.com.nubank.domain.model.socialNetwork.Vertex
import br.com.nubank.infrastructure.UnitSpec

class GraphLoaderTest extends UnitSpec {

  val graphLoader: GraphLoader = new GraphLoader()

  /*
    0 - 1 - - - |
     \- 2 - 3 - 4
  */
  it should "load graph and vertexes from file" in {
    val (graph, vertexes) = graphLoader.loadFrom("src/test/resources/edges")

    graph(Vertex(0)).shouldEqual(Set(Vertex(1), Vertex(2)))
    graph(Vertex(1)).shouldEqual(Set(Vertex(4)))
    graph(Vertex(2)).shouldEqual(Set(Vertex(3)))
    graph(Vertex(3)).shouldEqual(Set(Vertex(4)))

    vertexes should contain (Vertex(0))
    vertexes should contain (Vertex(1))
    vertexes should contain (Vertex(2))
    vertexes should contain (Vertex(3))
    vertexes should contain (Vertex(4))
  }

}
