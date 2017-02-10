package com.github.laysakura.requestlocaltime.controllers

import java.time.OffsetDateTime

import com.github.laysakura.requestlocaltime.filters.RequestLocalTime
import com.github.laysakura.requestlocaltime.idl
import com.github.laysakura.requestlocaltime.idl.VerboseService.Echo
import com.google.inject.{Inject, Singleton}
import com.twitter.finatra.thrift.Controller
import com.twitter.inject.Logging
import com.twitter.util.Future

@Singleton
class VerboseServiceController @Inject() ()
  extends Controller
    with idl.VerboseService.BaseServiceIface
    with Logging
{
  override val echo = handle(Echo) { args =>
    info(s"Current time: ${OffsetDateTime.now}\tRequestLocalTime=${RequestLocalTime.current.requestedAt}")

    debug("sleeping...")
    Thread.sleep(1000)

    info(s"Current time: ${OffsetDateTime.now}\tRequestLocalTime=${RequestLocalTime.current.requestedAt}")

    Future(s"You said: ${args.message}")
  }
}
