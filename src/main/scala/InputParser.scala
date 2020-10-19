import scala.util.{Success, Try}
import OutputFormatter._

object InputParser {

  def parseLimits(input: String): Option[Seq[Int]] = {
    Try(input.split(" ").map(_.toInt)) match {
      case Success(value) => Some(value)
      case _ =>
        printError(input)
        None
    }
  }

  def readCommand(input: String): Option[Command] = {
    input.trim match {
      case Inflate.value => Some(Inflate)
      case Bank.value => Some(Bank)
      case _ =>
        printError(input)
        None
    }
  }
}
