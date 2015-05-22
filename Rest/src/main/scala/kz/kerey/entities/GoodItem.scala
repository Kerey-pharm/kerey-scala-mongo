package kz.kerey.entities

import java.util.Date

import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId

case class GoodItem(_id: ObjectId = new ObjectId(),
                     parent: ObjectId = null,
                     good: ObjectId = null,
                     barcode: String,
                     serial: String,
                     initialDate: Date,
                     expireDate: Date,
                     initialBoxCount: Int,
                     initialCount: Int,
                     currentCount: Int)

object GoodItemMapper {
  def toBson(obj: GoodItem): DBObject = {
    MongoDBObject(
      "_id" -> obj._id,
      "parent" -> obj.parent,
      "good" -> obj.good,
      "barcode" -> obj.barcode,
      "serial" -> obj.serial,
      "initialDate" -> obj.initialDate,
      "expireDate" -> obj.expireDate,
      "initialBoxCount" -> obj.initialBoxCount,
      "initialCount" -> obj.initialCount,
      "currentCount" -> obj.currentCount
    )
  }
  def fromBson(bson: DBObject): GoodItem = {
    GoodItem(
      _id = bson.get("_id").asInstanceOf[ObjectId],
      parent = bson.get("parent").asInstanceOf[ObjectId],
      good = bson.get("good").asInstanceOf[ObjectId],
      barcode = bson.get("barcode").asInstanceOf[String],
      serial = bson.get("serial").asInstanceOf[String],
      initialDate = bson.get("initialDate").asInstanceOf[Date],
      expireDate = bson.get("expireDate").asInstanceOf[Date],
      initialBoxCount = bson.get("initialBoxCount").asInstanceOf[Int],
      initialCount = bson.get("initialCount").asInstanceOf[Int],
      currentCount = bson.get("currentCount").asInstanceOf[Int]
    )
  }
}
