package br.com.nubank.application.mapping.socialNetwork

import br.com.nubank.application.resource.socialNetwork.VertexResource
import br.com.nubank.domain.model.socialNetwork.Vertex

trait VertexResourceMapping {

  def toVertexResource(vertex: Vertex): VertexResource = {
    VertexResource(vertex.id, vertex.closenessCentrality)
  }

}
