package scalafunctionalkoans

import scalafunctionalkoans.support.KoanSuite
import org.scalatest._

import scala.collection.immutable

class Koans extends Sequential {
  override val nestedSuites: immutable.IndexedSeq[KoanSuite] =
    List(
      new AboutVal,
      new AboutExpressions,
      new AboutInstructions,
      new AboutMethods,
      new AboutClasses,
      new AboutHigherOrderFunctions,
      new AboutTuples,
      new AboutImmutableCaseClasses,
      new AboutImmutableIndexedSequences,
      new AboutRanges,
      new AboutImmutableSets,
      new AboutOptions,
      new AboutPatternMatching,
      new AboutPartialFunctions,
      new AboutImmutableMaps
    ).toIndexedSeq
}
