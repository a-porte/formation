package moving

import scala.collection.immutable.HashMap

//TODO how to respect open/close principle ? by using Command pattern?
enum Move(val rotation: Int):
  case LEFT extends Move(-90)
  case CONTINUE extends Move(0)
  case RIGHT extends Move(90)



object Move:
  def apply(letter: String): Option[Move] =
    //TODO useless complexity?
    val translation: HashMap[String, Move] = HashMap("G" -> Move.LEFT, "A" -> Move.CONTINUE, "D" -> Move.RIGHT)
    translation.get(letter)

  def unapply(m: Move): Int = m.rotation


