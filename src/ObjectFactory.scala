import java.util
import java.util.{ArrayList, Iterator, List}

class ObjectFactory(objectBuilder: ObjectBuilder) {
  var userTypeList: util.ArrayList[ObjectBuilder] = new util.ArrayList[ObjectBuilder]

  def objectRecorder(objectBuilder: ObjectBuilder):util.List[_] = {
    val list: util.List[ObjectBuilder] = new util.ArrayList[ObjectBuilder]
    userTypeList.add(objectBuilder)
    list
  }

  def getBuilderByName(name: String): ObjectBuilder = {
    val userTypeIterator = userTypeList.iterator
    while ( {
      userTypeIterator.hasNext
    }) {
      val objectBuilder = userTypeIterator.next
      if (objectBuilder.typeName == name) return objectBuilder
    }
    objectBuilder
  }
}
