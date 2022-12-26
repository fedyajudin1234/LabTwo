import java.awt.{BorderLayout, Dimension, Frame, Toolkit}
import java.awt.event.{ActionEvent, ActionListener}
import java.io.IOException
import javax.swing.{AbstractAction, BorderFactory, JButton, JDialog, JFrame, JLabel, JPanel, JTextField, WindowConstants}
import javax.swing.border.Border

class Console() {
  val jFrame: Frame = getFrame
  val jPanel = new JPanel
  var hash: Hash = new Hash(size)
  var hash1: Hash = new Hash(size)
  val vector2D = new Vector2D
  val hashSerialization = new HashSerialization
  jFrame.add(jPanel)
  val jlable = new JLabel("Insert HashMap size: ")
  jlable.setBounds(200, 50, 30, 40)
  jPanel.add(jlable)
  val jText = new JTextField(10)
  jPanel.add(jText)
  jText.setBounds(200, 50, 100, 100)
  jPanel.revalidate()
  val border: Border = BorderFactory.createEtchedBorder
  jPanel.setBorder(border)
  val jSetButton = new JButton("Add")
  jPanel.add(jSetButton)
  jSetButton.addActionListener(new ActionListener() {
    override def actionPerformed(e: ActionEvent): Unit = {
      try {
        val getValue = jText.getText
        size = getValue.toInt
        hash = new Hash(size = size);
        if (size > 0) {
          val myDialog = new Console.this.MyDialog
          myDialog.setVisible(true)
          myDialog.setBounds(500, 500, 500, 450)
        }
        else System.out.println("Incorrect value")
      } catch {
        case exception: Exception =>
          System.out.println("Text field should contain integer value")
      }
    }
  })
  jPanel.revalidate()
  var size = 0
  class MyDialog() extends JDialog(jFrame, "Type choosing", true) {
    val jStringButton = new JButton(new Console.this.MyActionString)
    jStringButton.setText("String")
    add(jStringButton, BorderLayout.NORTH)
    val jVectorButton = new JButton(new Console.this.MyActionVector)
    jVectorButton.setText("Vector")
    add(jVectorButton, BorderLayout.SOUTH)
    setBounds(550, 400, 250, 100)
  }

  class MyStringDialog() extends JDialog(jFrame, "String Console", true) {
    //hash = new Hash(size = 10)
    val jNumberLable = new JLabel("Number: ")
    add(jNumberLable, BorderLayout.BEFORE_FIRST_LINE)
    val jNumberTextField = new JTextField(20)
    jNumberTextField.setBounds(50, 3, 250, 20)
    add(jNumberTextField, BorderLayout.NORTH)
    val jGenerate = new JButton
    jGenerate.setText("Generate")
    jGenerate.setBounds(210, 30, 100, 25)
    add(jGenerate, BorderLayout.NORTH)
    jGenerate.addActionListener(new ActionListener() {
      override def actionPerformed(e: ActionEvent): Unit = {
        val numberValue = jNumberTextField.getText
        val number = numberValue.toInt
        val stringObjectBuilder = new StringObjectBuilder
        var factory: ObjectFactory = null
        for (i <- 0 until number) {
          hash.put(new IntObjectBuilder().create, stringObjectBuilder.create)
          try hash1 = hash.clone
          catch {
            case ex: CloneNotSupportedException =>
              throw new RuntimeException(ex)
          }
          factory = new ObjectFactory(stringObjectBuilder)
          factory.objectRecorder(stringObjectBuilder)
        }
        factory.getBuilderByName("String")
        // увеличение хэш-таблицы
        hash = hash.resizeHash(hash, hash1, number)
      }
    })
    val jIDGetLable = new JLabel("ID: ")
    jIDGetLable.setBounds(1, 75, 20, 15)
    add(jIDGetLable, BorderLayout.NORTH)
    val jIDGetTextField = new JTextField(20)
    jIDGetTextField.setBounds(18, 75, 300, 20)
    add(jIDGetTextField, BorderLayout.NORTH)
    val jGetHash = new JButton
    jGetHash.setText("Hash")
    jGetHash.setBounds(73, 100, 70, 25)
    add(jGetHash, BorderLayout.NORTH)
    jGetHash.addActionListener(new ActionListener() {
      override def actionPerformed(e: ActionEvent): Unit = {
        System.out.println(hash.toString)
      }
    })
    val jGetID = new JButton
    jGetID.setText("GetID")
    jGetID.setBounds(150, 100, 70, 25)
    add(jGetID, BorderLayout.NORTH)
    jGetID.addActionListener(new ActionListener() {
      override def actionPerformed(e: ActionEvent): Unit = {
        val keyGetter = jIDGetTextField.getText
        val number = keyGetter.toInt
        System.out.println(hash.get(number))
      }
    })
    val jRemove = new JButton
    jRemove.setText("Remove")
    jRemove.setBounds(227, 100, 90, 25)
    add(jRemove, BorderLayout.NORTH)
    jRemove.addActionListener(new ActionListener() {
      override def actionPerformed(e: ActionEvent): Unit = {
        val keyRemover = jIDGetTextField.getText
        val number = keyRemover.toInt
        System.out.println(hash.remove(number))
      }
    })
    val jSerialize = new JButton
    jSerialize.setText("Serialize")
    jSerialize.setBounds(1, 130, 317, 25)
    add(jSerialize, BorderLayout.NORTH)
    jSerialize.addActionListener(new ActionListener() {
      override def actionPerformed(e: ActionEvent): Unit = {
        try hashSerialization.saveToFile("hashMap.xml", hash, new StringObjectBuilder)
        catch {
          case ex: IOException =>
            throw new RuntimeException(ex)
        }
      }
    })
    val jDeserialize = new JButton
    jDeserialize.setText("Deserialize")
    jDeserialize.setBounds(1, 160, 317, 25)
    add(jDeserialize, BorderLayout.NORTH)
    jDeserialize.addActionListener(new ActionListener() {
      override def actionPerformed(e: ActionEvent): Unit = {
        val hashSerialization = new HashSerialization
        try hashSerialization.loadFromFile("hashMap.xml", hash)
        catch {
          case ex: IOException =>
            throw new RuntimeException(ex)
          case ex: ClassNotFoundException =>
            throw new RuntimeException(ex)
        }
      }
    })
    setBounds(400, 400, 335, 270)
  }

  class MyVectorDialog() extends JDialog(jFrame, "Vector Console", true) {
    hash = new Hash(size = 10)
    val jNumberLable = new JLabel("Number: ")
    add(jNumberLable, BorderLayout.BEFORE_FIRST_LINE)
    val jNumberTextField = new JTextField(20)
    jNumberTextField.setBounds(50, 3, 250, 20)
    add(jNumberTextField, BorderLayout.NORTH)
    val jGenerate = new JButton
    jGenerate.setText("Generate")
    jGenerate.setBounds(210, 30, 100, 25)
    add(jGenerate, BorderLayout.NORTH)
    jGenerate.addActionListener(new ActionListener() {
      override def actionPerformed(e: ActionEvent): Unit = {
        val numberValue = jNumberTextField.getText
        val number = numberValue.toInt
        var factory: ObjectFactory = null
        for (i <- 0 until number) {
          hash.put(new IntObjectBuilder().create, vector2D.create)
          try hash1 = hash.clone
          catch {
            case ex: CloneNotSupportedException =>
              throw new RuntimeException(ex)
          }
          factory = new ObjectFactory(vector2D)
          factory.objectRecorder(vector2D)
        }
        factory.getBuilderByName("Vector2D")
        hash = hash.resizeHash(hash, hash1, number)
      }
    })
    val jIDGetLable = new JLabel("ID: ")
    jIDGetLable.setBounds(1, 75, 20, 15)
    add(jIDGetLable, BorderLayout.NORTH)
    val jIDGetTextField = new JTextField(20)
    jIDGetTextField.setBounds(18, 75, 300, 20)
    add(jIDGetTextField, BorderLayout.NORTH)
    val jGetHash = new JButton
    jGetHash.setText("Hash")
    jGetHash.setBounds(73, 100, 70, 25)
    add(jGetHash, BorderLayout.NORTH)
    jGetHash.addActionListener(new ActionListener() {
      override def actionPerformed(e: ActionEvent): Unit = {
        System.out.println(hash.toString)
      }
    })
    val jGetID = new JButton
    jGetID.setText("GetID")
    jGetID.setBounds(150, 100, 70, 25)
    add(jGetID, BorderLayout.NORTH)
    jGetID.addActionListener(new ActionListener() {
      override def actionPerformed(e: ActionEvent): Unit = {
        val keyGetter = jIDGetTextField.getText
        val number = keyGetter.toInt
        System.out.println(hash.get(number))
      }
    })
    val jRemove = new JButton
    jRemove.setText("Remove")
    jRemove.setBounds(227, 100, 90, 25)
    add(jRemove, BorderLayout.NORTH)
    jRemove.addActionListener(new ActionListener() {
      override def actionPerformed(e: ActionEvent): Unit = {
        val keyRemover = jIDGetTextField.getText
        val number = keyRemover.toInt
        System.out.println(hash.remove(number))
      }
    })
    val jSerialize = new JButton
    jSerialize.setText("Serialize")
    jSerialize.setBounds(1, 130, 317, 25)
    add(jSerialize, BorderLayout.NORTH)
    jSerialize.addActionListener(new ActionListener() {
      override def actionPerformed(e: ActionEvent): Unit = {
        try {
          hashSerialization.saveToFile("hashMap.xml", hash, vector2D)
          System.out.println(hash.stringLengthCounter)
        } catch {
          case ex: IOException =>
            throw new RuntimeException(ex)
        }
      }
    })
    val jDeserialize = new JButton
    jDeserialize.setText("Deserialize")
    jDeserialize.setBounds(1, 160, 317, 25)
    add(jDeserialize, BorderLayout.NORTH)
    jDeserialize.addActionListener(new ActionListener() {
      override def actionPerformed(e: ActionEvent): Unit = {
        val hashSerialization = new HashSerialization
        try hashSerialization.loadFromFile("hashMap.xml", hash)
        catch {
          case ex: IOException =>
            throw new RuntimeException(ex)
          case ex: ClassNotFoundException =>
            throw new RuntimeException(ex)
          case ex: Exception =>
            throw new RuntimeException(ex)
        }
      }
    })
    setBounds(400, 400, 335, 270)
  }

  class MyActionString extends AbstractAction {
    override def actionPerformed(e: ActionEvent): Unit = {
      val myStringDialog = new Console.this.MyStringDialog
      myStringDialog.setVisible(true)
    }
  }

  class MyActionVector extends AbstractAction {
    override def actionPerformed(e: ActionEvent): Unit = {
      val myVectorDialog = new Console.this.MyVectorDialog
      myVectorDialog.setVisible(true)
    }
  }

  def getFrame: JFrame = {
    val jFrame = new JFrame
    jFrame.setVisible(true)
    jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    val tk = Toolkit.getDefaultToolkit
    val d = tk.getScreenSize
    jFrame.setBounds(d.width / 3, d.height / 3, 300, 150)
    jFrame.setTitle("L1 Console")
    jFrame
  }
}

