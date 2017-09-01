package com

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

import com.simpleblockchain.service.TxType
import com.typesafe.config.{Config, ConfigFactory}

package object simpleblockchain {

  def md5Hash(txType: TxType) : String = {
    val bytes = MessageDigest.getInstance("MD5").digest(txType.toString.getBytes(StandardCharsets.UTF_8))
    new String(bytes, StandardCharsets.UTF_8)
  }

  private val cfg: Config = ConfigFactory.load("application.conf")
  val port = cfg.getString("api.port")
}
