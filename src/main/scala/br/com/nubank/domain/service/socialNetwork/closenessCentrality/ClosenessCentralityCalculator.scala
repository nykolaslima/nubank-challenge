package br.com.nubank.domain.service.socialNetwork.closenessCentrality

import javax.inject.Singleton

import br.com.nubank.domain.model.socialNetwork.Vertex

@Singleton
class ClosenessCentralityCalculator {

  def calculate(distanceMatrix: Array[Array[Int]], vertexes: List[Vertex]): List[Vertex] = {
    vertexes.map(vertex => {
      val closeness = BigDecimal(1.0) / calculateFarness(distanceMatrix, vertexes.size, vertex)
      Vertex(vertex.id, closeness.setScale(15, BigDecimal.RoundingMode.DOWN).toDouble)
    })
  }

  private def calculateFarness(distanceMatrix: Array[Array[Int]], vertexesSize: Int, vertex: Vertex): Int = {
    (0 until vertexesSize).map(distanceMatrix(vertex.id)(_)).sum
  }

}
