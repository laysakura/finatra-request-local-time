package com.github.laysakura.requestlocaltime.modules

import com.github.laysakura.requestlocaltime
import com.github.laysakura.requestlocaltime.annotations.VerboseServiceServer
import com.github.laysakura.requestlocaltime.idl.{VerboseService, VerboseService$FinagleClient}
import com.google.inject.{Provides, Singleton}
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.thrift.ClientId
import com.twitter.inject.TwitterModule
import com.twitter.util.Future

object VerboseServiceModule extends TwitterModule {
  val ServiceName = "verbose-service"

  @Provides
  @Singleton
  def provideVerboseServiceFinagleClient(client: VerboseService[Future]): VerboseService$FinagleClient =
    client.asInstanceOf[VerboseService$FinagleClient]

  @Provides
  @Singleton
  def provideVerboseService(
    @VerboseServiceServer addr: String,
    @requestlocaltime.annotations.ClientId clientId: String
  ): VerboseService[Future] = {
    val thriftClient = ThriftMux.Client().withClientId(ClientId(clientId))
    thriftClient.newIface[VerboseService[Future]](addr, ServiceName)
  }
}
