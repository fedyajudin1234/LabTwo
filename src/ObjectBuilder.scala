trait ObjectBuilder {
  def typeName: String

  def create: Any

  def toString(value: Any): String
}
