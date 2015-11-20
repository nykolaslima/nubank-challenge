package br.com.nubank.infrastructure.server

import br.com.nubank.application.controller.socialNetwork.SocialNetworkController
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter

object TwitterServerMain extends TwitterServer

class TwitterServer extends HttpServer {

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[CommonFilters]
      .add[SocialNetworkController]
  }

}
