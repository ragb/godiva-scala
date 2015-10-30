package godiva.slick

import slick.driver.JdbcDriver
import slick.profile.SqlProfile
import scala.concurrent.ExecutionContext
import scala.language.higherKinds

/**
 * Provides the JDBC driver implementation
 */
trait DriverComponent[+T <: JdbcDriver] {
  val driver: T
}

trait DatabaseComponent[+T <: JdbcDriver] {
  val database: T#API#Database
}

/**
 * Marks a trait/class as having table schema
 * Note: take care when stacking traits of this kind to add all needed tables.
 */
trait TablesSchema {
  this: DriverComponent[JdbcDriver] =>
  import driver.api._

  /**
   * Tables to include on the schema
   * Note: TableQuery is invariant so we need this type bound to work.
   */
  def tables: Seq[TableQuery[_ <: Table[_]]]
  require(tables nonEmpty)
  final def tablesSchema: driver.SchemaDescription = tables.map(_.schema).reduce(_ ++ _)
}

/**
 * Utility trait to inject an implicit execution context
 */
trait DefaultExecutionContext {
  implicit val executionContext: ExecutionContext
}

/**
 * Utilities to process and modify queries based on option values.
 * This are mostly syntax helpers.
 */
object MaybeUtils {
  import slick.lifted._

  implicit class MaybeExtensionMethods[E, U, C[_]](query: Query[E, U, C]) {
    def maybeCompose[T](op: Option[T])(f: T => Query[E, U, C] => Query[E, U, C]) = {
      op.map(o => f(o)(query)) getOrElse (query)
    }

    def maybeFilter[T, R <: Rep[_]](op: Option[T])(f: T => E => R)(implicit w: CanBeQueryCondition[R]) = {
      op.map(o => query.filter(f(o))) getOrElse (query)
    }
  }

}
