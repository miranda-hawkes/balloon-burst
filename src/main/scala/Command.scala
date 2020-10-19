sealed trait Command {
  val value: String
}

case object Inflate extends Command {
  val value: String = "INFLATE"
}

case object Bank extends Command {
  val value: String = "BANK"
}
