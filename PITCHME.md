# Bank Linking Overview
---

# Purpose
Improve bank linking at Stash
---

# Current Projects and Services

- External Accounts
- Institution Search Service
- Stash Platform
- Stash Quovo v2 Client
- Stash Quovo v3 Client
---

# System Architecture

---?image=assets/eat_system_architecture.png&size=auto 90%
---

# External Accounts

### Purpose

Provides a vendor agnostic view of externally linked accounts. Any account (checking, savings, credit card etc) linked to a Stash account. Two sources so far, Plaid + Quovo
---

### Some External Accounts Features

- account linking
- account access health
- account stats
- balance check
- user information
---

### Linking an account with no MFA

---?image=assets/good_sync_flow.png&size=auto 90%
---

# Institution Search

### Purpose

Provide a cleaner search mechanism for customers when they link a bank account. Institutions are agnostic of provider and have a Stash unique identifier. 
---

### Features

- Elastic search backed type and search
- Provider to Stash unique id mapping (used by External Accounts)
---

# Stash Platform

### Purpose

- Common Stash library for shared service code and domain models.
- Publishes to S3
- Easy to share client code, like quovo
- S3 == Artifactory, mimics Maven/Ivy style repo
- S3 is cheap and we already use it
---

### Shared Service Code

- Thin base server and controller wrapper for Akka Http
- Thin environment trait for cake pattern use
- Health endpoint and response
---

### Other Shared Code

- Repository layer trait
- Account, Portfolio, Transaction and User repositories
- Mongo and Plaid Mongo repository concrete implementations
- Common Json serializers and deserializers (DateTime, Enums)
---

### Domain Models

- Account, Portfolio, Transaction and User
---

# Stash Quovo v2 Client

- Client for Quovo v2
- Uses play libraries
- Part of Stash Commons repo
- Will be deprecated soon (internally only?)
---

# Stash Quovo v3 Client

- Client for Quovo v3
- Uses new nomenclature
- Uses [sttp](https://github.com/softwaremill/sttp)
- Separate repo in Github
---

# Libraries

- [Akka Http](https://doc.akka.io/docs/akka-http/current/) + [Spray Json](https://doc.akka.io/docs/akka-http/current/common/json-support.html#json-support)
- [sttp](https://github.com/softwaremill/sttp)
- [cats](https://typelevel.org/cats/)
- [circe](https://circe.github.io/circe/)
- [doobie](https://tpolecat.github.io/doobie/)

---

# Patterns
---

### Cake Pattern

```scala
class UsersController(dependencies: Dependencies) extends Controller {
  
  private implicit val ec = dependencies.executionContext

  def routes: Route = pathPrefix("users" / stashUserId) { userId =>
    val stashUserId = UUID.fromString(userId)

    path("transactions") {
      get {
        parameters(queryParams)(parseOptions(_,_,_,_,_) { queryParams =>

          complete(getTransactions(stashUserId, queryParams.convert))
        })
      }
    }~
    path("stats") {
      get {
        complete(getUserPortfolioStats(stashUserId))
      }
    }
  }

  private def getTransactions(stashUserId: UUID, queryOptions: TransactionQueryOptions): Future[ToResponseMarshallable] = {
    import stash.transactions.api.json.TransactionProtocol._

    dependencies.transactionService.getForUser(
      user = stashUserId,
      queryOptions
    ).map { transactions =>
      ToResponseMarshallable(StatusCodes.OK -> transactions.toResponse)
    }.recover {
      case NonFatal(e) => ToResponseMarshallable(StatusCodes.BadRequest -> e.getMessage)
    }
  }


  private def getUserPortfolioStats(stashUserId: UUID): Future[ToResponseMarshallable] = {
    import stash.transactions.api.json.StatsProtocols._

    dependencies.portfoliosService.stats(stashUserId).map { portfolioStats =>
      val statsResponse = StatsResponse(Stats(portfolio = portfolioStats))

      ToResponseMarshallable(StatusCodes.OK -> statsResponse)
    }.recover {
      case NonFatal(e) => ToResponseMarshallable(StatusCodes.BadRequest -> e.getMessage)
    }
  }
}
```
---

### Companion

```scala
object UsersController {
  trait Dependencies {
    def executionContext: ExecutionContext
    def transactionService: TransactionsService
    def portfoliosService: PortfoliosService
  }
}
```
---

### Injecting

```scala
class Environment(configuration: Config, implicit val actorSystem: ActorSystem)
  extends BaseEnvironment
    with UsersController.Dependencies
    with PortfoliosController.Dependencies
    with QuovoPlaidTransactionService.Dependencies
    with QuovoPlaidPortfolioService.Dependencies
    with MongoRepository.Dependencies { env =>

    val currentVersion = Environment.versionedController("api")("v1") _

    lazy implicit val executionContext: ExecutionContext = actorSystem.dispatcher
    lazy val mongoExecutionContext = executionContext

    // mongo dependencies
    val mongoUri = configuration.getString("mongodb.uri")

    lazy val driver = MongoDriver()
    lazy val parsedUri = MongoConnection.parseURI(mongoUri)
    lazy val connectionTry = parsedUri.map(driver.connection)
    lazy val connection = Environment.getMongoDatabase(connectionTry, parsedUri)

    lazy val instrumentationFactory = new CensorinusFactory(new CensorinusFactory.Dependencies {
      val statsdConf = configuration.getConfig("statsd")
      val hostname = statsdConf.getString("hostname")
      val namespace = statsdConf.getString("namespace")
      val port = statsdConf.getInt("port")

      val statsdClient: StatsDClient = new StatsDClient(hostname, port, namespace)
    })

    // services
    lazy val transactionService = new QuovoPlaidTransactionService(env)
    lazy val portfoliosService = new QuovoPlaidPortfolioService(env)

    lazy val usersController = new UsersController(env)
    lazy val portfoliosController = new PortfoliosController(env)

    val controllers = Seq(usersController, portfoliosController).map(currentVersion)

    // repositories
    lazy val plaidAccountsRepository = new AccountRepoImpl(env)
    lazy val plaidUserRepository = new UserRepoImpl(env)
    lazy val plaidTransactionRepository = new MongoPlaidTransactionsRepository(env)

    lazy val quovoUsersRepository = new MongoQuovoUserRepository(env)
    lazy val quovoTransactionsRepository = new MongoQuovoTransactionRepository(env)

    lazy val quovoPortfoliosRepository = new MongoQuovoPortfolioRepository(env)
}
```
