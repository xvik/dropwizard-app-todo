# Todo: sample dropwizard 4 application

A very basic [dropwizard-guicey](https://github.com/xvik/dropwizard-guicey) demo application: a very simple todo app.
Includes the simplest delivery packaging using gradle application plugin.

Dropwizard 4 (guicey 7.x)

Could be used for bootstrapping new projects.

Based on https://todobackend.com/  
Client based on: https://todomvc.com/ (ES5 https://todomvc.com/examples/javascript-es5/dist/)

Started application would be available on url: http://localhost:8080/

## Structure

Web application is a simple ES5 application (without additional js processing).
It is served with [dropwizard-assets](https://www.dropwizard.io/en/stable/manual/core.html#serving-assets).
All web resources are in `src/main/resources/assets`

Dev configuration located at project root: config.yml

### Delivery

Delivery created by the gradle application plugin. Run scrips are generated into
`bin/` delivery directory (scripts renamed for simplicity to `startup` instead of project name)

`src/dist` directory contains additional delivery files: production configuration and run script.

## Tasks

* `./gradle run` starts application with dev config
* `./gradlew distZip` build zip delivery archive available in `build/deliveries`
* `./gradlew installDist` creates delivery in `build/install/` so delivery content could be easily examined

For a default run (`./gradlew`) delivery would be created

## Run from IDE

Create run configuration for `ru.vyarus.app.todo.TodoApp` with arguments: `server config.yml`  

## Lombok

Also shows [lombok](https://projectlombok.org/) usage for model class (it could be also used for configuration class too)

In IntelliJIdea it should work out of the box. in case of problems read [this guide](https://www.baeldung.com/lombok-ide#intellij)

## Test

There are also two tests: one showing lightweight rest testing (without starting web container) 
and the other shows complete integration test.

NOTE: as rest use PATCH method, default JerseyClient, prepared by guicey [will not work on jdk > 16](https://github.com/eclipse-ee4j/jersey/issues/4825#issuecomment-925836004)
(or see [blog post](http://blog.supol.cz/?p=320))
To workaround it, custom client factory used with apache client instead of HttpURLConnection.
Lightweight tests are not affected because they don't perform actual http calls.

## Banner

Banner (`banner.txt` file content) was created in https://manytools.org/hacker-tools/ascii-banner/
(font: DOS Rebel)