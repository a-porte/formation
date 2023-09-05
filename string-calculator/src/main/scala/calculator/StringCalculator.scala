package calculator

class StringCalculator :
  def add(input: String = ""): String =
    input match
      case "" => "0"
      case line =>
        val intList = line.split(s"[^0-9-]").toList.filter(_.nonEmpty).map(_.toInt)
        intList.count(_ < 0) match
          case 0 => intList.sum.toString
          case 1 => throw new NumberFormatException("Les nombres négatifs ne sont pas autorisés")
          case 2 => throw new NumberFormatException(s"Les nombres négatifs ne sont pas autorisés : ${intList.filter(_ < 0).mkString(", ")}")

