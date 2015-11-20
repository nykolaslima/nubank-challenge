package br.com.nubank.infrastructure.io.graph

import javax.inject.Singleton

import br.com.nubank.domain.model.socialNetwork.Vertex

import scala.io.Source

@Singleton
class GraphLoader {

  def loadFrom(filePath: String): (Map[Vertex, List[Vertex]], Set[Vertex]) = {
    val (iterator1, iterator2) = Source.fromFile(filePath).getLines()
      .map(line => {
        val v1 :: v2 :: tail = line.split(" ").toList.map(_.toInt)
        (Vertex(v1), Vertex(v2))
      }
      ).duplicate

    val graph = iterator1.foldLeft[Map[Vertex, List[Vertex]]](Map())(toGraph)
    val vertexes = iterator2.foldLeft[Set[Vertex]](Set())(toVertexes)

    (graph, vertexes)
  }

  private def toGraph(graph: Map[Vertex, List[Vertex]], entry: (Vertex, Vertex)): Map[Vertex, List[Vertex]] = {
    val (v1, v2) = entry
    graph + (v1 -> (graph.getOrElse(v1, List()) :+ v2))
  }

  private def toVertexes(vertexes: Set[Vertex], entry: (Vertex, Vertex)): Set[Vertex] = {
    val (v1, v2) = entry
    vertexes ++ Set(v1, v2)
  }

}
