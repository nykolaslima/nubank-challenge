package br.com.nubank.controller.socialNetwork

import br.com.nubank.domain.model.socialNetwork.Vertex
import br.com.nubank.infrastructure.IntegrationSpec
import br.com.nubank.infrastructure.io.graph.GraphLoader
import com.google.inject.testing.fieldbinder.Bind
import com.twitter.finagle.http.Status
import com.twitter.finatra.json.FinatraObjectMapper

class SocialNetworkControllerTest extends IntegrationSpec {

  /*
    0 - 1 - 2 - 3
        |
        4
  */
  @Bind val graphLoader = smartMock[GraphLoader]
  val graph = Map(
    Vertex(0) -> List(Vertex(1)),
    Vertex(1) -> List(Vertex(2), Vertex(4)),
    Vertex(2) -> List(Vertex(3))
  )
  val vertexes = Set(Vertex(0), Vertex(1), Vertex(2), Vertex(3), Vertex(4))
  graphLoader.loadFrom("src/main/resources/edges") returns ((graph, vertexes))

  "SocialNetworkController" should {
    "list vertexes rank" in {
      val expectedResult = List(Vertex(1, 0.2), Vertex(2, 0.166666666666666), Vertex(0, 0.125), Vertex(4, 0.125), Vertex(3, 0.111111111111111))
      val expectedJson = FinatraObjectMapper.create().writeValueAsString(expectedResult)

      server.httpGet(
        path = "/social-network/rank",
        andExpect = Status.Ok,
        withJsonBody = expectedJson
      )
    }
  }

}
