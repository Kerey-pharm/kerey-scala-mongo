package kz.kerey.entities

import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId

case class Good(_id: ObjectId = new ObjectId(),
                name: String,
                primaryBarcode: String,
                partialSelling: Boolean,
                countPerBox: Int,
                countSellable: Int,
                types: List[ObjectId])

object GoodMapper {
  def toBson(obj: Good): DBObject = {
    MongoDBObject(
      "_id" -> obj._id,
      "name" -> obj.name,
      "primaryBarcode" -> obj.primaryBarcode,
      "partialSelling" -> obj.partialSelling,
      "countPerBox" -> obj.countPerBox,
      "countSellable" -> obj.countSellable,
      "types" -> obj.types
    )
  }
  def fromBson(bson: DBObject): Good = {
    Good(
      _id = bson.get("_id").asInstanceOf[ObjectId],
      name = bson.get("name").asInstanceOf[String],
      primaryBarcode = bson.get("primaryBarcode").asInstanceOf[String],
      partialSelling = bson.get("partialSelling").asInstanceOf[Boolean],
      countPerBox = bson.get("countPerBox").asInstanceOf[Int],
      countSellable = bson.get("countSellable").asInstanceOf[Int],
      types = bson.get("types").asInstanceOf[List[ObjectId]]
    )
  }
}
