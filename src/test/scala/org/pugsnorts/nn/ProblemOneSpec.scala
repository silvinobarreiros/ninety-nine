package org.pugsnorts.nn

import org.scalatest._

class ProblemOneSpec extends FlatSpec with Matchers {
  
  trait Fixture {
    val last: List[Int] => Int = ProblemOne.lastV2
  }

  "last" should "give me back null when the list is empty" in new Fixture { fix =>
    
    fix.last(List.empty[Int]) shouldBe(Int.MaxValue)
  }

  it should "return the only element in a single element array" in new Fixture { fix =>

    fix.last(List(1)) shouldBe 1
  }

  it should "return the last element in an array with things" in new Fixture { fix =>

    fix.last(List(1, 2, 3)) shouldBe 3
  }
}
