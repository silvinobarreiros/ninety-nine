package com.stash.nn

import org.scalatest._

class ProblemFifteenSpec extends FlatSpec with Matchers {
  
  trait Fixture {
    def duplicateN[A]: (Int, List[A]) => List[A] = ProblemFifteen.duplicateNV6[A]
  }

  "duplicateN" should "return an empty list when there are no elements" in new Fixture { fix =>
    
    fix.duplicateN(1, List.empty[Int]) shouldBe List.empty[Int]
  }

  it should "return the same array when N is 1" in new Fixture { fix =>
    
    fix.duplicateN(1, List(1, 2, 3)) shouldBe List(1, 2, 3)
  }

  it should "return duplicate enteries when N is 2" in new Fixture { fix =>

    fix.duplicateN(2, List(1, 2, 3)) shouldBe List(1, 1, 2, 2, 3, 3)
  }
}
