package io.github.jkobejs.endpoints.authentication.endpoints

import endpoints.algebra
import io.github.jkobejs.endpoints.authentication.algebra.Authentication

trait AuthenticationEndpoints
    extends algebra.Endpoints
    with Authentication
    with endpoints.algebra.JsonEntitiesFromSchemas {
  val login = endpoint(
    post(path / "login", jsonRequest[Credentials]),
    wheneverValid(authenticationToken)
  )

  implicit val credentialsSchema: JsonSchema[Credentials]
}

case class Credentials(username: String, password: String)
