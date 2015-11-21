package br.com.nubank.domain.model.socialNetwork

case class Vertex(id: Int, closenessCentrality: Double = 0) {

  override def hashCode(): Int = {
    id.hashCode()
  }

  override def equals(o: Any) = o match {
    case that: Vertex => that.id.equals(id)
    case _ => false
  }

}
