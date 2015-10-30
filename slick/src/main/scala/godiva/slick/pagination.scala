package godiva.slick

import slick.driver.JdbcDriver

import godiva.core.pagination._

trait Pagination {
  this: DriverComponent[JdbcDriver] with DefaultExecutionContext =>
  import driver.api._

  implicit class PaginationExtensionMethods[T <: Table[_], U](query: Query[T, U, Seq]) {
    /**
     * Limits the query result to get a specific page
     * Note: Queries should be already sorted
     *
     *
     */
    def paginatedQuery(page: Page) = query.drop((page.number - 1) * page.size).take(page.size)

    def paginated(request: PaginationRequest) = (paginatedQuery(request.page).result zip pageTotals(request)) map (PaginatedResult[U](_: Seq[U], request.page, _: Option[PaginationTotals])).tupled

    /**
     * Utility method to get the number of elements and pages for a query
     */
    def pageTotals(request: PaginationRequest) = if (request.returnTotals) query.size.result map { c => Some(PaginationTotals(c, math.ceil(c.toDouble / request.page.size).toLong)) } else DBIO.successful(None)
  }
}
