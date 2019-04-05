import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import entities.Person
import org.scalatest.{Matchers, WordSpec}
import routes.CacheRouter

class AppTest extends WordSpec with Matchers with ScalatestRouteTest {
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  "Get Test" should {
    "return all the todos" in {
      val router = new CacheRouter()

      Get("/mongo/getAll") ~> router.route ~> check {
        val response = responseAs[Seq[Person]]
        println(response)
        status shouldBe StatusCodes.OK
        response shouldBe List(Person("pablo", 21))
      }
    }
  }

  "Post Insert Test" should {
    val router = new CacheRouter()
    "inserting person" in {

      Post("/mongo/insert", Person("Ramon", 40)) ~> router.route ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "Checking person inserted" in {

      Get("/mongo/getAll") ~> router.route ~> check {
        val response = responseAs[Seq[Person]]
        response shouldBe List(Person("pablo", 21), Person("Ramon", 40))

      }
    }
  }


  "DELETE Delete Test" should {
    val router = new CacheRouter()
    "Deleting person" in {

      Delete("/mongo/delete", Person("pablo", 21)) ~> router.route ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "Checking if the person was deleted" in {
      Get("/mongo/getAll") ~> router.route ~> check {
        val response = responseAs[Seq[Person]]
        response shouldBe List.empty

      }
    }
  }

  "UPDATE Update Test" should {
    val router = new CacheRouter()
    "Updating person" in {

      Post("/mongo/update", Person("pablo", 68)) ~> router.route ~> check {
        status shouldBe StatusCodes.OK
      }
    }

    "Checking if the person was updated" in {
      Get("/mongo/getAll") ~> router.route ~> check {
        val response = responseAs[Seq[Person]]
        response shouldBe List(Person("pablo", 68))

      }
    }
  }

}


