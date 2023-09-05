package calculator

class StringCalculator :
  def add(input: String = ""): String =
    input match
      case "" => "0"
      case line =>
        val intList = line.split(s"[^0-9-]").toList.filter(_.nonEmpty).map(_.toInt)
        if !intList.exists(_ < 0) then
          intList.sum.toString
        else
          throw new NumberFormatException("Les nombres négatifs ne sont pas autorisés")
