package br.com.nubank.infrastructure.server.initialization

import javax.inject.Inject

import br.com.nubank.domain.service.socialNetwork.SocialNetworkCalculator
import br.com.nubank.infrastructure.io.graph.GraphLoader
import br.com.nubank.infrastructure.persistence.memory.SocialNetworkDao
import com.twitter.finatra.utils.Handler

class SocialNetworkInitialize @Inject()
(socialNetworkCalculator: SocialNetworkCalculator,
 socialNetworkDao: SocialNetworkDao,
  graphLoader: GraphLoader) extends Handler {

  override def handle = {
    val (graph, vertexes) = graphLoader.loadFrom("src/main/resources/edges")

    val socialNetwork = socialNetworkCalculator.calculate(vertexes.toList, graph)
    socialNetworkDao.save(socialNetwork)
  }

}
