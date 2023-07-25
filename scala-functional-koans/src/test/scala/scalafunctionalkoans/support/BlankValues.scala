package scalafunctionalkoans.support

import org.scalatest.exceptions.TestPendingException
import org.scalatest.matchers.Matcher

object BlankValues {
  class ReplaceWithCorrectException extends Exception

  def __ : Matcher[Any] = {
    throw new TestPendingException
  }

  class ___ extends ReplaceWithCorrectException {
    override def toString: String = "___"
  }
}

object Blankout {
  def blank[T](t: T): T = t
}
