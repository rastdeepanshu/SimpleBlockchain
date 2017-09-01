package com.simpleblockchain.db

import java.sql.Timestamp

import slick.jdbc.PostgresProfile.api._

case class BlockChain(id:Int, hash: String, dateTime: Timestamp)

class BlockChainSchema(tag: Tag) extends Table[BlockChain](tag, "blockchain"){
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def hash = column[String]("hash", O.Unique)
  def dateTime = column[Timestamp]("datetime")
  def hashIndex = index("hashidx", hash, unique = true)
  def * = (id, hash, dateTime) <> (BlockChain.tupled, BlockChain.unapply)
}

object BlockChainSchema extends TableQuery(new BlockChainSchema(_))

