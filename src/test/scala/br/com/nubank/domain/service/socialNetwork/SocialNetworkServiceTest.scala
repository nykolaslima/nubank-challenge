package br.com.nubank.domain.service.socialNetwork

import br.com.nubank.domain.model.socialNetwork.{SocialNetwork, Vertex}
import br.com.nubank.domain.service.socialNetwork.closenessCentrality.ClosenessCentralityCalculator
import br.com.nubank.domain.service.socialNetwork.distance.VertexDistanceCalculator
import br.com.nubank.infrastructure.UnitSpec
import br.com.nubank.infrastructure.persistence.memory.SocialNetworkDao
import org.mockito.ArgumentCaptor
import org.mockito.Mockito._

class SocialNetworkServiceTest extends UnitSpec {

  var socialNetworkCalculator: SocialNetworkService = null
  var vertexDistanceCalculator: VertexDistanceCalculator = null
  var closenessCentralityCalculator: ClosenessCentralityCalculator = null
  var socialNetworkDao: SocialNetworkDao = null

  before {
    vertexDistanceCalculator = mock[VertexDistanceCalculator]
    closenessCentralityCalculator = mock[ClosenessCentralityCalculator]
    socialNetworkDao = mock[SocialNetworkDao]
    socialNetworkCalculator = new SocialNetworkService(vertexDistanceCalculator, closenessCentralityCalculator, socialNetworkDao)
  }

  it should "calculate social network for a graph" in {
    val graph = buildGraph
    val vertexes = buildVertexes
    val distanceMatrix = buildDistanceMatrix
    val vertexesWithCloseness = buildVertexesWithCloseness
    when(vertexDistanceCalculator.calculate(vertexes.size, graph)).thenReturn(distanceMatrix)
    when(closenessCentralityCalculator.calculate(distanceMatrix, vertexes)).thenReturn(vertexesWithCloseness)

    val socialNetwork = socialNetworkCalculator.calculate(vertexes, graph)

    socialNetwork.distanceMatrix.shouldEqual(distanceMatrix)
    socialNetwork.vertexes.shouldEqual(vertexesWithCloseness)
  }

  it should "add edge to social network" in {
    val socialNetwork = buildSocialNetwork
    when(socialNetworkDao.get).thenReturn(socialNetwork)
    val distanceMatrix = Array.fill[Int](4, 4)(0)
    when(vertexDistanceCalculator.calculate(4, buildExpectedGraph)).thenReturn(distanceMatrix)
    val expectedVertexes = Set(Vertex(0), Vertex(1), Vertex(2), Vertex(3))
    when(closenessCentralityCalculator.calculate(distanceMatrix, expectedVertexes)).thenReturn(expectedVertexes)

    socialNetworkCalculator.addEdge(Vertex(2), Vertex(3))

    val captor = ArgumentCaptor.forClass(classOf[SocialNetwork])
    verify(socialNetworkDao).save(captor.capture())
    verify(vertexDistanceCalculator).calculate(4, buildExpectedGraph)
    verify(closenessCentralityCalculator).calculate(distanceMatrix, expectedVertexes)

    val newSocialNetwork = captor.getValue
    newSocialNetwork.graph.shouldEqual(buildExpectedGraph)
    newSocialNetwork.vertexes.shouldEqual(expectedVertexes)
  }

  def buildExpectedGraph: Map[Vertex, Set[Vertex]] = {
    val expectedGraph = Map(
      Vertex(0) -> Set(Vertex(1)),
      Vertex(1) -> Set(Vertex(2)),
      Vertex(2) -> Set(Vertex(3))
    )
    expectedGraph
  }

  def buildExpectedDistanceMatrix: Array[Array[Int]] = {
    val expectedDistanceMatrix = Array.fill[Int](4, 4)(0)
    expectedDistanceMatrix(0)(1) = 1
    expectedDistanceMatrix(0)(2) = 2
    expectedDistanceMatrix(1)(0) = 1
    expectedDistanceMatrix(1)(2) = 1
    expectedDistanceMatrix(2)(0) = 2
    expectedDistanceMatrix(2)(1) = 1
    expectedDistanceMatrix(0)(3) = 3
    expectedDistanceMatrix(1)(3) = 2
    expectedDistanceMatrix(2)(3) = 1
    expectedDistanceMatrix(3)(3) = 0
    expectedDistanceMatrix
  }

  /*
      0 - 1 - 2
     */
  private def buildSocialNetwork: SocialNetwork = {
    SocialNetwork(buildDistanceMatrix, buildGraph, buildVertexes)
  }

  private def buildGraph: Map[Vertex, Set[Vertex]] = {
    Map(
      Vertex(0) -> Set(Vertex(1)),
      Vertex(1) -> Set(Vertex(2))
    )
  }

  private def buildVertexes: Set[Vertex] = {
    Set(Vertex(0), Vertex(1), Vertex(2))
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

  private def buildVertexesWithCloseness: Set[Vertex] = {
    Set(Vertex(0, 0.333), Vertex(1, 0.5), Vertex(2, 0.333))
  }

}
