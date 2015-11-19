package br.com.nubank.infrastructure

import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

abstract class UnitSpec extends FlatSpec with Matchers with BeforeAndAfter with MockitoSugar {

}
