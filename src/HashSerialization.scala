import java.io.{BufferedReader, FileInputStream, FileReader, IOException, ObjectInputStream, PrintWriter}
import scala.util.control.Breaks
import scala.util.control.Breaks.break

class HashSerialization {
  @throws[IOException]
  def saveToFile(filename: String, hash: IHash, objectBuilder: ObjectBuilder): Unit = {
    try {
      val writer = new PrintWriter(filename)
      try {
        writer.write("<hashMap>\n")
        hash.forEach((value: Any) => writer.println(objectBuilder.toString(value)))
        writer.write("</hashMap>")
        writer.close()
      } catch {
        case e: IOException =>
          throw new RuntimeException(e)
      } finally if (writer != null) writer.close()
    }
  }

  @throws[IOException]
  @throws[ClassNotFoundException]
  def loadFromFile(filename: String, hash: IHash): IHash = try {
    val br = new BufferedReader(new FileReader(filename))
    try {
      var line: String = null
      line = br.readLine
        while(line != null) {
          println(line)
          line = br.readLine
        }
      hash
    } catch {
      case e: Exception =>
        throw new RuntimeException(e)
    } finally if (br != null) br.close()
  }
    /*val fileInputStream = new FileInputStream("C:\\Users\\Username\\Desktop\\save.ser")
    val objectInputStream = new ObjectInputStream(fileInputStream)

    val savedGame = objectInputStream.readObject.asInstanceOf[Nothing]

    System.out.println(savedGame)

    objectInputStream.close()*/
}
