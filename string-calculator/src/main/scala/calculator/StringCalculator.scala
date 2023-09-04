package calculator

class StringCalculator :
  def add(input: String): String = input match
    case "" => "0"
    case _ => input.split("[,|\n]").toList.map(_.toInt).sum.toString
  

