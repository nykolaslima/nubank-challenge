package br.com.nubank.domain.service.socialNetwork

import javax.inject.{Inject, Singleton}

import br.com.nubank.domain.model.socialNetwork.{SocialNetwork, Vertex}
import br.com.nubank.domain.service.socialNetwork.closenessCentrality.ClosenessCentralityCalculator
import br.com.nubank.domain.service.socialNetwork.distance.VertexDistanceCalculator
import br.com.nubank.infrastructure.persistence.memory.SocialNetworkDao

@Singleton
class SocialNetworkService @Inject()(vertexDistanceCalculator: VertexDistanceCalculator,
                                        closenessCentralityCalculator: ClosenessCentralityCalculator,
                                         socialNetworkDao: SocialNetworkDao) {

  def calculate(vertexes: Set[Vertex], graph: Map[Vertex, Set[Vertex]]): SocialNetwork = {
    val distanceMatrix = vertexDistanceCalculator.calculate(vertexes.size, graph)
    val vertexesWithClosenessCentrality = closenessCentralityCalculator.calculate(distanceMatrix, vertexes)

    SocialNetwork(distanceMatrix, graph, vertexesWithClosenessCentrality)
  }

  def addEdge(v1: Vertex, v2: Vertex) = {
    val socialNetwork = socialNetworkDao.get
    val vertexesConnected = socialNetwork.graph.get(v1).getOrElse(Set()) + v2
    val newGraph = socialNetwork.graph + (v1 -> vertexesConnected)
    val newVertexes = socialNetwork.vertexes + v1 + v2

    socialNetworkDao.save(calculate(newVertexes, newGraph))
  }

}
