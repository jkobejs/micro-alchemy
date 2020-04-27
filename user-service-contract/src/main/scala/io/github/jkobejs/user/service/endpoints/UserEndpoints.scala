package io.github.jkobejs.user.service.endpoints

import java.util.UUID

import endpoints.{ algebra, generic }

trait UserEndpoints extends algebra.Endpoints with algebra.JsonEntitiesFromSchemas with generic.JsonSchemas {
  val create = endpoint(
    post(
      path / "users",
      jsonRequest[CreateUser],
      docs = Some("Create user data")
    ),
    ok(jsonResponse[User], docs = Some("Created user"))
  )

  val getUser = endpoint(
    get(
      path / "users" / segment[Long]("user id", docs = Some("Users' uuid"))
    ),
    ok(jsonResponse[User]).orNotFound()
  )

  val all = endpoint(
    get(
      path / "users"
    ),
    ok(jsonResponse[Seq[User]])
  )

  implicit lazy val jsonSchemaCreateUser: JsonSchema[CreateUser] = genericJsonSchema
  implicit lazy val jsonSchemaUser: JsonSchema[User]             = genericJsonSchema
}

case class CreateUser(
  name: String,
  age: Int
)

case class User(
  id: Long,
  name: String,
  age: Int
)
