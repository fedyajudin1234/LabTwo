import java.util.Random

class Vector2D() extends ObjectBuilder {
  valueX = 0
  valueY = 0
  var valueX = .0
  var valueY = .0

  def this(valueX: Double, valueY: Double) {
    this()
    this.valueX = valueX
    this.valueY = valueY
  }

  def this(v: Vector2D) {
    this()
    valueX = v.valueX
    valueY = v.valueY
  }

  def print(): Unit = {
    System.out.println("X is: " + valueX + " Y is: " + valueY)
  }

  override def toString: String = {
    val s = "X: " + valueX + " " + "Y: " + valueY
    s
  }

  override def typeName = "Vector2D"

  override def create: Any = {
    val min = 0
    val max = 100
    val diff = max - min
    val random = new Random
    val i1 = random.nextInt(diff + 1)
    val i2 = random.nextInt(diff + 1)
    new Vector2D(i1, i2)
  }

  override def toString(value: Any): String = value.toString.toString
}
