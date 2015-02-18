package im.actor.server

import im.actor.server.api.frontend.{ Tcp, Ws }
import akka.actor._
import akka.stream.ActorFlowMaterializer
import akka.kernel.Bootable
import com.typesafe.config.ConfigFactory
import im.actor.server.db.{ Db, FlywayInit }

class ApiKernel extends Bootable with FlywayInit {
  val config = ConfigFactory.load()
  val serverConfig = config.getConfig("actor-server")

  implicit val system = ActorSystem(serverConfig.getString("actor-system-name"), serverConfig)
  implicit val executor = system.dispatcher
  implicit val materializer = ActorFlowMaterializer()

  def startup() = {
    val flyway = initFlyway(config.getConfig("jdbc"))
    flyway.migrate()
    Db.check()

    Tcp.start(serverConfig)
    Ws.start(serverConfig)
  }

  def shutdown() = {
    system.shutdown()
  }
}
