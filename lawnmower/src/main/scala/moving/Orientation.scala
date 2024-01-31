package moving

enum Orientation:
  case NORTH, EAST, SOUTH, WEST


object Orientation:
  def apply(toConvert: String) : Orientation =
    Orientation.valueOf(Orientation.values.filter(_.toString.startsWith(toConvert)).mkString)
