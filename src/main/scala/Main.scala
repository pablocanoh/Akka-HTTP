import configuration.ServerConfig

import scala.concurrent.{Await, ExecutionContext}
import scala.util.{Failure, Success}

object Main extends App {

  val serverConfig: ServerConfig = ServerConfig()
  implicit val ec: ExecutionContext = serverConfig.getExecutionContext

  val binding = serverConfig.bind()
  binding.onComplete {
    case Success(_) => println("Success!")
    case Failure(error) => println(s"Failed: ${error.getMessage}")
  }

  import scala.concurrent.duration._
  Await.result(binding, 3.seconds)

}
