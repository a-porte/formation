package calculator

class StringCalculator :
  def add(input: String = ""): String =
    input match
      case "" => "0"
      case line => line.split(s"[^0-9]").toList.filter(_.nonEmpty).map(_.toInt).sum.toString
