package br.com.nubank.application.controller.socialNetwork

import javax.inject.{Singleton, Inject}

import br.com.nubank.application.mapping.socialNetwork.VertexResourceMapping
import br.com.nubank.application.resource.socialNetwork.EdgeResource
import br.com.nubank.domain.model.socialNetwork.Vertex
import br.com.nubank.domain.service.socialNetwork.SocialNetworkService
import br.com.nubank.infrastructure.persistence.memory.SocialNetworkDao
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

@Singleton
class SocialNetworkController @Inject()(socialNetworkDao: SocialNetworkDao, socialNetworkService: SocialNetworkService)
  extends Controller with VertexResourceMapping {

  get("/social-network/rank") { request: Request =>
    socialNetworkDao.get.rank.map(toVertexResource)
  }

  post("/social-network/edges") { edgeResource: EdgeResource =>
    socialNetworkService.addEdge(Vertex(edgeResource.v1), Vertex(edgeResource.v2))
    response.created
  }

  post("/social-network/vertexes/:id/actions/fraudulent") { request: Request =>
    socialNetworkService.markFraudulent(Vertex(request.params("id").toInt))
    response.ok
  }

}
