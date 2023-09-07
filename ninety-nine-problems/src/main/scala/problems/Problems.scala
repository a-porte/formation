package problems

import problems.Problems.flatten

import scala.annotation.tailrec
import scala.runtime.Nothing$

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
      
  def compress[A](l: List[A]): List[A] = l.foldRight(List[A]()) {
    case (element, acc) => acc.headOption match
      case Some(value) if value == element => acc
      case _ => element +: acc
  }

  def pack[A](l: List[A]) : List[List[A]] = l.foldRight(List[List[A]]()) {
    case (element, ll) => ll.headOption match
      case Some(value) if value.head == element => (element +: value) +: ll.tail
      case _ => (element +: Nil) +: ll
  }

 // should use pack method
  def encode[A](l: List[A]): List[(Int, A)]=
    pack(l).map(e => e.length -> e.head)

  def encodeModified(l: List[Any]): List[Any] =
    encode(l).map{
      (nb, c) => if nb > 1 then (nb, c) else c
    }

  def decode[A](l: List[(Int, A)]): List[A] =
    l.flatMap ((nb, c) => List.tabulate(nb)(_ => c))


  def duplicatedN[A](n: Int, l: List[A]) : List[A] = l.flatMap(e => List.tabulate(n)(_ => e))

  def drop[A](n: Int, l: List[A]) : List[A] =
    l.zipWithIndex.collect{case (e, i) if (i+1) % n != 0 => e}
      //.filterNot((e, i) => (1+i) % n == 0)
      //.map((e, i) => e)


  def split[A](n: Int, l: List[A]) : (List[A],List[A]) = l.splitAt(n)

  def slice[A](fromExclude: Int, toIncluded: Int, l: List[A]) : List[A] =
    l.dropRight(l.length - toIncluded - 1).drop(fromExclude)

  def rotate[A](n: Int, l: List[A]) : List[A] =
    if n > 0 then
      val (begin, end) = split(n,l)
      end ++ begin
    else
      val (begin, end) = split(n * -1,l.reverse)
      begin.reverse ++ end.reverse
