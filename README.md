# Process Watch

## Story

Task managers are nice to use but usually they are limited.
On the other hand you can find and manipulate processes from the command line any way,
but it is really not a user-friendly experience.

So your team decided to implement your own graphical but versatile task manager.

## What are you going to learn?

- How to build a desktop application
- What are the properties of processes in an operating system.

## Tasks

1. Gather the current processes from the computer and display in the application.
    - Display as much information about the running processes as possible
    - If one property of a process can't be retrieved then still display the process and note that the information is not available for the given attribute

2. Have a button on the application window that refreshes the process list.
    - When the button is pressed the list of active processes shall be re-requested and displayed

3. Provide some way for the user to filter the displayed processes.
    - Filter the processes of a given user
    - Display all the processes that have the same parent process

4. Provide some way for the user to kill processes.
    - Provide a way to select some of the processes
    - Provide a way to select all of the **displayed** processes
    - Provide a way to terminate selected processes

5. Provide contextual help / usage information for the user.
    - Have a little question mark `?` button next to all interactive parts that describes how that control works

6. Add an "About" window displaying general description about the application.
    - Add a button which when clicked, displays a pop-up window that gives a brief description of the application

## General requirements

None

## Hints

- The GUI used here is written in JavaFX, the standard GUI library for Java SE.
  The JavaFX framework is set as a dependency in `pom.xml` (Maven will need
  some time to download it upon opening the project for the first time).
  The application starts by typing `mvn compile javafx:run` from the command line
  or by selecting the `javafx:run` option from the Maven menu in IntelliJ
  (`View / Tool windows / Maven`).
- The starter code utilizes streams, a powerful functional programming concept
  introduced in Java 8. You shouldn't spend _too much_ time on it now,
  just try to use and extend existing code. What you'll need from the
  Streams API is mostly applying _filters_ to streams. See the link
  in the background materials for some examples.
- The `ProcessHandle` API in Java is easy to use but it doesn't retrieve as much
  info as possible. If you have time left, look into the alternatives:
    - Using the virtual file system under `/proc`
    - parsing the output of [ps(1)](https://linux.die.net/man/1/ps).

## Starting your project

To start your project click [this link](https://journey.code.cool/v2/project/team/blueprint/process-watch/java), and make sure to **select or create your team**.

## Background materials

- :exclamation: [Using JavaFX UI Controls](https://docs.oracle.com/javafx/2/ui_controls/jfxpub-ui_controls.htm)
- :movie_camera: [JavaFX GUI Tutorial](https://www.youtube.com/watch?v=FLkOX4Eez6o&list=PL6gx4Cwl9DGBzfXLWLSYVy8EbTdpGbUIG)
- :exclamation: [ProcessHandle Interface](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/ProcessHandle.html)
- :exclamation: :movie_camera: [Task manager in Linux](https://www.youtube.com/watch?v=qScLRKeLeaQ)
- :exclamation: [Java stream examples](https://www.baeldung.com/java-stream-filter-lambda)
