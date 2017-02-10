package com.github.laysakura.requestlocaltime.filters

import java.time.OffsetDateTime

import com.twitter.finagle.Service
import com.twitter.finagle.context.Contexts
import com.twitter.finatra.thrift.{ThriftFilter, ThriftRequest}
import com.twitter.util.Future

case class RequestLocalTime(requestedAt: OffsetDateTime) {
  /**
    * f を実行している間は、 RequestLocalTime.current.requestedAt で、常に同一の時刻（リクエスト時点のもの）が見える。
    */
  def asCurrent[T](f: => T): T = RequestLocalTime.let(this)(f)
}

object RequestLocalTime {
  private val ctx = new Contexts.local.Key[RequestLocalTime]

  def current: RequestLocalTime = Contexts.local.get(ctx).get

  private def let[R](requestedAt: RequestLocalTime)(f: => R): R =
    Contexts.local.let(ctx, requestedAt)(f)
}

class SetRequestLocalTimeFilter extends ThriftFilter {
  override def apply[T, U](request: ThriftRequest[T], service: Service[ThriftRequest[T], U]): Future[U] =
    RequestLocalTime(OffsetDateTime.now).asCurrent {
      service(request)
    }
}
