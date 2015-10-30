package godiva.slick

import slick.driver.JdbcDriver
import scala.concurrent.ExecutionContext

import slick.jdbc.meta.MTable

trait SchemaManagement {
  this: DriverComponent[JdbcDriver] with DatabaseComponent[JdbcDriver] with TablesSchema with DefaultExecutionContext =>
  import driver.api._

  /**
   * Creates the database schema
   */
  def createTables = database.run(tablesSchema.create)

  /**
   *  Drops all tables from the database schema
   */
  def dropTables = database.run(tablesSchema.drop)

  /**
   * Creates tables in the schema if they not exist.
   *
   * Note: This tries to compute the best schema to create however doesn't take account table changes.
   * It doesn't replace a proper migration infrastructure
   */
  def createTablesIfNotExist = database.run(foldWithMTables(_.isEmpty, _.create))

  /**
   * Drops tables if they exist.
   */
  def dropTablesIfExist = database.run(foldWithMTables(_.nonEmpty, _.drop))

  // code very much inspired  on this gist:
  // https://gist.github.com/davidhoyt/98cbe0a94840373b6e62
  type SchemaDbIoAction[T <: Effect] = DBIOAction[Unit, NoStream, T]

  // Slick as no empty schema description
  // so we do it here.
  private object emptySchema extends driver.SchemaDescription {
    protected override def createPhase1 = Seq.empty
    protected override def createPhase2 = Seq.empty
    protected override def dropPhase1 = Seq.empty
    protected override def dropPhase2 = Seq.empty
  }

  private def dbioActionZero[T <: Effect]: SchemaDbIoAction[T] =
    DBIO.successful(()).asInstanceOf[SchemaDbIoAction[T]]

  // Creates a composed DBIOAction which 
  // Traverses all ttables in the schema, constructing a list with DDL instances.
  // DDL is then merged.
  private def foldWithMTables(predicate: Vector[MTable] => Boolean, fn: driver.SchemaDescription => SchemaDbIoAction[Effect.Schema]) = {
    val schemaActions = tables map { table =>
      MTable.getTables(table.baseTableRow.tableName).map { mtables =>
        if (predicate(mtables)) table.schema else emptySchema
      }
    }
    DBIO.fold(schemaActions, emptySchema)(_ ++ _) flatMap {
      case t if t == emptySchema => dbioActionZero[Effect.Schema]
      case schema => fn(schema)
    }
  }
}

