package com.simpleblockchain.db

import java.security.MessageDigest
import java.sql.Timestamp
import java.time.Instant
import java.util.UUID

import com.typesafe.scalalogging.LazyLogging
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration.DurationDouble
import scala.concurrent.{Await, Future}

object DbOp extends LazyLogging{

  private val db = Database.forConfig("postgres")
  private val duration = 2.seconds

  def lastBlockHash(): Future[String] = {
    db.run(
      BlockChainSchema
        .sortBy(_.id.reverse)
        .map(x => x.hash)
        .result
        .head)
  }

  def block(hash: String): Future[Option[BlockChain]] = {
    db.run(BlockChainSchema
      .filter(_.hash === hash)
      .result
      .headOption)
  }

  def addBlock(block: BlockChain): Future[Int] = {
    db.run(BlockChainSchema += block)
  }

  def chainLength(): Future[Int] = {
    db.run(BlockChainSchema.length.result)
  }

  def insertGenesis(): Unit = {
    val len = Await.result(chainLength(), duration)
    logger.info(s"length is $len")
    if (len == 0) {
      logger.info("# Inserting genesis #")
      Await.ready(addBlock(
                    BlockChain(0,
                               md5Hash(UUID.randomUUID().toString),
                               Timestamp.from(Instant.now()))),
                  duration)
    }
  }

  private def md5Hash(str: String) = {
    MessageDigest.getInstance("MD5").digest(str.getBytes).toString
  }
}
