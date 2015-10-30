/**
 * Types to help with pagination
 */

package godiva.core.pagination

/**
 * Represents a page with number and size (maximum number of elements)
 *
 * Note: number starts from one.
 */
case class Page(number: Long, size: Long)

/**
 * Represents information about total number of elements in a collection and total pages.
 */
case class PaginationTotals(totalElements: Long, totalPages: Long)

/**
 * Pagination information to be requested.
 *
 * @param page the page to request
 * @param returnTotals if true return also information about total elements. These usually implies additional requests to a persistence store.
 */
case class PaginationRequest(page: Page, returnTotals: Boolean = true)

/**
 * Represents a page of elements with pagination information.
 */
case class PaginatedResult[T](elements: Seq[T], page: Page, totals: Option[PaginationTotals])
