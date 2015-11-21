package br.com.nubank.domain.service.socialNetwork.closenessCentrality

import javax.inject.Singleton

import br.com.nubank.domain.model.socialNetwork.Vertex

@Singleton
class ClosenessCentralityCalculator {

  def calculate(distanceMatrix: Array[Array[Int]], vertexes: Set[Vertex]): Set[Vertex] = {
    val vertexesWithClosenessCentrality = vertexes.map(vertex => {
      val closeness = BigDecimal(1.0) / calculateFarness(distanceMatrix, vertexes.size, vertex)
      vertex.copy(closenessCentrality = closeness.setScale(15, BigDecimal.RoundingMode.DOWN).toDouble)
    })

    val fraudulentVertexes: Set[Vertex] = vertexesWithClosenessCentrality
      .filter(_.fraudulent)
      .flatMap(fraudulentVertex =>
        (0 until vertexes.size).map(i => {
          val vertexToBeRecalculated = vertexesWithClosenessCentrality.find(_.id == i).get
          val shortestPath = distanceMatrix(fraudulentVertex.id)(i)
          val closenessCentrality = vertexToBeRecalculated.closenessCentrality

          val recalculatedClosenessCentrality = shortestPath match {
            case 0 => 0
            case 1 => (BigDecimal(closenessCentrality) / 2).setScale(15, BigDecimal.RoundingMode.DOWN).toDouble
            case _ => calculateIndirectlyReferredByFraudulent(closenessCentrality, shortestPath)
          }

          vertexToBeRecalculated.copy(closenessCentrality = recalculatedClosenessCentrality)
        })
      )

    vertexesWithClosenessCentrality -- fraudulentVertexes ++ fraudulentVertexes
  }

  private def calculateFarness(distanceMatrix: Array[Array[Int]], vertexesSize: Int, vertex: Vertex): Int = {
    (0 until vertexesSize).map(distanceMatrix(vertex.id)(_)).sum
  }

  private def calculateIndirectlyReferredByFraudulent(closenessCentrality: Double, shortestPath: Int): Double = {
    val coefficient = (1 - Math.pow((1.0/2.0), shortestPath))
    (BigDecimal(closenessCentrality) * coefficient).setScale(15, BigDecimal.RoundingMode.DOWN).toDouble
  }

}
