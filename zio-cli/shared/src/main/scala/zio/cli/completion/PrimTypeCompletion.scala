package zio.cli.completion

import zio._
import zio.cli.PathType
import zio.cli.PrimType

import java.time.{ZoneId => JZoneId}
import scala.jdk.CollectionConverters._

object PrimTypeCompletion {
  def firstTokens[R, E](
    primType: PrimType[Any],
    prefix: String,
    compgen: Compgen,
    completer: Completer
  ): ZIO[R, E, Set[String]] =
    primType match {
      case PrimType.Bool(_) =>
        ZIO.succeed(Set("true", "false").filter(_.startsWith(prefix))).map(appendSpaces)
      case PrimType.Decimal =>
        completer.complete
      case PrimType.Duration =>
        completer.complete
      case PrimType.Enumeration(cases @ _*) =>
        ZIO
          .succeed(cases.collect {
            case (name, _) if name.startsWith(prefix) => name
          }.toSet)
          .map(appendSpaces)
      case PrimType.Instant =>
        completer.complete
      case PrimType.Integer =>
        completer.complete
      case PrimType.LocalDate =>
        completer.complete
      case PrimType.LocalDateTime =>
        completer.complete
      case PrimType.LocalTime =>
        completer.complete
      case PrimType.MonthDay =>
        completer.complete
      case PrimType.OffsetDateTime =>
        completer.complete
      case PrimType.OffsetTime =>
        completer.complete
      case PrimType.Path(PathType.Either | PathType.File, _, _) =>
        compgen.completeFileNames(prefix).map(_.toSet).orDie
      case PrimType.Path(PathType.Directory, _, _) =>
        compgen.completeDirectoryNames(prefix).map(_.toSet).orDie
      case PrimType.Period =>
        completer.complete
      case PrimType.Text =>
        completer.complete
      case PrimType.Year =>
        completer.complete
      case PrimType.YearMonth =>
        completer.complete
      case PrimType.ZoneId =>
        ZIO.succeed(JZoneId.getAvailableZoneIds().iterator.asScala.filter(_.startsWith(prefix)).toSet).map(appendSpaces)
      case PrimType.ZoneOffset =>
        completer.complete
      case PrimType.ZonedDateTime =>
        completer.complete
    }

  def appendSpaces(tokens: Set[String]): Set[String] = tokens.map(_ + " ")
}
