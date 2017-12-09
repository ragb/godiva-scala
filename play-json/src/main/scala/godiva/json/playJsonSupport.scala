package godiva.json

import play.api.libs.json._
import play.api.libs.functional.syntax._

import godiva.core.pagination._

trait PlayJsonProtocol {
  implicit val pageFormat = Json.format[Page]
  implicit val paginationTotalsFormat = Json.format[PaginationTotals]
  implicit def paginatedResultWrites[T: Writes]: Writes[PaginatedResult[T]] = (
    (__ \ 'elements).write[Seq[T]] ~
    (__ \ 'page).write[Page] ~
    (__ \ 'paginationTotals).write[Option[PaginationTotals]])(unlift(PaginatedResult.unapply[T]))

  def paginatedResultReads[T: Reads]: Reads[PaginatedResult[T]] = (
    (__ \ "elements").read[Seq[T]] ~
    (__ \ "page").read[Page] ~
    (__ \ "paginationTotals").readNullable[PaginationTotals])(PaginatedResult.apply[T] _)
}

object PlayJsonProtocol extends PlayJsonProtocol
