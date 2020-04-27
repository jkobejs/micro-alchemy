package user.service.dao

import io.github.jkobejs.user.service.endpoints.CreateUser
import scala.concurrent.Future
import io.github.jkobejs.user.service.endpoints.User

trait UserDao {
  def create(createUser: CreateUser): Future[User]
  def getById(id: Long): Future[Option[User]]
  def all(): Future[Seq[User]]
}
