package user.service

import endpoints.play
import endpoints.play.server.PlayComponents
import io.github.jkobejs.user.service.endpoints.UserEndpoints
import java.util.concurrent.atomic.AtomicReference
import io.github.jkobejs.user.service.endpoints.User
import java.{ util => ju }
import user.service.dao.UserDao

class UserServer(val playComponents: PlayComponents, val userDao: UserDao)
    extends UserEndpoints
    with play.server.Endpoints
    with play.server.JsonEntitiesFromSchemas { parent =>
  val routes = routesFromEndpoints(
    create.implementedByAsync { createUser =>
      userDao.create(createUser)
    },
    getUser.implementedByAsync(id => userDao.getById(id)),
    all.implementedByAsync(_ => userDao.all())
  )

}
