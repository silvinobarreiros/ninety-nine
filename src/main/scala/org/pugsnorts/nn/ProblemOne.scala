package org.pugsnorts.nn

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
}