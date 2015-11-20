package br.com.nubank.application.controller.socialNetwork

import javax.inject.{Singleton, Inject}

import br.com.nubank.application.mapping.socialNetwork.VertexResourceMapping
import br.com.nubank.infrastructure.persistence.memory.SocialNetworkDao
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

@Singleton
class SocialNetworkController @Inject()(socialNetworkDao: SocialNetworkDao)
  extends Controller with VertexResourceMapping {

  get("/social-network/rank") { request: Request =>
    socialNetworkDao.get.rank.map(toVertexResource)
  }

}
