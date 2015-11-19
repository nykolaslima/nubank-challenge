package br.com.nubank.infrastructure.persistence.memory

import javax.inject.Singleton

import br.com.nubank.domain.model.socialNetwork.{SocialNetwork, Vertex}

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
