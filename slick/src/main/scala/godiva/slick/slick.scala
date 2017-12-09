package godiva.slick

import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext
import scala.language.higherKinds

/**
 * Provides the JDBC driver implementation
 */
trait DriverComponent[+T <: JdbcProfile] {
  val driver: T
}

trait DatabaseComponent[+T <: JdbcProfile] {
  val database: T#API#Database
}

/**
 * Marks a trait/class as having table schema
 * Note: take care when stacking traits of this kind to add all needed tables.
 */
trait TablesSchema {
  this: DriverComponent[JdbcProfile] =>
  import driver.api._

  /**
   * Tables to include on the schema
   * Note: TableQuery is invariant so we need this type bound to work.
   */
  def tables: Seq[TableQuery[_ <: Table[_]]]
  require(tables nonEmpty)
  final def tablesSchema: driver.SchemaDescription = tables.map(_.schema).reduce(_ ++ _)
  def createAdditionalActions: Seq[DBIO[Int]] = Seq.empty
  def dropAdditionalActions: Seq[DBIO[Int]] = Seq.empty

}

/**
 * Utility trait to inject an implicit execution context
 */
trait DefaultExecutionContext {
  implicit def executionContext: ExecutionContext
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
