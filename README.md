# finatra-request-local-time
[![Build Status](https://travis-ci.org/laysakura/finatra-request-local-time.svg?branch=master)](https://travis-ci.org/laysakura/finatra-request-local-time)

Provides `RequestLocalTime.current.requestedAt` , which is a consistent timestamp among a request-response lifetime.

## Usage
See https://github.com/laysakura/finatra-request-local-time/pull/1 for an example usage.

### Setting `SetRequestLocalTimeFilter`
```scala
import com.github.laysakura.requestlocaltime.filters.SetRequestLocalTimeFilter
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.routing.ThriftRouter

class YourServer extends ThriftServer
{
  // ...

  override def configureThrift(router: ThriftRouter) {
    router
      // other filters ...
      .filter[SetRequestLocalTimeFilter]
      .add[YourController]
  }
}
```

### Accessing `RequestLocalTime.current.requestedAt`
It can be accessed from anywhere if the code path is reached by a request.
