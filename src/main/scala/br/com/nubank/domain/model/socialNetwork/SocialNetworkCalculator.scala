package br.com.nubank.domain.model.socialNetwork

import br.com.nubank.domain.model.socialNetwork.closenessCentrality.ClosenessCentralityCalculator
import br.com.nubank.domain.model.socialNetwork.distance.VertexDistanceCalculator

class SocialNetworkCalculator ()(vertexDistanceCalculator: VertexDistanceCalculator, closenessCentralityCalculator: ClosenessCentralityCalculator) {

  def calculate(vertexes: List[Vertex], graph: Map[Vertex, List[Vertex]]): SocialNetwork = {
    val distanceMatrix = vertexDistanceCalculator.calculate(vertexes.size, graph)
    val vertexesWithClosenessCentrality = closenessCentralityCalculator.calculate(distanceMatrix, vertexes)

    SocialNetwork(distanceMatrix, graph, vertexesWithClosenessCentrality)
  }

}
