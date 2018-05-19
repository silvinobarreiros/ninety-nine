package org.pugsnorts.nn

import org.scalatest._

class ProblemOneSpec extends FlatSpec with Matchers {
  
  trait Fixture {
    def last[A >: Null]: List[A] => A = ProblemOne.lastV3[A]
  }

  "last" should "give me back null when the list is empty" in new Fixture { fix =>
    
    fix.last[String](List.empty[String]) shouldBe(null)
  }

  it should "return the only element in a single element array" in new Fixture { fix =>

    fix.last[String](List("one")) shouldBe "one"
  }

  it should "return the last element in an array with things" in new Fixture { fix =>

    fix.last[String](List("one", "two", "three")) shouldBe "three"
  }
}
