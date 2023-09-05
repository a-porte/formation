package calculator

class StringCalculator :
  private val isNegative :Int => Boolean = (i:Int) => i < 0
  def add(input: String = ""): String =
    input match
      case "" => "0"
      case line =>
        val intList = line.split(s"[^0-9-]").toList.filter(_.nonEmpty).map(_.toInt).filter(_ < 1000)
        intList.count(isNegative) match
          case 0 => intList.sum.toString
          case _ => raiseCustomException(intList.filter(isNegative))

  private def raiseCustomException(negList: List[Int]) =
    throw
      val innerInterpolatedString = if negList.size > 1 then s" : ${negList.mkString(", ")}" else ""
      new NumberFormatException(s"Les nombres négatifs ne sont pas autorisés$innerInterpolatedString")
