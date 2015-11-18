package br.com.nubank.domain.model.socialNetwork

class ClosenessCentralityCalculator {

  def calculate(distanceMatrix: Array[Array[Int]], vertexes: List[Vertex]): List[Vertex] = {
    vertexes.map(vertex => {
      val closeness = BigDecimal(1.0) / calculateFarness(distanceMatrix, vertexes.size, vertex)
      Vertex(vertex.id, closeness.setScale(3, BigDecimal.RoundingMode.DOWN).toDouble)
    })
  }

  def calculateFarness(distanceMatrix: Array[Array[Int]], vertexesSize: Int, vertex: Vertex): Int = {
    val allDistances = for (i <- 0 until vertexesSize)
      yield distanceMatrix(vertex.id)(i)
    allDistances.sum / 1
  }
}
