package br.com.nubank.domain.model.socialNetwork

case class SocialNetwork(distanceMatrix: Array[Array[Int]], graph: Map[Vertex, List[Vertex]], vertexQuantity: Int) {

  def distanceBetween(v1: Vertex, v2: Vertex): Int = {
    distanceMatrix(v1.id)(v2.id)
  }

}
