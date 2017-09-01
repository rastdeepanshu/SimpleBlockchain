package com.simpleblockchain.service

import com.simpleblockchain.db.DbOp
import com.simpleblockchain.port
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter

object Main{
  def main(args: Array[String]): Unit = {
    DbOp.insertGenesis()
    val server = new FinatraServer
    server.main(args)
  }
}

class FinatraServer extends HttpServer{

  override protected def defaultFinatraHttpPort: String = s":$port"

  override protected def configureHttp(router: HttpRouter): Unit = {
    router.add[Router]
  }
}