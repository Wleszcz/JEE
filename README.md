# Jakarta EE Tools and Applications

Project contains examples for Jakarta EE classes conducted at the Faculty of Electronics, Telecommunications and
Informatics of Gdańsk University of Technology.

[![MIT licensed][shield-mit]](LICENSE)
[![Java v17][shield-java]](https://openjdk.java.net/projects/jdk/17/)
[![Jakarta EE v10][shield-jakarta]](https://jakarta.ee/specifications/platform/10/)
[![TypeScript v5][shield-typescript]](https://jakarta.ee/specifications/platform/10/)
[![Angular v16][shield-angular]](https://jakarta.ee/specifications/platform/10/)

## Examples

Each example is provided as different branch. New features are made in incremental way, so each subsequent branch is
based on previous one. In some cases there can be parallel patch in branches incremental (marked by origin branch
column). For example from one point CDI and EJB based applications are developed independently.

| branch        | origin branch | description                                   |
|---------------|---------------|-----------------------------------------------|
| servlet       | master        | Jakarta Servlet specification                 |
| cdi           | servlet       | Jakarta Contexts and Dependency specification |
| jsf           | cdi           | Jakarta Faces specification                   |
| jax-rs        | jsf           | Jakarta RESTful Web Services specification    |
| jpa           | jax-rs        | Jakarta Persistence specification             |
| ejb           | jpa           | Jakarta Enterprise Beans specification        |
| cdi--security | jpa           | Jakarta Security specification                |
| ejb--security | ejb           | Jakarta Security specification                |

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

## Stuff worth paying attention

New stuff added in this branch worth paying attention.

In `simple-rpg`:

* `pom.xml` - project configuration with Jakarta and Open Liberty server dependencies,
* `requests.http` - file with example HTTP requests testing application HTTP endpoint.

In `simple-rpg/src/main/liberty`:

* `server.xml` - Open Libery server config with Servlet feature.

In `simple-rpg/src/main/webapp`:

* `WEB-INF/web-xml` - web application general descriptor,
* `WEB-INF/ibm-web-ext.xml` - Open Liberty server descriptor.

In `simple-rpg/src/main/java/pl/edu/pg/eti/kask/rpg`:

* `crypto/component/Pbkdf2PasswordHash.java` - komponent for hashing and verifying user passwords,
* `serialization/component/CloningUtility.java` - cloning utility used in datastore to avoid reference leaking,
* `datastore/component/DataStore.java` - dummy in-memory data store simulating database,
* `configuration/listener/*` - number of web listener initializing beans and data,
* `repository/api/Repository.java` - general repository interface,
* `controller/servlet/exception/*` - number of exceptions which can be thrown by controllers,
* `controller/filter/ExceptionFilter.java` - web filter for exception handling,
* `controller/servlet/ApiServlet.java` - main API servlet implementing HTTP endpoint,
* `compoment/DtoFunctionFactory.java` - factory for creating functions instances,
* `user/entity/*` - entity classes associated with users,
* `user/repository/api/*` - interfaces defining repositories associated with users,
* `user/repository/memory/*` - in-memory implementation of repositories associated with users,
* `user/dto/*` - DTO classes used in HTTP request and responses,
* `user/dto/function/*` - functions converting between DTO and entity classes,
* `user/services/*` - business logic implementation associated with users,
* `creature/entity/*` - entity classes associated with creatures,
* `character/entity/*` - entity classes associated with characters,
* `character/repository/api/*` - interfaces defining repositories associated with characters,
* `character/repository/memory/*` - in-memory implementation of repositories associated with characters,
* `character/dto/*` - DTO classes used in HTTP request and responses,
* `character/dto/function/*` - functions converting between DTO and entity classes,
* `character/services/*` - business logic implementation associated with characters,
* `character/controller/api/*` - interfaces defining controllers associated with characters,
* `character/controller/simple/*` - servlet based implementation of controllers associated with characters.

## License

Project is licensed under the [MIT](LICENSE) license.

## Credits

All character's portraits were created using [DMHeroes](http://dmheroes.com/) developed by
[Christian Oesch](https://twitter.com/ChristianOesch).

## Author

Copyright &copy; 2020 - 2023, Michał (psysiu) Wójcik

[![][gravatar-psysiu]]()

[shield-mit]: https://img.shields.io/badge/license-MIT-blue.svg
[shield-java]: https://img.shields.io/badge/Java-17-blue.svg
[shield-jakarta]: https://img.shields.io/badge/Jakarta_EE-10-blue.svg
[shield-typescript]: https://img.shields.io/badge/TypeScript-5-blue.svg
[shield-angular]: https://img.shields.io/badge/Angular-16-blue.svg
[gravatar-psysiu]: https://s.gravatar.com/avatar/b61b36a5b97ca33e9d11d122c143b9f0
