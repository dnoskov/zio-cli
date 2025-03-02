package zio.cli.completion

import zio._

trait Completable {
  def completer: Option[Completer]
}
final case class Completer(complete: UIO[Set[String]])
object Completer {
  val Empty = Completer(ZIO.succeed(Set.empty))
}
