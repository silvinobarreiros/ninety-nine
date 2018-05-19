package com.stash.nn

import org.scalatest._
import com.stash.nn.ProblemSixtyOne._

class ProblemSixtyOneSpec extends FlatSpec with Matchers {
  
  trait Fixture {
    def leafCount[A](tree: Tree[A]): Int = Node.leafCount(tree)
  }

  "leaf count" should "return 1 for a tree with 1 node" in new Fixture { fix =>
    val tree = Node("a")

    fix.leafCount(tree) shouldBe 1
  }

  it should "return 0 for an empty tree" in new Fixture { fix =>
    val tree = End

    fix.leafCount(tree) shouldBe 0
  }

   it should "return 2 for a balance 3 node tree" in new Fixture { fix =>
     val tree = Node("a",
       Node("b"),
       Node("c")
     )

     fix.leafCount(tree) shouldBe 2
   }

   it should "return 1 for a balance left biased tree" in new Fixture { fix =>
     val tree = Node("a",
       Node("b",
         Node("c"),
         End
       ),
       End
     )

     fix.leafCount(tree) shouldBe 1
   }
}
