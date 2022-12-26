import java.util.Random

class IntObjectBuilder extends ObjectBuilder {
  override def typeName = "IntObjectBuilder"

  override def create: Any = {
    val min = 0
    val max = 32000
    val diff = max - min
    val random = new Random
    val i = random.nextInt(diff + 1)
    i
  }

  override def toString(value: Any): String = value.toString
}

