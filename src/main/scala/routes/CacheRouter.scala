package routes

import akka.http.scaladsl.server.{Directives, Route}
import entities.Person
import repositories.CacheRepository


class CacheRouter extends Router with Directives {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  val repository: CacheRepository = CacheRepository.apply


  override def route: Route = pathPrefix("mongo") {
    path(pm = "getAll") {
      get {
        complete(repository.get())
      }
    } ~ path(pm = "insert") {
      post {
        entity(as[Person]) { person => {

          println(person)

          complete(repository.insert(person.name, person))
        }

        }
      }
    } ~ path(pm = "delete") {
      delete {
        entity(as[Person]) { person => {
          complete(repository.delete(person.name))
        }
        }
      }
    } ~ path(pm = "update") {
      post {
        entity(as[Person]) { person => {
          complete(repository.update(person.name, person))
        }
        }
      }
    }


  }


}
