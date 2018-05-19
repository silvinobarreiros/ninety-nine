package com.stash.nn

/**
 * Find the last element of a list.
 */
object ProblemOne {
  
  /**
   * return the last element of the list, if empty return Int.MaxValue
   */
  def lastV1(list: List[Int]): Int = {
    var last = Int.MaxValue

    if (list.length == 1) {
      last = list(0)
    } else if (list.nonEmpty) {
      last = list(list.length - 1)
    }

    last
  }

  def lastV2(list: List[Int]): Int = {
    if (list.length == 1) {
      list(0)
    } else if (list.nonEmpty) {
      list(list.length - 1)
    } else {
      Int.MinValue
    }
  }

  def lastV3[A >: Null](list: List[A]): A = {
    if (list.length == 1) {
      list(0)
    } else if (list.nonEmpty) {
      list(list.length - 1)
    } else {
      null
    }
  }

  def lastV4[A](list: List[A]): Option[A] = {
    if (list.length == 1) {
      Some(list(0))
    } else if (list.nonEmpty) {
      Some(list(list.length - 1))
    } else {
      None
    }
  }

  def lastV5[A](list: List[A]): Option[A] = list match {
    case Nil => None
    case ::(head , Nil) => Some(head)
    case head :: tail => lastV5(tail)
  }

  def lastV6[A](list: List[A]): A = list.last
}
