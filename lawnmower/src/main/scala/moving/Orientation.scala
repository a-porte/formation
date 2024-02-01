package moving

enum Orientation(val leftOrright:Int, val upOrDown:Int):
  case NORTH extends Orientation(0, 1)
  case EAST extends Orientation(1, 0)
  case  SOUTH extends Orientation(0, -1)
  case  WEST extends Orientation(-1, 0)


object Orientation:
  def apply(toConvert: String) : Orientation =
    Orientation.valueOf(Orientation.values.filter(_.toString.startsWith(toConvert)).mkString)
