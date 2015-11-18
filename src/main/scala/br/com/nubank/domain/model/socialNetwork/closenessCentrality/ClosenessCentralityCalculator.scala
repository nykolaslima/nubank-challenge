package br.com.nubank.domain.model.socialNetwork.closenessCentrality

import br.com.nubank.domain.model.socialNetwork.Vertex

class ClosenessCentralityCalculator {

  def calculate(distanceMatrix: Array[Array[Int]], vertexes: List[Vertex]): List[Vertex] = {
    vertexes.map(vertex => {
      val closeness = BigDecimal(1.0) / calculateFarness(distanceMatrix, vertexes.size, vertex)
      Vertex(vertex.id, closeness.setScale(3, BigDecimal.RoundingMode.DOWN).toDouble)
    })
  }

  private def calculateFarness(distanceMatrix: Array[Array[Int]], vertexesSize: Int, vertex: Vertex): Int = {
    (0 until vertexesSize).map(distanceMatrix(vertex.id)(_)).sum
  }

}
