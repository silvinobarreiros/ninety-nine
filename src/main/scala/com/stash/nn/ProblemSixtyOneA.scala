package com.stash.nn

object ProblemSixtyOneA {
  case class Node[+T](value: T, left: Node[T], right: Node[T])

  object Node {
    
    def leafCountV1[T](node: Node[T]): Int = {
      import scala.collection.mutable

      val queue = new mutable.Queue[Node[T]]
      var temp = node

      var count = 0

      while(temp != null) {
        if (temp.left != null) {
          queue += temp.left
        }

        if (temp.right != null) {
          queue += temp.right
        }

        if (temp.right == null && temp.left == null) {
          count = count + 1
        }

        temp = if (queue.nonEmpty) queue.dequeue() else null
      }

      count
    }

    def leafCountV2[T](node: Node[T]): Int = {
      if (node == null) {
        0
      } else if (node.left == null && node.right == null) {
        1
      } else {
        leafCountV2(node.left) + leafCountV2(node.right)
      }
    }

    def leafCountV3[T](node: Node[T]): Int = {
      node match {
        case null => 0
        case Node(_, null, null) => 1
        case Node(_, left, right) => leafCountV3(left) + leafCountV3(right)
      }
    }
  }
}