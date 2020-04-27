import com.github.tototoshi.sbt.slick.CodegenPlugin.autoImport.{
  slickCodegenDatabasePassword,
  slickCodegenDatabaseUrl,
  slickCodegenJdbcDriver
}
import Dependencies._

inThisBuild(
  List(
    name := "micro-alchemy",
    organization := "io.github.jkobejs",
    homepage := Some(url("https://github.com/jkobejs/endpoints-microservices")),
    organizationName := "Josip Grgurica and Contributors",
    startYear := Some(2019),
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "jkobejs",
        "Josip Grgurica",
        "josip.grgurica@gmail.com",
        url("https://github.com/jkobejs")
      )
    ),
    scalaVersion := "2.13.1",
    version := "1.0-SNAPSHOT"
  )
)

lazy val root = (project in file(".")).aggregate(
  commons,
  `user-service-contract`,
  `user-service`
)

lazy val `user-service` =
  Project("user-service", file("user-service"))
    .settings(
      `docker-settings`,
      libraryDependencies ++= Seq(
        jdbc,
        library.postgresql,
        library.`play-slick`,
        library.`play-flyway`
      )
    )
    .settings(`flyway-sbt-settings`("user-service"))
    .settings(`slick-codgen-settings`("user-service", "user.service"))
    .enablePlugins(DockerPlugin, PlayScala, FlywayPlugin, CodegenPlugin)
    .dependsOn(`user-service-contract`)

lazy val `user-service-contract` =
  Project("user-service-contract", file("user-service-contract"))
    .settings(
      libraryDependencies ++= Seq(library.`endpoints-json-schema-eneric`, library.`endpoints-play-server`)
    )
    .dependsOn(commons)

lazy val commons = Project("commons", file("commons"))
  .settings(
    libraryDependencies ++= Seq(
      library.`endpoints-algebra`
    )
  )

lazy val `docker-settings` = Seq(
  dockerBaseImage := "anapsix/alpine-java:8",
  dockerRepository := sys.env.get("DOCKER_REPOSITORY")
)

lazy val databaseUser     = sys.env.getOrElse("DATABASE_USER", "postgres")
lazy val databasePassword = sys.env.getOrElse("DATABASE_PASSWORD", "postgres")

def `slick-codgen-settings`(service: String, rootPackage: String) = Seq(
  slickCodegenDatabaseUrl := sys.env.getOrElse("JDBC_DATABASE_URL", s"jdbc:postgresql://localhost:55432/$service"),
  slickCodegenDatabaseUser := databaseUser,
  slickCodegenDatabasePassword := databasePassword,
  slickCodegenDriver := _root_.slick.jdbc.PostgresProfile,
  slickCodegenJdbcDriver := "org.postgresql.Driver",
  slickCodegenOutputPackage := s"$rootPackage.slick",
  slickCodegenExcludedTables := Seq("flyway_schema_history"),
  slickCodegenOutputDir := file(s"$service/app")
)

def `flyway-sbt-settings`(service: String) = Seq(
  flywayUrl := sys.env.getOrElse("JDBC_DATABASE_URL", s"jdbc:postgresql://localhost:55432/$service"),
  flywayUser := databaseUser,
  flywayPassword := databasePassword,
  flywayBaselineOnMigrate := true
)

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt it:scalafmt")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck it:scalafmtCheck")
