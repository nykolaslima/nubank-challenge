package br.com.nubank.controller.socialNetwork

import br.com.nubank.application.resource.socialNetwork.EdgeResource
import br.com.nubank.domain.model.socialNetwork.Vertex
import br.com.nubank.infrastructure.IntegrationSpec
import br.com.nubank.infrastructure.io.graph.GraphLoader
import com.google.inject.testing.fieldbinder.Bind
import com.twitter.finagle.http.Status

class SocialNetworkControllerTest extends IntegrationSpec {

  /*
    0 - 1 - 2 - 3
        |
        4
  */
  @Bind val graphLoader = smartMock[GraphLoader]
  val graph = Map(
    Vertex(0) -> Set(Vertex(1)),
    Vertex(1) -> Set(Vertex(2), Vertex(4)),
    Vertex(2) -> Set(Vertex(3))
  )
  val vertexes = Set(Vertex(0), Vertex(1), Vertex(2), Vertex(3), Vertex(4))
  graphLoader.loadFrom("src/main/resources/edges") returns ((graph, vertexes))

  "SocialNetworkController" should {
    "list vertexes rank" in {
      val expectedResult = List(Vertex(1, 0.2), Vertex(2, 0.166666666666666), Vertex(0, 0.125), Vertex(4, 0.125), Vertex(3, 0.111111111111111))

      server.httpGet(
        path = "/social-network/rank",
        andExpect = Status.Ok,
        withJsonBody = toJson(expectedResult)
      )
    }

    "add edge to social network" in {
      val edge = EdgeResource(3, 5)

      server.httpPost(
        path = "/social-network/edges",
        postBody = toJson(edge),
        andExpect = Status.Created
      )

      val expectedResult = List(Vertex(1, 0.125), Vertex(2, 0.125), Vertex(3, 0.1), Vertex(0, 0.083333333333333), Vertex(4, 0.083333333333333), Vertex(5, 0.071428571428571))
      server.httpGet(
        path = "/social-network/rank",
        andExpect = Status.Ok,
        withJsonBody = toJson(expectedResult)
      )
    }
  }

}
