import java.io.IOException

trait IHash {
  def put(key: Any, value: Any): Unit

  def get(key: Any): Any

  def toString: String

  @throws[IOException]
  def forEach(a: ActionStarter): Unit
}
