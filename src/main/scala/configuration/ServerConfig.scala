package configuration

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}
import routes.CacheRouter

import scala.concurrent.{ExecutionContext, Future}

case class ServerConfig(
                  port: Int,
                  host: String,
                  mongoRouter: CacheRouter)(implicit system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext){

  def bind(): Future[ServerBinding] = Http().bindAndHandle(
    mongoRouter.route,
    host,
    port)

  def getExecutionContext: ExecutionContext = ec
  def getSystem: ActorSystem = system

}



object ServerConfig {

  import system.dispatcher
  implicit val system: ActorSystem = ActorSystem(name = "api")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val mongoRouter = new CacheRouter()


  def apply(
             port: Int,
             interface: String): ServerConfig = new ServerConfig(port, interface, mongoRouter)



  def apply(): ServerConfig = apply(ConfigFactory.load.getConfig("akka-http-app"))

  def apply(config: Config): ServerConfig = {
    new ServerConfig(
      config.getInt("port"),
      config.getString("host"),
      mongoRouter
    )
  }

}
