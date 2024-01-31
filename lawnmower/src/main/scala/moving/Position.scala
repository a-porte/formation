package moving

case class Position(x: Int, y: Int)


object Position:
  def apply(xAndY: Array[Int]) : Position = Position(xAndY.head, xAndY.reverse.head)
