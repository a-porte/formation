package calculator

class StringCalculator :
  def add(input: String = ""): String =
    input match
      case "" => "0"
      case line =>
        val intList = line.split(s"[^0-9-]").toList.filter(_.nonEmpty).map(_.toInt)
        intList.count(_ < 0) match
          case 0 => intList.sum.toString
          case _ => raiseCustomException(intList.filter(_ < 0))

  private def raiseCustomException(negList: List[Int]) =
    throw
      val innerInterpolatedString = if negList.size > 1 then " : " + negList.mkString(", ") else ""
      new NumberFormatException(s"Les nombres négatifs ne sont pas autorisés$innerInterpolatedString")
