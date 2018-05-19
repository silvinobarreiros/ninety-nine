package org.pugsnorts.nn

import org.scalatest._

class ProblemOneSpec extends FlatSpec with Matchers {
  
  trait Fixture {
    def last[A]: List[A] => A = ProblemOne.lastV6[A]
  }

  "last" should "throw an NoSuchElementException when the list is empty" in new Fixture { fix =>
    
    an [NoSuchElementException] should be thrownBy fix.last[Double](List.empty[Double])
  }

  it should "return the only element in a single element array" in new Fixture { fix =>

    fix.last[Int](List(1)) shouldBe 1
  }

  it should "return the last element in an array with things" in new Fixture { fix =>

    fix.last[Int](List(1, 2, 3)) shouldBe 3
  }
}
