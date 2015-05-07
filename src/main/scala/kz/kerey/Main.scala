package kz.kerey

import kz.kerey.controllers.ResourceController
import kz.kerey.db.DefaultDbConfig
import kz.kerey.entities.system.Session
import spark._

object Main {

  def main(args: Array[String]): Unit = {

    ResourceController.initialize()

  }
}
