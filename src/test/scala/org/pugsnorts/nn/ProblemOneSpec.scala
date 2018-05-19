package org.pugsnorts.nn

import org.scalatest._

class ProblemOneSpec extends FlatSpec with Matchers {
  
  trait Fixture {
    val last: List[Int] => Int = ProblemOne.lastV1
  }

  "last" should "give me back null when the list is empty" in new Fixture { fix =>

    fix.last(List.empty[Int]) shouldBe(Int.MaxValue)
  }
}
