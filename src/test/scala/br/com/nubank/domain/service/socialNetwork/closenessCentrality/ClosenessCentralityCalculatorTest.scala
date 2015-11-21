package br.com.nubank.domain.service.socialNetwork.closenessCentrality

import br.com.nubank.domain.model.socialNetwork.Vertex
import br.com.nubank.infrastructure.UnitSpec

class ClosenessCentralityCalculatorTest extends UnitSpec {

  val closenessCentralityCalculator = new ClosenessCentralityCalculator()

  it should "calculate closeness centrality" in {
    val calculatedVertexes: Set[Vertex] = closenessCentralityCalculator.calculate(buildDistanceMatrix, buildVertexes)

    calculatedVertexes.find(v => v.id == 0).get.closenessCentrality.shouldEqual(0.125)
    calculatedVertexes.find(v => v.id == 1).get.closenessCentrality.shouldEqual(0.2)
    calculatedVertexes.find(v => v.id == 2).get.closenessCentrality.shouldEqual(0.166666666666666)
    calculatedVertexes.find(v => v.id == 3).get.closenessCentrality.shouldEqual(0.111111111111111)
    calculatedVertexes.find(v => v.id == 4).get.closenessCentrality.shouldEqual(0.125)
  }

  it should "calculate closeness centrality with fraudulent vertex" in {
    val distanceMatrix = buildDistanceMatrix
    val vertexes: Set[Vertex] = buildVertexes - Vertex(3) + Vertex(id = 3, fraudulent = true)

    val calculatedVertexes: Set[Vertex] = closenessCentralityCalculator.calculate(distanceMatrix, vertexes)

    calculatedVertexes.find(v => v.id == 0).get.closenessCentrality.shouldEqual(0.109375)
    calculatedVertexes.find(v => v.id == 1).get.closenessCentrality.shouldEqual(0.15)
    calculatedVertexes.find(v => v.id == 2).get.closenessCentrality.shouldEqual(0.083333333333333)
    calculatedVertexes.find(v => v.id == 3).get.closenessCentrality.shouldEqual(0)
    calculatedVertexes.find(v => v.id == 4).get.closenessCentrality.shouldEqual(0.109375)
  }

  /*
    0 - 1 - 2 - 3
        |
        4
   */
  def buildDistanceMatrix: Array[Array[Int]] = {
    val distanceMatrix = Array.fill[Int](5, 5)(0)
    distanceMatrix(0)(0) = 0
    distanceMatrix(0)(1) = 1
    distanceMatrix(0)(2) = 2
    distanceMatrix(0)(3) = 3
    distanceMatrix(0)(4) = 2

    distanceMatrix(1)(0) = 1
    distanceMatrix(1)(1) = 0
    distanceMatrix(1)(2) = 1
    distanceMatrix(1)(3) = 2
    distanceMatrix(1)(4) = 1

    distanceMatrix(2)(0) = 2
    distanceMatrix(2)(1) = 1
    distanceMatrix(2)(2) = 0
    distanceMatrix(2)(3) = 1
    distanceMatrix(2)(4) = 2

    distanceMatrix(3)(0) = 3
    distanceMatrix(3)(1) = 2
    distanceMatrix(3)(2) = 1
    distanceMatrix(3)(3) = 0
    distanceMatrix(3)(4) = 3

    distanceMatrix(4)(0) = 2
    distanceMatrix(4)(1) = 1
    distanceMatrix(4)(2) = 2
    distanceMatrix(4)(3) = 3
    distanceMatrix(4)(4) = 0

    distanceMatrix
  }

  def buildVertexes: Set[Vertex] = {
    val vertexes = Set(Vertex(0), Vertex(1), Vertex(2), Vertex(3), Vertex(4))
    vertexes
  }

}
