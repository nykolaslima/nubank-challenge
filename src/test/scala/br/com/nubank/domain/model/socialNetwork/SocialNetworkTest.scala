package br.com.nubank.domain.model.socialNetwork

import br.com.nubank.infrastructure.UnitSpec

class SocialNetworkTest extends UnitSpec {

  it should "rank vertexes based on closeness centrality" in {
    val rankedVertexes: List[Vertex] = SocialNetwork(buildDistanceMatrix, buildGraph, buildVertexesWithCloseness).rank

    rankedVertexes(0).shouldEqual(Vertex(1, 0.5))
    rankedVertexes(1).shouldEqual(Vertex(2, 0.333))
    rankedVertexes(2).shouldEqual(Vertex(0, 0.332))
  }

  private def buildGraph: Map[Vertex, List[Vertex]] = {
    Map(
      Vertex(0) -> List(Vertex(1)),
      Vertex(1) -> List(Vertex(2))
    )
  }

  private def buildDistanceMatrix: Array[Array[Int]] = {
    val distanceMatrix = Array.fill[Int](3, 3)(0)
    distanceMatrix(0)(1) = 1
    distanceMatrix(0)(2) = 2
    distanceMatrix(1)(0) = 1
    distanceMatrix(1)(2) = 1
    distanceMatrix(2)(0) = 2
    distanceMatrix(2)(1) = 1

    distanceMatrix
  }

  private def buildVertexesWithCloseness: List[Vertex] = {
    List(Vertex(0, 0.332), Vertex(1, 0.5), Vertex(2, 0.333))
  }

}
