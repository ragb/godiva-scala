package godiva.spray.pagination
import spray.routing._
import spray.routing.Directives._
import shapeless._
import godiva.core.pagination.{ PaginationRequest, Page }

/**
 * Spray directives to use with pagination
 */
trait PaginationDirectives {

  val defaultPageSize = 10

  def paginated: Directive1[PaginationRequest] = readPageParameters.hmap {
    case pageNumber :: pageSize :: pageTotals :: HNil => PaginationRequest(Page(pageNumber.getOrElse(1), pageSize.getOrElse(defaultPageSize)), pageTotals.getOrElse(true))
  }

  private val readPageParameters: Directive[Option[Long] :: Option[Long] :: Option[Boolean] :: HNil] = parameters("pageNumber".as[Long].? :: "pageSize".as[Long].? :: "pageTotals".as[Boolean].? :: HNil)

}

object PaginationDirectives extends PaginationDirectives
