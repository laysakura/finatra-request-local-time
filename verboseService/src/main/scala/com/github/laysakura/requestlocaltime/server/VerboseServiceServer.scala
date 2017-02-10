package com.github.laysakura.requestlocaltime.server

import com.github.laysakura.requestlocaltime.controllers.VerboseServiceController
import com.github.laysakura.requestlocaltime.modules.ClientIdModule
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift
import com.twitter.finatra.thrift.routing.ThriftRouter

class VerboseServiceServer extends ThriftServer
{
  private val THRIFT_PORT = 4000
  private val ADMIN_PORT = 4001

  override val name = "verbose-service-server"
  override val defaultFinatraThriftPort: String = s":$THRIFT_PORT"
  override def defaultHttpPort: Int = ADMIN_PORT

  override val modules = Seq(
    new ClientIdModule("VerboseServiceServer")
  )

  override def configureThrift(router: ThriftRouter) {
    router
      .filter[thrift.filters.LoggingMDCFilter]
      .filter[thrift.filters.TraceIdMDCFilter]
      .filter[thrift.filters.ThriftMDCFilter]
      .filter[thrift.filters.AccessLoggingFilter]
      .filter[thrift.filters.StatsFilter]
      .add[VerboseServiceController]
  }
}

object VerboseServiceServerMain extends VerboseServiceServer
