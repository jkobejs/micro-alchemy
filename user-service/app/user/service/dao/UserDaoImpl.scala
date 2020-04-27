package user.service.dao

import io.github.jkobejs.user.service.endpoints.{ CreateUser, User }
import scala.concurrent.Future
import slick.basic.DatabaseConfig
import slick.basic.BasicProfile
import user.service.slick.Tables
import scala.concurrent.ExecutionContext
import slick.jdbc.PostgresProfile
import slick.basic.BasicBackend

class UserDaoImpl(
  db: BasicBackend#DatabaseDef,
  val profile: slick.jdbc.PostgresProfile
)(implicit ec: ExecutionContext)
    extends UserDao
    with Tables {

  import profile.api._
  def create(createUser: CreateUser): Future[User] =
    db.run(
        Users returning Users += UsersRow(
          id = 0,
          name = createUser.name,
          age = createUser.age
        )
      )
      .map(rowToUser)

  def getById(id: Long): Future[Option[User]] =
    db.run(
      Users.filter(_.id === id).result.headOption.map(_.map(rowToUser))
    )
  def all(): Future[Seq[User]] =
    db.run(
      Users.result.map(_.map(rowToUser))
    )

  private def rowToUser(userRow: UsersRow): User =
    User(
      id = userRow.id,
      name = userRow.name,
      age = userRow.age
    )
}
