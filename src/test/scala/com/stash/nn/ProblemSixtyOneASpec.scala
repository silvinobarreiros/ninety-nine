package com.stash.nn

import com.stash.nn.ProblemSixtyOneA.Node
import org.scalatest.{FlatSpec, Matchers}

class ProblemSixtyOneASpec extends FlatSpec with Matchers {

  trait Fixture {
    def leafCount[A](node: Node[A]): Int = Node.leafCountV3(node)
  }

  "leaf count" should "return 1 for a tree with 1 node" in new Fixture { fix =>
    val tree = Node("a", null, null)

    fix.leafCount(tree) shouldBe 1
  }

  it should "return 0 for an empty tree" in new Fixture { fix =>
    val tree = null

    fix.leafCount(tree) shouldBe 0
  }

  it should "return 2 for a balance 3 node tree" in new Fixture { fix =>
    val tree = Node("a",
      Node("b", null, null),
      Node("c", null, null)
    )

    fix.leafCount(tree) shouldBe 2
  }

  it should "return 1 for a balance left biased tree" in new Fixture { fix =>
    val tree = Node("a",
      Node("b",
        Node("c", null, null),
        null
      ),
      null
    )

    fix.leafCount(tree) shouldBe 1
  }
}
