package moving

import scala.collection.immutable.HashMap

enum Move:
  case LEFT, CONTINUE, RIGHT



object Move:
  def apply(letter: String): Option[Move] =
    val translation: HashMap[String, Move] = HashMap("G" -> Move.LEFT, "A" -> Move.CONTINUE, "D" -> Move.RIGHT)
    translation.get(letter)

