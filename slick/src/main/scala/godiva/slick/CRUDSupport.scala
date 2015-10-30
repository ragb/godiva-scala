package com.sphonic.wfm2.repo.dal

import slick.driver.JdbcDriver
import slick.ast.TypedType
import scala.language.{ reflectiveCalls, higherKinds }

trait CRUDSupport {
  this: DriverComponent[_ <: JdbcDriver] =>
  import driver.api._

  // TODO: this should be parameterized
  // Can't get around the trait context bounds limitation, so use a constant id type for now
  type IdType = java.util.UUID
  type WithId[T] = { def id: T }

  trait CRUDTable[T] {
    this: Table[T] =>
    def id = column[IdType]("id", O.PrimaryKey)
  }

  /**
   * CRUD basic queries
   * Note: the `U` parameter may be redundant as it is T#TableElementType, however I can't get the compiler
   * to understand it.
   */
  trait CRUDTableQuery[U <: WithId[IdType], T <: Table[U] with CRUDTable[U]] {
    table: TableQuery[T] with CRUDTableQuery[U, T] =>

    lazy val findById = table.findBy(_.id)
    def get(id: IdType) = findById(id).result.headOption
    def delete(id: IdType) = findById(id).delete
    // Return the Id value.
    def create(element: U) = table += element
    def update(u: U) = findById(u.id).update(u)
  }
}
