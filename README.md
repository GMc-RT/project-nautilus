
# Project Nautilus
![20kLeaguesOrSo](https://upload.wikimedia.org/wikipedia/commons/b/be/Nautilus_macromphalus_-_edited_image.jpg)

## Objectives
Create a project on Github that has the following elements: 
- Dockerized for ease of distribution and updating of tests 
<details><summary>Secret extra Mission</summary>

![Classifed 4th Directive](https://i.stack.imgur.com/vbB3e.jpg)
</details>

- Written in Java / Cucumber 
- Use Selenium and any additionally needed frameworks 
- Runs both Firefox and Chrome 
- Browses a selection of pages on the w3 site and validates the following: - There are no console errors on page loads (chrome minimum) 
- The response code from the page (200, 302, 404, etc.) 
- All links on the page go to another live (non 4xx) page (no need to actually parse the linked page/image). 
- Pages to browse: 
- https://www.w3.org/standards/badpage 
- https://www.w3.org/standards/webofdevices/multimodal 
- https://www.w3.org/standards/webdesign/htmlcss 
- Report the results of the scan.

## Table of Contents

- [Authors](#authors)
- [Pre-requisites](#pre-requisites)
- [Libraries](#libraries)
- [Running Tests](#running-tests)
- [Running Through IntelliJ](#running-through-intellij)

## Authors
Building from the excelent template created by [Soraia Reis](https://github.com/soraiareis), 
Nautilis is written By Gerry

## Pre-requisites

You should download and install these properly on your system. Visit the websites (linked) to find instructions on how to set them up.

* [Java](https://www.java.com/en/download/)
* [Gradle](https://gradle.org/)
* [Firefox](https://www.mozilla.org/)
* [Docker](https://www.docker.com/)

## Libraries

* [Cucumber](https://cucumber.io/) - library used to support Behavior-Driven Development (BDD).
* [Selenium WebDriver](https://www.selenium.dev/documentation/en/webdriver/) - drives a browser natively, as a real user would, either locally or on remote machines.
* [Hamcrest](http://hamcrest.org/JavaHamcrest/tutorial) - a framework for writing matcher objects allowing `match` rules to be defined declaratively.

## Running Tests

### Spring Profile
You can run either `default` or `test` profiles. The `default` runs on the URL `https://soraia.herokuapp.com`, and the `test` runs on `http://the-internet.herokuapp.com`.

For the `default` profile you just need to execute Gradle `test` task.
```
./gradlew test
```

To execute the `test` profile the environment variable `SPRING_PROFILES_ACTIVE` should be set. The following command line should be used:
```
SPRING_PROFILES_ACTIVE=test ./gradlew test
```

### Context
You can either run `local` (Firefox is used) or `remote` (in Docker containers). The default value is `remote`, but for `local` execution the `context` property should be set as `local`. However, the `docker-compose` is set as required every time the test task is executed, so passing `context` as `local` will bring up the Docker containers anyway, even though the execution happens locally.
In the command line we pass the following argument:
```
./gradlew test -Dcontext=local
```

### Browser
If we run remotely, we have the option of running in a Firefox or a Chrome browser. The default value is `firefox`, but for `chrome` the `browser` property should be set as `chrome`.
In the command line we pass the following argument:
```
./gradlew test -Dbrowser=chrome
```

### Parallel
The project is configured to run in parallel by default. The default `threads count` for parallel executions are `10`. We can either change the thread count to `1` and execute them sequentially, or even increase the default number if necessary. In the command line we pass the following argument with the `thread count` wanted:
```
./gradlew test -Ddataproviderthreadcount="1"
./gradlew test -Ddataproviderthreadcount="20"
```

### Tags
All the scenarios are executed unless we specify the tag group that we want to execute. In the command line, we pass the following argument with the tag(s):
```
./gradlew test -Dcucumber.filter.tags="@smoke"
```

## Running Through IntelliJ
You can also execute the test scenarios through IntelliJ and there are multiple execution options.

### Locally
In the `application.yml` file change the property `context` to `local`.

* Right-click on the feature file and select `Run 'Feature: login'`; or
* Right-click on the CucumberRunner.java file and select `Run 'CucumberRunner'`.

### Remote (Docker)
In the `application.yml` file make sure the property `context` is `remote`.

Bring the docker containers `up` by executing the command:
```
docker-compose up -d --scale firefox=2 --scale chrome=2
```

* Right-click on the feature file and select `Run 'Feature: login'`; or
* Right-click on the CucumberRunner.java file and select `Run 'CucumberRunner'`.

Bring the docker containers `down` after the test is done by executing the command:
```
docker-compose down
```
