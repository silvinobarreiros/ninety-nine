package com.stash.nn

/**
  * Duplicate the elements of a list a given number of times.
  */
object ProblemFifteen {
  
  def duplicateNV1[A](n: Int, list: List[A]): List[A] = {
    import scala.collection.mutable.ListBuffer
    
    val buffer = new ListBuffer[A]()
    var i = 0

    for (i <- 0 until list.length) {
      
      val temp = new ListBuffer[A]()
      var j = 0

      for (j <- 0 until n) {
        buffer += list(i)
      }
    }

    buffer.toList
  }

  def duplicateNV2[A](n: Int, list: List[A]): List[A] = {
    import scala.collection.mutable.ListBuffer
    
    def halp(a: A): List[A] = {
      List.fill(n)(a)
    }

    val buffer = new ListBuffer[A]()
    var i = 0

    for (i <- 0 until list.length) {
      val repeatedList = halp(list(i))
      
      repeatedList.foreach {
        j => buffer += j
      }
    }

    buffer.toList
  }

  def duplicateNV3[A](n: Int, list: List[A]): List[A] = {
    import scala.collection.mutable.ListBuffer

    val buffer = new ListBuffer[A]()
    var i = 0

    for (i <- 0 until list.length) {
      List.fill(n)(list(i)).foreach(j => buffer += j)
    }

    buffer.toList
  }

  def duplicateNV4[A](n: Int, list: List[A]): List[A] = {
    
    list.foldLeft(List.empty[A]) { (acc, next) =>
      acc ++ List.fill(n)(next)
    }
  }

  def duplicateNV5[A](n: Int, list: List[A]): List[A] = {
    list.flatMap { item =>
      List.fill(n)(item)
    }
  }

  // def duplicateNV6[A](n: Int, list: List[A]): List[A] = {
  //   val filler = List.fill[A](n)_

  //   list.flatMap(filler(_))
  // }

  def duplicateNV6[A](n: Int, list: List[A]): List[A] = list.flatMap(List.fill[A](n)(_))
}
