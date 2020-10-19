import scala.io.StdIn.readLine
import scala.annotation.tailrec

object BalloonBurst extends App {

  @tailrec
  def inflateBalloon(balloonLimit: Int, currentSize: Int): Int = {

    println(s"\nCurrent balloon size: $currentSize")
    println("Enter INFLATE or BANK\n")

    InputParser.readCommand(readLine()) match {
      case Some(Inflate) =>
        if(currentSize == balloonLimit) {
          println("\n!!! BURST !!!")
          0
        } else inflateBalloon(balloonLimit, currentSize + 1)
      case Some(Bank) => currentSize
      case None => inflateBalloon(balloonLimit, currentSize)
    }
  }

  @tailrec
  def start(): Unit = {

    println("Press ^C to quit")
    println("Enter balloon inflate limits separated by space, e.g. 10 2 4 6 9 2\n")

    InputParser.parseLimits(readLine()) match {
      case Some(value) =>

        val balloonLimits: Seq[Int] = value
        val finalScore = (for(balloon <- balloonLimits) yield inflateBalloon(balloon, 0)).sum

        println(s"\nFinal score: $finalScore")

        Thread.sleep(1000)

      case None => start()
    }
  }

  start()
}