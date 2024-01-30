package mower

import moving.{Move, Orientation, Position}

case class Mower(pos: Position, orientation: Orientation, moves: List[Move])
