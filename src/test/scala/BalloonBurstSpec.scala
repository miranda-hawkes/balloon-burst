import java.io.{ByteArrayOutputStream, StringReader}

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BalloonBurstSpec extends AnyWordSpec with Matchers {

  "BalloonBurst .inflateBalloon" when {

    "balloon is inflated to the max limit" should {

      val inputStr =
        """|INFLATE
           |INFLATE
           |INFLATE
           |BANK
        """.stripMargin

      val input = new StringReader(inputStr)
      val output = new ByteArrayOutputStream()

      "return max score" in {
        Console.withOut(output) {
          Console.withIn(input)(BalloonBurst.inflateBalloon(3, 0)) shouldBe 3
        }
      }

      "output correct prompts" in {
        output.toString should (
          include ("Enter INFLATE or BANK") and
          include ("Current balloon size: 0") and
          include ("Current balloon size: 1") and
          include ("Current balloon size: 2") and
          include ("Current balloon size: 3")
        )
      }
    }

    "balloon is inflated to a limit below the max" should {

      val inputStr =
        """|INFLATE
           |INFLATE
           |BANK
        """.stripMargin

      val input = new StringReader(inputStr)
      val output = new ByteArrayOutputStream()

      "return score as number of times inflated" in {
        Console.withOut(output) {
          Console.withIn(input)(BalloonBurst.inflateBalloon(3, 0)) shouldBe 2
        }
      }

      "output correct prompts" in {
        output.toString should (
          include ("Enter INFLATE or BANK") and
          include ("Current balloon size: 0") and
          include ("Current balloon size: 1") and
          include ("Current balloon size: 2")
        )
      }
    }

    "balloon is inflated past the max" should {

      val inputStr =
        """|INFLATE
           |INFLATE
           |INFLATE
           |INFLATE
        """.stripMargin

      val input = new StringReader(inputStr)
      val output = new ByteArrayOutputStream()

      "return 0 score" in {
        Console.withOut(output) {
          Console.withIn(input)(BalloonBurst.inflateBalloon(3, 0)) shouldBe 0
        }
      }

      "output correct prompts" in {
        output.toString should (
          include ("Enter INFLATE or BANK") and
          include ("Current balloon size: 0") and
          include ("Current balloon size: 1") and
          include ("Current balloon size: 3") and
          include ("!!! BURST !!!")
        )
      }
    }

    "invalid characters are entered" should {

      val inputStr =
        """|INFLATE
           |INVALID PROMPT
           |INFLATE
           |BANK
        """.stripMargin

      val input = new StringReader(inputStr)
      val output = new ByteArrayOutputStream()

      "continue with game after a valid command is entered" in {
        Console.withOut(output) {
          Console.withIn(input)(BalloonBurst.inflateBalloon(3, 0)) shouldBe 2
        }
      }

      "display prompt" in {
        output.toString should include ("Not a valid input: 'INVALID PROMPT'")
      }
    }
  }

  "BalloonBurst .start" when {

    "max score is achieved" should {

      val inputStr =
        """|abcdef
           |2 2 3
           |INFLATE
           |INFLATE
           |BANK
           |INFLATE
           |INFLATE
           |BANK
           |INFLATE
           |INFLATE
           |INFLATE
           |BANK
        """.stripMargin

      val input = new StringReader(inputStr)
      val output = new ByteArrayOutputStream()

      Console.withOut(output) {
        Console.withIn(input)(BalloonBurst.start())
      }

      "output correct prompts and total score" in {
        output.toString should (
          include ("Press ^C to quit") and
          include ("Enter balloon inflate limits separated by space, e.g. 10 2 4 6 9 2") and
          include ("Final score: 7")
        )
      }
    }

    "one or more balloons are burst" should {

      val inputStr =
        """|abcdef
           |2 2 3
           |INFLATE
           |INFLATE
           |INFLATE
           |BANK
           |INFLATE
           |INFLATE
           |INFLATE
           |BANK
           |INFLATE
           |INFLATE
           |INFLATE
           |BANK
        """.stripMargin

      val input = new StringReader(inputStr)
      val output = new ByteArrayOutputStream()

      Console.withOut(output) {
        Console.withIn(input)(BalloonBurst.start())
      }

      "output correct prompts and total score" in {
        output.toString should (
          include ("Press ^C to quit") and
          include ("Enter balloon inflate limits separated by space, e.g. 10 2 4 6 9 2") and
          include ("!!! BURST !!!") and
          include ("Final score: 3")
        )
      }
    }

    "invalid balloon inflate limits are entered" should {

      val inputStr =
        """|abcdef
           |1 1
           |INFLATE
           |BANK
           |INFLATE
           |BANK
        """.stripMargin

      val input = new StringReader(inputStr)
      val output = new ByteArrayOutputStream()

      Console.withOut(output) {
        Console.withIn(input)(BalloonBurst.start())
      }

      "display prompt and continue with game" in {
        output.toString should (
          include ("Not a valid input: 'abcdef'") and
          include ("Final score: 2")
        )
      }
    }
  }
}
