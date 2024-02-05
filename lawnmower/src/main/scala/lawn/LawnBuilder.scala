package lawn

import mower.MowersBuilder

import scala.util.{Failure, Success, Try}

object LawnBuilder:

  def buildFrom(width: String, height: String, funcOnParams : Int => Int): Option[Lawn] =
    Try(Lawn(
      funcOnParams(width.toInt), 
      funcOnParams(height.toInt)
    )) match
      case Failure(exception) => None
      case Success(value) => Some(value)





