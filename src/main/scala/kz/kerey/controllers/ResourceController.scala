package kz.kerey.controllers

import java.io.{FileInputStream, File}

import kz.kerey.entities.system.Session
import spark._

import scala.io.Source

object ResourceController {

  def initialize() = {

    SparkBase.secure("spark", "Kestroday1987", null, null)

    val loginFilter = new Filter {
      override def handle(request: Request, response: Response): Unit = {
        val session = request.cookie("session")
        println(session + " is come")
        if (session==null) {
          response.redirect("/login")
        }
        else {
          if (!Session.isValid(session)) {
            response.removeCookie("session")
            Spark.halt(401, "Not authorized")
          }
          else {
            response.cookie("session",session)
          }
        }
      }
    }

    Spark.before("/index", loginFilter)
    Spark.before("/web/pages/:name", loginFilter)

    Spark.get("/login", new Route {
      override def handle(request: Request, response: Response): AnyRef = {
        val session = request.cookie("session")
        if (session==null) {
          val cook = Session.getSessionCookie()._id.toString
          response.cookie("session", cook)
          response.redirect("/index")
          "login is ok"
        }
        else {
          response.redirect("/index")
          "redirecting to Index"
        }
      }
    })

    Spark.get("/index", new Route {
      override def handle(request: Request, response: Response): AnyRef = {
        response.redirect("/web/pages/index.html")
        ""
      }
    })

    Spark.get("/login", new Route {
      override def handle(request: Request, response: Response): AnyRef = {
        response.redirect("/web/pages/login.html")
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
        ""
      }
    })

    Spark.get("/web/pages/:name", new Route {
      override def handle(request: Request, response: Response): AnyRef = {
        response.`type`("text/html")
        val file = new File("web/pages/"+request.params("name"))
        val inputStream = new FileInputStream(file)
        val buf = new Array[Byte](file.length().toInt)
        inputStream.read(buf)
        response.raw().getOutputStream.write(buf)
        ""
      }
    })

  }

}
