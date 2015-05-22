package kz.kerey.entities

import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId

case class GoodType(_id: ObjectId = new ObjectId(),
                    name: String)

object GoodTypeMapper {
  def toBson(obj: GoodType): DBObject = {
    MongoDBObject(
    "_id" -> obj._id,
    "name" -> obj.name
    )
  }
  def fromBson(bson: DBObject): GoodType = {
    GoodType(
      _id = bson.get("_id").asInstanceOf[ObjectId],
      name = bson.get("name").asInstanceOf[String]
    )
  }
}