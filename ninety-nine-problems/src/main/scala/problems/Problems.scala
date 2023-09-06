package problems

import scala.annotation.tailrec

object Problems :
  def last(l: List[Int], isRec: Boolean = false): Int =
    if isRec then
      @tailrec
      def iter(innerL: List[Int], acc: Int): Int = innerL match
        case head :: next => iter(next, head)
        case Nil => acc

      iter(l, 0)
    else
      l.last
  def penultimate(l: List[Int], isRec: Boolean = false): Int =
    if isRec then
      @tailrec
      def iter(innerL: List[Int], acc: Int): Int = innerL match
        case head :: next :: tail => iter(tail, next)
        case _ => acc

      iter(l, 0)
    else
      l.findLast(_ < l.last).getOrElse(0)

  def nth(n: Int, l: List[Int], isRec : Boolean = false): Int =
    if isRec then
      @tailrec
      def iter(n: Int, innerL: List[Int], acc: Int): Int = n match
        case 0 => acc
        case _ => iter(n-1, innerL.tail, innerL.head)

      iter(n, l, 0)
    else
      l.zipWithIndex
        .filter((_, i) => i == n - 1)
        .map((nb, _) => nb)
        .headOption.getOrElse(0)