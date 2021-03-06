package kz.kerey.controllers

import java.io.{FileInputStream, File}

import kz.kerey.db.DefaultDbConfig
import kz.kerey.entities.system.{User, Session}
import spark._

object ResourceController {

  def initialize() = {

    SparkBase.secure("spark", "Kestroday1987", null, null)

    val loginFilter = new Filter {
      override def handle(request: Request, response: Response): Unit = {
        val session = request.cookie("session")
        println(session + " is come")
        if (session==null) {
          response.redirect("/web/pages/public/login.html")
        }
        else {
          if (!Session.isValid(session)) {
            response.removeCookie("session")
            response.redirect("/web/pages/public/error.html")
          }
          else {
            response.cookie("session",session)
          }
        }
      }
    }

    Spark.before("/", loginFilter)
    Spark.before("/web/pages/auth/:name", loginFilter)

    Spark.get("/login", new Route {
      override def handle(request: Request, response: Response): AnyRef = {
        val userId = request.queryParams("userId")
        val password = request.queryParams("password")
        val user = User.authenticate(userId, password)

        if (user!=null) {
          val session = Session.getSessionCookie(userId)
          response.cookie("session",session._id.toString)
          response.removeCookie("status")
          response.redirect("/")
          ""
        }
        else {
          response.cookie("status", "false")
          response.redirect("/web/pages/public/login.html")
          ""
        }
      }
    })

    Spark.get("/", new Route {
      override def handle(request: Request, response: Response): AnyRef = {
        response.redirect("/web/pages/auth/index.html")
        ""
      }
    })

    Spark.get("/web/js/:name", new Route {
      override def handle(request: Request, response: Response): AnyRef = {
        response.`type`("text/javascript")
        val file = new File("web/js/"+request.params("name"))
        val inputStream = new FileInputStream(file)
        val buf = new Array[Byte](file.length().toInt)
        inputStream.read(buf)
        response.raw().getOutputStream.write(buf)
        inputStream.close()
        ""
      }
    })

    Spark.get("/web/css/:name", new Route {
      override def handle(request: Request, response: Response): AnyRef = {
        response.`type`("text/css")
        val file = new File("web/css/"+request.params("name"))
        val inputStream = new FileInputStream(file)
        val buf = new Array[Byte](file.length().toInt)
        inputStream.read(buf)
        response.raw().getOutputStream.write(buf)
        inputStream.close()
        "ok"
      }
    })

    Spark.get("/web/css/images/:name", new Route {
      override def handle(request: Request, response: Response): AnyRef = {
        response.`type`("image/png")
        val file = new File("web/css/images/"+request.params("name"))
        val inputStream = new FileInputStream(file)
        val buf = new Array[Byte](file.length().toInt)
        inputStream.read(buf)
        response.raw().getOutputStream.write(buf)
        inputStream.close()
        ""
      }
    })

    Spark.get("/web/pages/auth/:name", new Route {
      override def handle(request: Request, response: Response): AnyRef = {
        response.`type`("text/html")
        val file = new File("web/pages/auth/"+request.params("name"))
        val inputStream = new FileInputStream(file)
        val buf = new Array[Byte](file.length().toInt)
        inputStream.read(buf)
        response.raw().getOutputStream.write(buf)
        inputStream.close()
        ""
      }
    })

    Spark.get("/web/pages/public/:name", new Route {
      override def handle(request: Request, response: Response): AnyRef = {
        response.`type`("text/html")
        val file = new File("web/pages/public/"+request.params("name"))
        val inputStream = new FileInputStream(file)
        val buf = new Array[Byte](file.length().toInt)
        inputStream.read(buf)
        response.raw().getOutputStream.write(buf)
        inputStream.close()
        ""
      }
    })

  }

}
