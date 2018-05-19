package com.stash.nn

object ProblemSixtyOne {

  sealed trait Tree[+T]

  case class Node[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T]
  case object End extends Tree[Nothing]
  
  object Node {
    def apply[T](value: T): Node[T] = Node(value, End, End)

     def leafCount[T](tree: Tree[T]): Int = {

       tree match {
         case Node(_, End, End) => 1
         case Node(_, left, right) => leafCount(left) + leafCount(right)
         case _ => 0
       }
     }
  }
}