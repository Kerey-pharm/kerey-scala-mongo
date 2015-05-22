package kz.kerey.entities.system

import com.mongodb.casbah.commons.MongoDBObject
import kz.kerey.db.DefaultDbConfig

case class User(_id: String, password: String, name: String, lastName: String, email: String)

object User {

  val users = DefaultDbConfig.users

  val adminUser = User(_id = "root", password = "root", name = "Dauren", lastName = "Mussa", email = "dauren.mussa@gmail.com")
  def initialize() = {
    if (getUser(adminUser._id)==null) {
      addUser(adminUser)
    }
  }

  def authenticate(userId: String, password: String): User = {
    val userDb = users.findOne(userId)
    if (userDb!=null && userDb.get("password")==password)
      User(
        _id = userDb.get("_id").asInstanceOf[String],
        password = userDb.get("password").asInstanceOf[String],
        name = userDb.get("name").asInstanceOf[String],
        lastName = userDb.get("lastName").asInstanceOf[String],
        email = userDb.get("email").asInstanceOf[String]
      )
    else null
  }

  def getUser(userId: String): User = {
    val userDb = users.findOne(userId)
    if (userDb!=null) {
      User(
        _id = userDb.get("_id").asInstanceOf[String],
        password = userDb.get("password").asInstanceOf[String],
        name = userDb.get("name").asInstanceOf[String],
        lastName = userDb.get("lastName").asInstanceOf[String],
        email = userDb.get("email").asInstanceOf[String]
      )
    }
    else null
  }

  def addUser(user: User): Unit = {
    if (getUser(user._id)==null) {
      users.insert(
        MongoDBObject(
          "_id"->user._id,
          "password"->user.password,
          "name"->user.name,
          "lastName"->user.lastName,
          "email"->user.email
        )
      )
    }
    else {
      throw new Exception("User is already exists")
    }
  }
}
