import sbt._
import Keys._

object Dependencies {
  val libraryVersion = new {
    val endpoints     = "0.15.0"
    val postgresql    = "42.2.12"
    val `play-slick`  = "5.0.0"
    val `play-flyway` = "6.0.0"
  }

  val library = new {
    val postgresql                     = "org.postgresql"    % "postgresql"                     % libraryVersion.postgresql
    val `endpoints-algebra`            = "org.julienrf"      %% "endpoints-algebra"             % libraryVersion.endpoints
    val `endpoints-json-schema-eneric` = "org.julienrf"      %% "endpoints-json-schema-generic" % libraryVersion.endpoints
    val `endpoints-play-server`        = "org.julienrf"      %% "endpoints-play-server"         % libraryVersion.endpoints
    val `endpoints-play-client`        = "org.julienrf"      %% "endpoints-play-client"         % libraryVersion.endpoints
    val `play-slick`                   = "com.typesafe.play" %% "play-slick"                    % libraryVersion.`play-slick`
    val `play-flyway`                  = "org.flywaydb"      %% "flyway-play"                   % libraryVersion.`play-flyway`
  }
}
