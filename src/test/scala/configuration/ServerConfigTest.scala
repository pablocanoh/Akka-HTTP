package configuration

import org.scalatest.FlatSpec

class ServerConfigTest extends FlatSpec {

  "ServerConfig class" should "should get the properties from application.conf file" in {
    val serverConfig: ServerConfig = ServerConfig.apply()
    assert(serverConfig.port === 8080)
    assert(serverConfig.host === "0.0.0.0")
  }

}
