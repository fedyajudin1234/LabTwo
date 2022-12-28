import java.util
import java.util._
import scala.collection.mutable

class Hash(var size: Int) extends IHash with Cloneable {
  var start: Long = System.currentTimeMillis()
  var stringLengthCounter: Int = 0
  var currValue: Int = 0
  var totalValue: Int = 0
  var maxValue: Int = 0
  var time: Long = 0
  class Entry(var key: Any, var value: Any) {
    var next: Hash#Entry = null

    def getKey: Any = this.key

    def getValue: Any = this.value

    def setValue(value: Any): Unit = {
      this.value = value
    }

    override def toString: String = {
      var tmp: Hash#Entry = this
      val sb: mutable.StringBuilder = new mutable.StringBuilder
      while ( {
        tmp != null
      }) {
        sb.append(tmp.key + " -> " + tmp.value + "; ")
        tmp = tmp.next
      }
      sb.toString
    }
  }
  var array: Array[Hash#Entry] = new Array(size)

  def Hash(size: Int){
    this.size = size
    array = new Array[Hash#Entry](this.size)
  }
  override def put(key: Any, value: Any): Unit = {
    val hash: Int = key.hashCode % size
    var e = array(hash)
    if (e == null) array(hash) = new Entry(key, value)
    else {
      while ( {
        e.next != null
      }) {
        if (e.getKey equals key) {
          e.setValue(value)
          return
        }
        e = e.next
      }
      if (e.getKey equals key) {
        e.setValue(value)
        return
      }
      e.next = new Entry(key, value)
    }
  }
  override def get(key: Any): Any = {
    val hash: Int = key.hashCode % size
    var e: Hash#Entry = array(hash)
    if (e == null) return null
    while ( {
      e != null
    }) {
      if (e.getKey == key) return e.getValue
      e = e.next
    }
    null
  }
  def remove(key: Any): Hash#Entry = {
    val hash: Int = key.hashCode % size
    var e: Hash#Entry = array(hash)
    if (e == null) return null
    if (e.getKey == key) {
      array(hash) = e.next
      e.next = null
      return e
    }
    var prev: Hash#Entry = e
    e = e.next
    while ( {
      e != null
    }) if (e.getKey == key) {
      prev = e
      e = e.next
      prev.next = e.next
      e.next = null
      return e
    }
    null
  }

  override def toString: String = {
    val sb: StringBuilder = new StringBuilder
    for (i <- 0 until this.size) {
      if (array(i) != null){
        sb.append("\t" + i + " " + array(i) + "\n")
      }
      else sb.append(i + " " + "null" + "\n")
    }
    sb.toString
  }

  override def forEach(as: ActionStarter): Unit = {
    var e: Hash#Entry = array(0)
    for (i <- 0 until size) {
      e = array(i)
      as.toDo(e)
    }
  }

  def sizeRecorder(): Unit = {
    var middleValue: Int = 0
    val arrayList: util.ArrayList[Integer] = new util.ArrayList[Integer]
    for (i <- 0 until size) {
      var e: Hash#Entry = array(i)
      currValue = 0
      while ( {
        e != null
      }) {
        currValue += 1
        e = e.next
      }
      arrayList.add(currValue)
      middleValue += currValue
    }
    val min = Collections.min(arrayList)
    val max = Collections.max(arrayList)
    middleValue = middleValue / size
    totalValue = (max - min) - middleValue
    maxValue = max
    System.out.println("----------------------------------------------------------------------------------")
    System.out.println("Размер: " + size)
    System.out.println("Минимальное значение: " + min)
    System.out.println("Максимальное значение: " + max)
    System.out.println("Среднее значение: " + middleValue)
    System.out.println("Значение, которое мы берём для увеличения хэш-таблицы((макс - мин) - ср.знач): " + totalValue)
    System.out.println("Для просмотра, по окончании итераций хэш-таблицы, нажмите Hash")
    System.out.println("----------------------------------------------------------------------------------")
  }

  @throws[CloneNotSupportedException]
  override def clone: Hash = super.clone.asInstanceOf[Hash]

  def insert(hash: Hash): Hash = {
    for (i <- 0 until size) {
      var e: Hash#Entry = array(i)
      while ( {
        e != null
      }) {
        hash.put(e.key, e.value)
        e = e.next
      }
    }
    hash
  }

  def resizeHash(hash: Hash, number: Int): Hash = {
    var newHash = hash
    newHash.sizeRecorder()
    if(hash.maxValue > hash.totalValue){
      newHash = hash.clone
      while (newHash.maxValue > newHash.totalValue) {
        newHash.size = newHash.size * 2
        newHash = new Hash(newHash.size)
        newHash = hash.insert(newHash)
        newHash.sizeRecorder()
      }
    }
    newHash
  }
}
