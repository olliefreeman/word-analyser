# Word Analyser Service

 [![Build Status](https://travis-ci.org/olliefreeman/word-analyser.svg?branch=master)](https://travis-ci.org/olliefreeman/word-analyser)
 
 This project is designed to be built and tested using [Gradle](https://docs.gradle.org/current/userguide/userguide.html).
 This is easily done using the gradle wrapper which is included in the repository. 
 The wrapper will download the defined version of gradle and then execute the command, this means there is no requirement to install gradle.
 
## Required 

* Java 8+ 

## Development

This project is designed to be developed inside Intellij IDEA or Eclipse. 
The easiest way to do this is in Intellij IDEA, just choose "File"->"Open" and then choose the folder of the cloned repository.
JetBrains will do the rest and import the project as a new Gradle project and load all the required dependencies. 
You may get a pop-up asking if you wish to import the Gradle project, if so pick "yes".

Alternatively you can use the Gradle wrapper to build the required project files.

```bash
# Use gradle wrapper to build the Intellij IDEA project files
> ./gradlew idea

# Use gradle wrapper to build the Eclipse project files
> ./gradlew eclipse
``` 

All development should be done using [git-flow](https://nvie.com/posts/a-successful-git-branching-model/) and then submitted as a Pull Request to this repository.

## Testing

Testing can be performed using the Gradle wrapper, this will generate a HTML file with the test results. 
The URL to the HTML file will be supplied to the terminal in the event of any test failures.

```bash

# Run tests
> ./gradlew test

# Run tests with a debug hook on port 5005
> ./gradlew test --debug-jvm

``` 

## Running

```bash
usage: word-analyser -f <FILE>
Analyse word contents of file.
Read the provided file in and analyse contents at a word level

 -f,--file <FILE>           The file to be analysed
 -g,--groovy                Shorthand option for --language=groovy
 -h,--help
 -j,--java                  Shorthand option for --language=java
 -l,--language <LANGUAGE>   Programming language to use for analysis. One of 'java' or 'groovy', default is 'java'.
 -v,--version

```

### Running as application

The gradle build is configured to allow the application to be run using the Gradle Wrapper, the usual application args need to be passed using the
additional paramter `--args=`

```bash
# Get the help/usage
> ./gradlew run --args="-h"

# Get the version
> ./gradlew run --args="-v"

# Run word analysis on a file
> ./gradlew run --args="-f <FILEPATH>"
```

### Distributing as a standalone application

The project can be built as a standalone application which can then be deployed on a Windows or Unix machine.

```bash

# Build the distribution as a tarball
> ./gradlew distTar

# Build the distribution as a zip
> ./gradlew distZip

```

Once the archive is built it can be extracted in your desired location which will result in the following directory structure:


* word-analyser
  * bin
    * word-analyser
    * word-analyser.bat
  * lib
    * JAR files
    
 Running the application can now be done using either the bash file or bat file depending on the platform.
 Below are the commands for a Unix platform.
 
 
```bash
# Get the help/usage
> ./word-analyser/bin/word-analyser -h

# Get the version
> ./word-analyser/bin/word-analyser -v

# Run word analysis on a file
> ./word-analyser/bin/word-analyser -f <FILEPATH>

# Run word analysis on a file using groovy code rather than java
> ./word-analyser/bin/word-analyser -f <FILEPATH> -g

```

## Assumptions

Words are as identified by splitting lines on whitespace and then the following pattern is used to strip any leading or trailing punctuation:
`^\p{Punct}*([^\p{Punct}].+?%?)\p{Punct}*$`.
The pattern performs the following
* Removes all punctuation up to the first non-punctation character
* Removes all trailing punctuation apart from `%`.

There is also a "catch" in place which returns any solitary `&` characters. 

## Groovy vs Java

As an added interest the code has been implemented in both Groovy and Java, 
this is to attempt to show the cleaner and simpler code which Groovy allows over the the more complex Java lambdas,
both code sets perform the same function and operate on the file in the same manner.
The default analysis will be done using Java however the command options `-g` or `-l groovy` will start a Groovy version of the analysis service.
