package repositories


trait BasicRepository[T] {

  def get(): Iterable[Any]
  def insert(id: String,entity: T): Unit
  def delete(id: String): Unit
  def update(id: String,entity: T): Unit

}
