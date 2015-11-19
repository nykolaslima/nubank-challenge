package br.com.nubank.domain.service.socialNetwork

import javax.inject.{Inject, Singleton}

import br.com.nubank.domain.model.socialNetwork.{SocialNetwork, Vertex}
import br.com.nubank.domain.service.socialNetwork.closenessCentrality.ClosenessCentralityCalculator
import br.com.nubank.domain.service.socialNetwork.distance.VertexDistanceCalculator

@Singleton
class SocialNetworkCalculator @Inject()(vertexDistanceCalculator: VertexDistanceCalculator, closenessCentralityCalculator: ClosenessCentralityCalculator) {

  def calculate(vertexes: List[Vertex], graph: Map[Vertex, List[Vertex]]): SocialNetwork = {
    val distanceMatrix = vertexDistanceCalculator.calculate(vertexes.size, graph)
    val vertexesWithClosenessCentrality = closenessCentralityCalculator.calculate(distanceMatrix, vertexes)

    SocialNetwork(distanceMatrix, graph, vertexesWithClosenessCentrality)
  }

}
