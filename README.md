[![BUILDER](https://github.com/Aman7123/rarity-sys-api-ac/actions/workflows/builder.yml/badge.svg?branch=main)](https://github.com/Aman7123/rarity-sys-api-ac/actions/workflows/builder.yml)

### rarity-sys-api-ac
* Version: 1.0.2
* Most up to date implementation will be found in branch `develop`.
* Creator: Aaron Renner
* This API project was generated using MS3's [Camel OpenAPI Archetype](https://github.com/MS3Inc/camel-archetypes), version 0.2.7.

### Introduction
This RESTful API provides NFT rarity resources. This API lacks generic examples, this is a more direct implement of an API that I require for some projects. First off I needed a database to hold persistent data, the only data it really needs to keep is a simple PK of the NFT token ID (by default is unique in ERC-721) and the value which is the rarity. The process to push data to the database is a manual process which is managed with BASH script(included). Rarity information is scrapped from `https://rarity.tools` with a AutoHotKey to download the pages.

### Documentation
* See the Swagger OpenAPI Specification: `https://app.swaggerhub.com/apis-docs/ARTechnology/rarity-sys-api/1.0.0`
* A MySQL schema of Stored Procedures and image database in `src/test/resources/mysql`
* Postman collection and environment variables in `src/test/resources/postman`

### Getting Started
**Running Locally using IDE**
This project uses Spring profiles, and corresponding application properties .yaml files.
All values from the application properties can be overwritten by the environment!
* Use the following environment variables: 
   * ```spring.profiles.active=<env>```
   * ```spring.config.name=<project-name>```

Where the combination of these values determines which properties file to choose from `src/main/resources/` for example in this
folder you will find the provided example file `rarity-sys-api-ac-dev.yaml`. To access that file a combination of 
`spring.config.name=rarity-sys-api-ac` and `spring.profiles.active=dev` would be used to run this instance.

Note: IDE specific development
* Eclipse - When modifying this API in Eclipse the VM arguments added to the runtime configuration will be prefixed with `-D`.
  * Example: `-Dspring.profiles.active=dev`

**Running on the Command Line**
The command arguments in a terminal also follow the prefix `-D` rule.
```
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.config.name=rarity-sys-api-ac --spring.profiles.active=dev"
```

### Docker & Compose & Maven
This project includes a lightweight, portable maven executable that can be used in place of having maven installed.
You will still need Java installed.

When building this application for production I have included the Dockerfile that can allow for building, preparing
and executing all source code in the base directory. Using CI/CD this can all be automated and I will try to include
an example for using Github workflows.

### Actuator Endpoints

To access the list of available Actuator endpoints, go to: `http://<host>:8080/actuator` or `{{url}}/actuator`

The available endpoints are as follows:

* `/health`
* `/metrics`
* `/info`

#### Metrics

List of available metrics can be found here: http://localhost:8080/actuator/metrics/

Add the metric name in `/metrics/<metric name>` to access the metric for that particular topic.

Sample metric: http://localhost:8080/actuator/metrics/jvm.memory.used

```
{
    "name": "jvm.memory.used",
    "description": "The amount of used memory",
    ...
}
```
### Contact

* Aaron Renner (admin@aaronrenner.com)
