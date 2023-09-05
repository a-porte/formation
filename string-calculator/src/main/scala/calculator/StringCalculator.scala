package calculator

class StringCalculator :
  private val isNegative :Int => Boolean = (i:Int) => i < 0
  def add(input: String = ""): String =
    input match
      case "" => "0"
      case line =>
        val delim: String = parseDelim(line)
        val intList =  line.split(s"[,|\n|$delim]").toList.filter(_.nonEmpty)
          .filterNot(_.contains("//")).map(_.toInt).filter(_ < 1000)

        intList.count(isNegative) match
          case 0 => intList.sum.toString
          case _ => raiseCustomException(intList.filter(isNegative))

  private def parseDelim(line: String) =
     line.split("\n").toList.filter(_.contains("//"))
       .map(_.mkString).mkString //flattening everything
       .replace("//", "")


  private def raiseCustomException(negList: List[Int]) =
    throw
      val innerInterpolatedString = if negList.size > 1 then s" : ${negList.mkString(", ")}" else ""
      new NumberFormatException(s"Les nombres négatifs ne sont pas autorisés$innerInterpolatedString")
