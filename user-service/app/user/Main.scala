import play.api._
import play.api.ApplicationLoader.Context
import play.api.routing.Router
import user.service.UserServer
import endpoints.play.server.PlayComponents
import play.api.mvc.ControllerComponents
import play.core.server.ServerConfig
import play.core.server.NettyServer
import play.api.mvc.EssentialFilter
import play.api.db.DBComponents
import play.api.db.HikariCPComponents
import org.flywaydb.play.FlywayPlayComponents
import play.api.db.slick.SlickComponents
import play.api.db.slick.DbName
import slick.jdbc.PostgresProfile
import user.service.dao.UserDaoImpl
import slick.dbio.DBIOAction

class Main extends ApplicationLoader {

  def load(context: Context): Application = {
    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment, context.initialConfiguration, Map.empty)
    }

    new UserComponents(context).application
  }
}

class UserComponents(context: Context)
    extends BuiltInComponentsFromContext(context)
    // with DBComponents
    with HikariCPComponents
    with SlickComponents
    with FlywayPlayComponents {
  def httpFilters: Seq[EssentialFilter] = Nil

  flywayPlayInitializer

  val dbConfig = slickApi.dbConfig[PostgresProfile](DbName("default"))

  lazy val router = Router.from(
    new UserServer(PlayComponents.fromBuiltInComponents(this), new UserDaoImpl(dbConfig.db, dbConfig.profile)).routes
  )
}
