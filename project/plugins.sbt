libraryDependencies += "org.postgresql" % "postgresql" % "42.2.12"

addSbtPlugin("com.typesafe.sbt"      % "sbt-native-packager" % "1.7.0")
addSbtPlugin("org.scalameta"         % "sbt-scalafmt"        % "2.3.2")
addSbtPlugin("com.typesafe.play"     % "sbt-plugin"          % "2.8.1")
addSbtPlugin("com.github.tototoshi"  % "sbt-slick-codegen"   % "1.4.0")
addSbtPlugin("io.github.davidmweber" % "flyway-sbt"          % "6.2.3")
