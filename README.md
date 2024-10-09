# Jakarta EE Tools and Applications

Project contains examples for Jakarta EE classes conducted at the Faculty of Electronics, Telecommunications and
Informatics of Gdańsk University of Technology.

[![MIT licensed][shield-mit]](LICENSE)
[![Java v17][shield-java]](https://openjdk.java.net/projects/jdk/17/)
[![Jakarta EE v10][shield-jakarta]](https://jakarta.ee/specifications/platform/10/)
[![TypeScript v5][shield-typescript]](https://jakarta.ee/specifications/platform/10/)
[![Angular v16][shield-angular]](https://jakarta.ee/specifications/platform/10/)

## Requirements

The list of tools required to build and run the project:

* Open JDK 17
* Apache Maven 3.8
* npm 9.5
* Angular CLI 16
* Node 18

Requirements for particular branch are provided in branch `README.md` file.

## Building

In order to build project use:

```bash
mvn clean package
```

If your default `java` is not from JDK 17 use (in `simple-rpg` directory):

```bash
JAVA_HOME=<path_to_jdk_home> mvn package
```

## Running

In order to run using Open Liberty Application server use (in `simple-rpg` directory):

```bash
mvn -P liberty liberty:dev
```

If your default `java` is not from JDK 17 use (in `simple-rpg` directory):

```bash
JAVA_HOME=<path_to_jdk_home> mvn -P liberty liberty:dev
```
