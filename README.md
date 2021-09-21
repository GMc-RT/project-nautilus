
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

We have the option of running in a Firefox or a Chrome browser. The default value is `firefox`, but for `chrome` the `browser` property should be set as `chrome`.
In the command line we pass the following argument:
```
./gradlew test -Dbrowser=chrome
```

```
./gradlew test -Dbrowser=firefox
```



