package br.com.nubank.infrastructure

import br.com.nubank.infrastructure.server.TwitterServer
import com.twitter.finatra.http.test.{EmbeddedHttpServer, HttpTest}
import com.twitter.finatra.json.FinatraObjectMapper
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar

abstract class IntegrationSpec extends FeatureTest with HttpTest with MockitoSugar with Mockito with BeforeAndAfter {

  override protected val server = new EmbeddedHttpServer(new TwitterServer)

  def toJson(any: Any): String = {
    lazy val mapper = server.injector.instance[FinatraObjectMapper]
    mapper.writeValueAsString(any)
  }

}
