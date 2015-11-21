package br.com.nubank.infrastructure.persistence.memory

import javax.inject.{Inject, Singleton}

import br.com.nubank.domain.model.socialNetwork.{SocialNetwork, Vertex}
import br.com.nubank.domain.service.socialNetwork.SocialNetworkService
import br.com.nubank.infrastructure.io.graph.GraphLoader

@Singleton
class SocialNetworkDao {

  var socialNetwork: SocialNetwork = null

  def get: SocialNetwork = {
    socialNetwork
  }

  def save(socialNetwork: SocialNetwork) = {
    this.socialNetwork = socialNetwork
  }

}
