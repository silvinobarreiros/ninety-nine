package org.pugsnorts.nn

import org.scalatest._

class ProblemOneSpec extends FlatSpec with Matchers {
  
  trait Fixture {
    def last[A]: List[A] => Option[A] = ProblemOne.lastV5[A]
  }

  "last" should "give me back None when the list is empty" in new Fixture { fix =>
    
    fix.last[Double](List.empty[Double]) shouldBe(None)
  }

  it should "return the only element in a single element array" in new Fixture { fix =>

    fix.last[Double](List(1.0D)) shouldBe Some(1.0D)
  }

  it should "return the last element in an array with things" in new Fixture { fix =>

    fix.last[Double](List(1.0D, 2.0D, 3.0D)) shouldBe Some(3.0D)
  }
}
