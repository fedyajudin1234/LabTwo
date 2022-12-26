import java.util.Random

class StringObjectBuilder extends ObjectBuilder {
  create

  override def typeName = "String"

  override def create: String = {
    val leftBorder = 97
    val rightBorder = 122
    val random = new Random
    val stringBuffer = new StringBuffer
    for (i <- 0 until 1) {
      val randomBorder = leftBorder + (random.nextFloat * (rightBorder - leftBorder + 1)).toInt
      stringBuffer.append(randomBorder.toChar)
    }
    stringBuffer.toString
  }

  override def toString(value: Any): String = value.toString.toString
}
