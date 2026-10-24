# VS

A small Java project. It has two simple servers that run at the same time:

- **Syslog receiver** – listens on UDP port `514` and prints every log message it gets.
  Messages longer than 2048 bytes are thrown away.
- **Discovery responder** – listens on UDP port `8888`. When a client sends a packet,
  it sends an empty answer back. So the client can find the server's IP address.

## What you need

- Java (the project is set to version 27, see `pom.xml`)
- [Maven](https://maven.apache.org/)
- [just](https://github.com/casey/just) (optional, it makes the commands shorter)

## How to run

With `just`:

```
just compile
just exec
```

Or with Maven only:

```
mvn compile
mvn exec:java -Dexec.mainClass="vs.Main"
```

Note: Port 514 is a low port. On Linux and Mac you may need `sudo` to use it.

## Test it

Send a message to the syslog receiver:

```
echo "hello" | nc -u -w1 localhost 514
```

Ask the discovery responder:

```
echo "who is there?" | nc -u -w1 localhost 8888
```

## Files

- `src/main/java/vs/Main.java` – starts both servers
- `src/main/java/vs/SyslogReceiver.java` – the syslog server
- `src/main/java/vs/DiscoveryResponder.java` – the discovery server
- `justfile` – short commands
