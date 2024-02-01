package moving

case class Position(x: Int, y: Int):
  override def toString: String = s"$x $y"


object Position:
  def apply(xAndY: Array[Int]) : Position = Position(xAndY.head, xAndY.reverse.head)
