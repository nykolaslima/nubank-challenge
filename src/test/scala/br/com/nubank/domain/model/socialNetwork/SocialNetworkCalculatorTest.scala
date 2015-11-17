package br.com.nubank.domain.model.socialNetwork

import br.com.nubank.infrastructure.UnitSpec

class SocialNetworkCalculatorTest extends UnitSpec {

  val shortestPathCalculator = new SocialNetworkCalculator()

  it should "calculate the shortest distance between two vertex connected directly" in {
    val vertexQuantity = 2;
    val v0 = Vertex(0)
    val v1 = Vertex(1)
    val graph = Map(v0 -> List(v1))

    val socialNetwork = shortestPathCalculator.calculate(vertexQuantity, graph)

    socialNetwork.distanceBetween(v0, v1).shouldEqual(1)
  }

  it should "calculate the shortest distance between a vertex and it's connections that are connected directly" in {
    val vertexQuantity = 3;
    val graph = Map(Vertex(0) -> List(Vertex(1), Vertex(2)))

    val socialNetwork = shortestPathCalculator.calculate(vertexQuantity, graph)

    socialNetwork.distanceBetween(Vertex(0), Vertex(1)).shouldEqual(1)
    socialNetwork.distanceBetween(Vertex(0), Vertex(2)).shouldEqual(1)
  }

  /*
    0 - 1   3
     \- 2 -/
  */
  it should "calculate shortest path with indirect connected vertex" in {
    val vertexQuantity = 4
    val graph = Map(Vertex(0) -> List(Vertex(1), Vertex(2)), Vertex(2) -> List(Vertex(3)))

    val socialNetwork = shortestPathCalculator.calculate(vertexQuantity, graph)

    socialNetwork.distanceBetween(Vertex(0), Vertex(1)).shouldEqual(1)
    socialNetwork.distanceBetween(Vertex(0), Vertex(2)).shouldEqual(1)
    socialNetwork.distanceBetween(Vertex(2), Vertex(3)).shouldEqual(1)
    socialNetwork.distanceBetween(Vertex(0), Vertex(3)).shouldEqual(2)
    socialNetwork.distanceBetween(Vertex(1), Vertex(3)).shouldEqual(3)
  }

  /*
    0 - 1 - - - |
     \- 2 - 3 - 4
  */
  it should "calculate shortest path with two possible paths" in {
    val vertexQuantity = 5
    val graph = Map(
      Vertex(0) -> List(Vertex(1), Vertex(2)),
      Vertex(1) -> List(Vertex(4)),
      Vertex(2) -> List(Vertex(3)),
      Vertex(3) -> List(Vertex(4))
    )

    val socialNetwork = shortestPathCalculator.calculate(vertexQuantity, graph)

    socialNetwork.distanceBetween(Vertex(0), Vertex(1)).shouldEqual(1)
    socialNetwork.distanceBetween(Vertex(0), Vertex(2)).shouldEqual(1)
    socialNetwork.distanceBetween(Vertex(0), Vertex(3)).shouldEqual(2)
    socialNetwork.distanceBetween(Vertex(0), Vertex(4)).shouldEqual(2)
    socialNetwork.distanceBetween(Vertex(1), Vertex(2)).shouldEqual(2)
    socialNetwork.distanceBetween(Vertex(1), Vertex(3)).shouldEqual(2)
    socialNetwork.distanceBetween(Vertex(1), Vertex(4)).shouldEqual(1)
    socialNetwork.distanceBetween(Vertex(2), Vertex(3)).shouldEqual(1)
    socialNetwork.distanceBetween(Vertex(2), Vertex(4)).shouldEqual(2)
  }

  /*
    0 - 1 - 2
    | - - - |
  */
  it should "calculate shortest path with cyclic connection" in {
    val vertexQuantity = 3
    val graph = Map(
      Vertex(0) -> List(Vertex(1)),
      Vertex(1) -> List(Vertex(2)),
      Vertex(2) -> List(Vertex(0))
    )

    val socialNetwork = shortestPathCalculator.calculate(vertexQuantity, graph)

    socialNetwork.distanceBetween(Vertex(0), Vertex(1)).shouldBe(1)
    socialNetwork.distanceBetween(Vertex(0), Vertex(2)).shouldBe(1)
    socialNetwork.distanceBetween(Vertex(1), Vertex(2)).shouldBe(1)
  }

}
