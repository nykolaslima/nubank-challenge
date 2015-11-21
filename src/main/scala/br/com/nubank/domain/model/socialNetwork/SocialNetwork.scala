package br.com.nubank.domain.model.socialNetwork

case class SocialNetwork(distanceMatrix: Array[Array[Int]], graph: Map[Vertex, Set[Vertex]], vertexes: Set[Vertex]) {

  def rank: List[Vertex] = {
    vertexes.toList.sortBy(-_.closenessCentrality)
  }

}
