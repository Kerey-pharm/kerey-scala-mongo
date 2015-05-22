package kz.kerey.db

import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.{MongoDB, MongoClient}

trait DbConfig {
  def host: String
  def port: Int
  def dbName: String

  val db = MongoClient(host,port)(dbName)
  def collection(collection: String) = db getCollection collection

  val sequence = collection("sequence")
  val sessions = collection("sessions")
  val users = collection("users")

  def nextId(collection: String) = sequence.findAndModify(MongoDBObject("_id" -> collection),
    null, null, false, MongoDBObject("$inc"-> MongoDBObject("seq"->1.toInt)), true, true)
    .get("seq").asInstanceOf[Int]
}

object DefaultDbConfig extends DbConfig {
  def host = "localhost"
  def port = 27017
  def dbName = "kerey_database"
}
