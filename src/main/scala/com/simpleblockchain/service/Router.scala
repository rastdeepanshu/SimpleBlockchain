package com.simpleblockchain.service

import java.sql.Timestamp
import java.time.Instant

import com.simpleblockchain._
import com.simpleblockchain.db.{BlockChain, DbOp}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

sealed trait TxType

case class Transaction(fromAddress: String, toAddress: String, amount: Double) extends TxType

case class Block(transactionHash: String, prevHash: String) extends TxType

class Router extends Controller {

  prefix("block"){
    post(""){tx: Transaction =>
      createBlockChainLink(tx)
    }
    get("/:hash"){request: Request =>
      DbOp.block(request.getParam("hash"))
    }
    get("/length"){request: Request =>
      DbOp.chainLength()
    }
  }

  private def createBlockChainLink(tx: Transaction): Unit = this.synchronized {
    DbOp.lastBlockHash().onComplete {
      case Success(s) =>
        val txHash = md5Hash(tx)
        val block = Block(txHash, s)
        val currentHash = md5Hash(block)
        DbOp.addBlock(
          BlockChain(0, currentHash, Timestamp.from(Instant.now())))
      case Failure(f) => f
    }
  }
}
