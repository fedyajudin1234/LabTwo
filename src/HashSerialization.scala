import java.io.{BufferedReader, FileInputStream, FileReader, IOException, ObjectInputStream, PrintWriter}

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

}
