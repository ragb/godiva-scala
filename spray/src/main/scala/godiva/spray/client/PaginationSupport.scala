package godiva.spray.client

import godiva.core.pagination._
import spray.http._

trait PaginationSupport {
  implicit class UriPaginationSupport(uri: Uri) {
    def withPagination(pagination: PaginationRequest) = {
      val params = Seq[(String, String)](
        "pageNumber" -> pagination.page.number.toString,
        "pageSize" -> pagination.page.size.toString,
        "pageTotals" -> pagination.returnTotals.toString
      )
      uri.withQuery((uri.query ++ params): _*)
    }
  }

}

object PaginationSupport extends PaginationSupport
