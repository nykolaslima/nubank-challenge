package br.com.nubank.domain.model.socialNetwork

import br.com.nubank.domain.model.socialNetwork.closenessCentrality.ClosenessCentralityCalculator
import br.com.nubank.domain.model.socialNetwork.distance.VertexDistanceCalculator
import br.com.nubank.infrastructure.UnitSpec
import org.mockito.Mockito._

class SocialNetworkCalculatorTest extends UnitSpec {

  var socialNetworkCalculator: SocialNetworkCalculator = null
  var vertexDistanceCalculator: VertexDistanceCalculator = null
  var closenessCentralityCalculator: ClosenessCentralityCalculator = null

  before {
    vertexDistanceCalculator = mock[VertexDistanceCalculator]
    closenessCentralityCalculator = mock[ClosenessCentralityCalculator]
    socialNetworkCalculator = new SocialNetworkCalculator()()()(vertexDistanceCalculator, closenessCentralityCalculator)
  }

  it should "calculate social network for a graph" in {
    val graph = Map(
      Vertex(0) -> List(Vertex(1)),
      Vertex(1) -> List(Vertex(2))
    )
    val vertexes = List(Vertex(0), Vertex(1), Vertex(2))
    val distanceMatrix = buildDistanceMatrix
    val vertexesWithCloseness = buildVertexesWithCloseness
    when(vertexDistanceCalculator.calculate(vertexes.size, graph)).thenReturn(distanceMatrix)
    when(closenessCentralityCalculator.calculate(distanceMatrix, vertexes)).thenReturn(vertexesWithCloseness)

    val socialNetwork: SocialNetwork = socialNetworkCalculator.calculate(vertexes, graph)

    socialNetwork.distanceMatrix.shouldEqual(distanceMatrix)
    socialNetwork.vertexes.shouldEqual(vertexesWithCloseness)
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
    List(Vertex(0, 0.333), Vertex(1, 0.5), Vertex(2, 0.333))
  }

}
