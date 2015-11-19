package br.com.nubank.domain.model.socialNetwork

case class SocialNetwork(distanceMatrix: Array[Array[Int]], graph: Map[Vertex, List[Vertex]], vertexes: List[Vertex]) {

}
