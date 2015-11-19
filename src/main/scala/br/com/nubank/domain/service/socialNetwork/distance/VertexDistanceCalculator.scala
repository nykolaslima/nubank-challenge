package br.com.nubank.domain.service.socialNetwork.distance

import javax.inject.Singleton

import br.com.nubank.domain.model.socialNetwork.Vertex

@Singleton
class VertexDistanceCalculator {

  val Inf = 999

  def calculate(vertexQuantity: Int, graph: Map[Vertex, List[Vertex]]): Array[Array[Int]] = {
    val distanceMatrix = initDistanceMatrix(vertexQuantity, graph)

    for {
      k <- 0 until vertexQuantity
      i <- 0 until vertexQuantity
      j <- 0 until vertexQuantity
    } {
      if(shouldUpdateDistance(distanceMatrix, k, i, j)) {
        distanceMatrix(i)(j) = distanceMatrix(i)(k) + distanceMatrix(k)(j)
      }
    }

    distanceMatrix
  }

  private def initDistanceMatrix(vertexQuantity: Int, graph: Map[Vertex, List[Vertex]]): Array[Array[Int]] = {
    val distanceMatrix = Array.fill[Int](vertexQuantity, vertexQuantity)(Inf)

    for(i <- 0 until vertexQuantity) distanceMatrix(i)(i) = 0

    for {
      vertex <- graph.keys
      directConnections <- graph.get(vertex)
      directConnection <- directConnections
    } {
      distanceMatrix(vertex.id)(directConnection.id) = 1
      distanceMatrix(directConnection.id)(vertex.id) = 1
    }

    distanceMatrix
  }

  private def shouldUpdateDistance(distanceMatrix: Array[Array[Int]], k: Int, i: Int, j: Int): Boolean = {
    distanceMatrix(i)(k) + distanceMatrix(k)(j) < distanceMatrix(i)(j)
  }

}
