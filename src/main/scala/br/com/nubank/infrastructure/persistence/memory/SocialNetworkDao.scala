package br.com.nubank.infrastructure.persistence.memory

import javax.inject.{Inject, Singleton}

import br.com.nubank.domain.model.socialNetwork.{SocialNetwork, Vertex}
import br.com.nubank.domain.service.socialNetwork.SocialNetworkCalculator
import br.com.nubank.infrastructure.io.graph.GraphLoader

@Singleton
class SocialNetworkDao @Inject()(graphLoader: GraphLoader, socialNetworkCalculator: SocialNetworkCalculator) {

  val (graph, vertexes) = graphLoader.loadFrom("src/main/resources/edges")
  var socialNetwork = socialNetworkCalculator.calculate(vertexes.toList, graph)

  def get: SocialNetwork = {
    socialNetwork
  }

  def save(socialNetwork: SocialNetwork) = {
    this.socialNetwork = socialNetwork
  }

}
