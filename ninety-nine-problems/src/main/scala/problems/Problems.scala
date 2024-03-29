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

  def rotate[A](n: Int, l: List[A], isRec: Boolean = false) : List[A] = if isRec then
    @tailrec
    def iter[B](n: Int, innerL: List[B], acc: List[B]): List[B] =
      if n > 0 then
        iter(n - 1, innerL.tail, acc :+ innerL.head)
      else if n == 0 then
        innerL :++ acc
      else
        iter(innerL.length + n, innerL, Nil)
    iter(n, l, Nil)

  else //if not recursive
    if n > 0 then
      val (begin, end) = split(n, l)
      end ++ begin
    else if n == 0 then
      l
    else
      val (begin, end) = split(n * -1, l.reverse)
      begin.reverse ++ end.reverse
      //rotate(l.length + n, l) // is also valid instead of using split ! but rotate would be tailrec then

  def insertAt[A](item: A, at: Int, l: List[A], isRec: Boolean = false ) : List[A] =
    if isRec then
      @tailrec
      def iter(it: A, n: Int, innerL: List[A], acc: List[A]) : List[A] =
        if n == 0 then
          (acc :+ item) ++: innerL
        else
          iter(it, n-1, innerL.tail, acc :+ innerL.head )
      iter(item, at, l, Nil)
    else
      val (firstPart, secondPart) = l.splitAt(at)
      firstPart :++ (item +: secondPart)


  def lSort[A](l : List[List[A]]) : List[List[A]] = l.sortBy(_.length)

  def lSortFreq[A](l : List[List[A]]) : List[List[A]] =
    l.groupBy(identity )
      .map{(_,v) =>  v -> v.length}
      .toList
      .sortBy{(_, v) =>  v}
      .flatMap{(k, _) =>  k}

  @tailrec
  def gcd(a: Int, b: Int) : Int = if b == 0 then a else gcd(b, a % b)

  extension (i: Int)
    def isPrime: Boolean = (1 to Math.sqrt(i.toDouble).toInt).inclusive.count(i % _ == 0) < 3
    def isCoprimeTo(that: Int): Boolean = gcd(i, that) == 1
    def totient: Int = (1 to i).map(_.isCoprimeTo(i)).count(_ == true)
    def primeFactors: List[Int] =
      def iter(n: Int, lToSqrt: List[Int], acc: List[Int]) : List[Int] = ???
     /*   if n.isPrime then
          println(s"is prime $n ${n.isPrime}")
          acc
        else if n % lToSqrt.head == 0 then
          println(s"else if $n $lToSqrt $acc")
          iter(n / lToSqrt.head, lToSqrt, acc :+ lToSqrt.head)
        else
          println(s"else $n $lToSqrt $acc")
          iter(n , lToSqrt.tail, acc)
      /*lToSqrt match
        case ::(head, next) =>
          if n.isCoprimeTo(head) then
            iter(n / head, lToSqrt, acc :+ head)
          else
            iter(n, lToSqrt.tail, acc)
        case Nil => acc*/*/
      iter(i, (1 to Math.sqrt(i.toDouble).toInt).toList.reverse, Nil)