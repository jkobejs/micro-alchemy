# Microservices Alchemy

This repository is used for experimenting in building microservices using Scala.

## Commands

### Migrate

To run database migrations against postgres database server run:

```bash
./bin/migrate user-service
```

It will start postgres docker container, run flyway migrations for specific service, generate Slick tables file,
stop and remove container.

### Start database server

To start postgres database server run:

```bash
./bin/startdb user-service
```

It will start postgres docker container on local port **55432** with database **user-service**, username **postgres** and password **postgres**.

### Stop database server

To stop postgres database server run:

```bash
./bin/stopdb
```

It will stop and remove postgres docker container.
