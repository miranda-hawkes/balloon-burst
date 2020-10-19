import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class InputParserSpec extends AnyWordSpec with Matchers {

  "InputParser .parseLimits" when {

    "input is a valid Int sequence" should {

      "return sequence of Ints" in {
        InputParser.parseLimits("1 2 3 4 10") shouldBe Some(Seq(1, 2, 3, 4, 10))
      }
    }

    "input is invalid" should {

      "return None" in {
        InputParser.parseLimits("abc abc") shouldBe None
      }
    }
  }

  "InputParser .readCommand" when {

    "input is INFLATE" should {

      "return sequence of Ints" in {
        InputParser.readCommand("INFLATE") shouldBe Some(Inflate)
      }
    }

    "input is BANK" should {

      "return None" in {
        InputParser.readCommand("BANK") shouldBe Some(Bank)
      }
    }

    "input is unrecognised" should {

      "return None" in {
        InputParser.readCommand("junk") shouldBe None
      }
    }
  }
}
