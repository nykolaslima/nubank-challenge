package br.com.nubank.infrastructure

import br.com.nubank.infrastructure.server.TwitterServer
import com.twitter.finatra.http.test.{EmbeddedHttpServer, HttpTest}
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import org.scalatest.mock.MockitoSugar

abstract class IntegrationSpec extends FeatureTest with HttpTest with MockitoSugar with Mockito {

  override protected val server = new EmbeddedHttpServer(new TwitterServer)

}
