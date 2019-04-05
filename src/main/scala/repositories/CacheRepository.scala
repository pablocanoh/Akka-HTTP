package repositories

import entities.Person

class CacheRepository extends BasicRepository[Person] {

  private var db:Map[String,Person] =Map(
    "pablo" -> Person("pablo", 21)
  )

  override def get(): Iterable[Person] ={
    println(s"Calling get $db")
    for {
      person <- db
    } yield person._2
  }

  override def insert(id: String, person: Person): Unit ={
    println(s"Calling insert")
    db = db + (id -> person)
  }

  override def delete(id: String): Unit = db = db - id

   def update(id: String, entity: Person): Unit = {
    if (db.contains(id)) {
      insert(id, Person(id, entity.age))
    } else {
      insert(id, entity)
    }
  }

}

object CacheRepository{

  def apply: CacheRepository = new CacheRepository()
}

