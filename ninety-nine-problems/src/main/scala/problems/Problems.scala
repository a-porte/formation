package problems

import problems.Problems.flatten

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
        case head :: next :: tail => iter(tail, head)
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


  def isPalindrome[A](l: List[A], isRec : Boolean = false): Boolean =
    if isRec then //A is B but compiler warns about a suspicious shadowing type regarding iter method
      @tailrec
      def iter[B](innerL: List[B], begin: Option[B], end: Option[B], acc: Boolean = true) : Boolean =
        innerL match
          case Nil | _ :: Nil => acc // the alternative is to match single innerList i.e. when l List has an odd number of elements
          case ::(head, next) =>
            iter(
              innerL.filterNot((e:B) => (e == innerL.last) || (e == head)),
              Option(head),
              Option(innerL.last),
              acc && (begin == end)
            )

      l.headOption match
        case Some(_) => iter(l, None, None)
        case None => false

    else
      l.reverse == l
    //l.foldRight(List[Char]())((element, acc) =>  acc :+ element) // is also valid
    
  def flatten(l :List[Any]): List[Any] =
    l match
      case head :: next => head match
        case nested: List[_] => flatten(nested) ::: flatten(next)
        case  simple:Any => simple:: flatten(next)
      case Nil => Nil