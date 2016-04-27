package godiva.akka.http

import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

import godiva.core.pagination._

trait PaginationDirectives {

  val defaultPageSize = 10

  def paginated: Directive1[PaginationRequest] = readPageParameters.tmap {
    case (pageNumber, pageSize, pageTotals) => PaginationRequest(Page(pageNumber.getOrElse(1), pageSize.getOrElse(defaultPageSize)), pageTotals.getOrElse(true))
  }

  private val readPageParameters: Directive[(Option[Long], Option[Long], Option[Boolean])] = parameters("pageNumber".as[Long].?, "pageSize".as[Long].?, "pageTotals".as[Boolean].?)

}

object PaginationDirectives extends PaginationDirectives
