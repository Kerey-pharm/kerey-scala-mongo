package kz.kerey

import kz.kerey.controllers.ResourceController
import kz.kerey.db.DefaultDbConfig
import kz.kerey.entities.system.{User, Session}
import spark._

object Main {

  def main(args: Array[String]): Unit = {

    User.initialize()
    ResourceController.initialize()

  }
}
