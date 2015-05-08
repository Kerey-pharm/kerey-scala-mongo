package kz.kerey.entities.system

import java.util.Date

import com.mongodb.casbah.commons.MongoDBObject
import kz.kerey.db.DefaultDbConfig
import org.bson.types.ObjectId

case class Session(_id: ObjectId = new ObjectId, var expire: Long, var user: String)

object Session {

  val fiveMinutes = 1000 * 60 * 5
  val sessions = DefaultDbConfig.sessions

  def isValid(_id: String): Boolean = {
    if (!ObjectId.isValid(_id)) return false
    val cookie = sessions.findOne(new ObjectId(_id))
    if (cookie!=null) {
      if (cookie.get("expire").asInstanceOf[Long] < new Date().getTime) { false }
      else {
        val res = sessions.update(MongoDBObject("_id"->new ObjectId(_id)), MongoDBObject("$set" -> MongoDBObject("expire"-> (new Date().getTime + fiveMinutes))))
        true
      }
    }
    else {
      false
    }
  }

  def getSessionCookie(user: String): Session = {
    val session = Session(expire = (new Date().getTime + fiveMinutes), user = user)
    sessions.insert( MongoDBObject("_id"->session._id, "expire"->session.expire, "user"->session.user) )
    session
  }

}
