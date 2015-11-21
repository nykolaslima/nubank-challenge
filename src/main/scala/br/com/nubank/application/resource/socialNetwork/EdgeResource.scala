package br.com.nubank.application.resource.socialNetwork

import com.twitter.finatra.validation.Min

case class EdgeResource(@Min(0) v1: Int, @Min(0) v2: Int) {

}
