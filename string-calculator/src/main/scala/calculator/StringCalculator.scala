package calculator

class StringCalculator :
  def add(input: String): String = input match
    case "" => "0"
    case _ => input.split(',').toList.map(_.toInt).sum.toString
  

