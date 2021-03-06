package br.com.nubank.infrastructure.server

import javax.inject.Inject

import br.com.nubank.domain.service.socialNetwork.SocialNetworkService
import br.com.nubank.infrastructure.io.graph.GraphLoader
import br.com.nubank.infrastructure.persistence.memory.SocialNetworkDao
import com.twitter.finatra.utils.Handler

class SocialNetworkInitializer @Inject()(
                                          socialNetworkDao: SocialNetworkDao,
                                          graphLoader: GraphLoader,
                                          socialNetworkCalculator: SocialNetworkService) extends Handler {

  override def handle() = {
    graphLoader.loadFrom("src/main/resources/edges").map(result => {
      val (graph, vertexes) = result
      val socialNetwork = socialNetworkCalculator.calculate(vertexes, graph)
      socialNetworkDao.save(socialNetwork)
    })
  }

}
