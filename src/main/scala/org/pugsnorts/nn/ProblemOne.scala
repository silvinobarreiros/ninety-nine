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

    if (list.isEmpty) {
      return last
    } else if (list.length == 1) {
      last = list(0)
    } else {
      last = list(list.length - 1)
    }

    last
  }
}
