# Museum Management System (Java, Design Patterns)

A Java application that manages Romanian museums and the visitor groups that tour them. It loads the public museum registry, validates group membership (guides, students, professors) and notifies groups about museum events. The design is built around four classic **design patterns**.

The program's outputs match the reference outputs for all 9 scenarios of the course test suite. The scenarios cover basic loading, malformed input, guide and member validation, group search and event notification.

## Design patterns

| Pattern | Where | Why |
|---------|-------|-----|
| **Singleton** | `Database` | a single shared registry of museums and groups |
| **Builder** | `Museum.Builder`, `Location.Builder` | museums have many optional fields (phone, URL, director, coordinates, …), so a builder keeps construction readable and avoids telescoping constructors |
| **Factory Method** | `PersonFactory` | creates a `Student` or a `Professor` from the input row without exposing the creation logic; adding a new person type does not change the callers |
| **Observer** | `Museum` (subject) → `Group` (observers) | a museum broadcasts events to the groups registered for it without knowing anything about them |

## Domain rules
- A group is identified by **museum code + time slot**.
- A group's guide must be a **professor**, and a group can have only one guide.
- A group has **at most 10 members**.
- Invalid operations raise custom exceptions (`GuideExistsException`, `GuideTypeException`, `GroupNotExistsException`, …). Their messages are written to the output instead of crashing the program.

### Supported commands
- Museums: `ADD MUSEUM`.
- Groups: `ADD GUIDE`, `REMOVE GUIDE`, `FIND GUIDE`, `ADD MEMBER`, `REMOVE MEMBER`, `FIND MEMBER`.
- Events: `ADD EVENT`, which notifies every group registered at that museum.

## Example

Input (`groups_01.in`, pipe-separated):

```
ADD GUIDE|Maria|Ionescu|profesor|45|maria.ionescu@domain.ro|Colegiul National|10|ghid|7300203|10-12
ADD MEMBER|Alex|Popescu|student|20|alex.popescu@example.com|Liceul National|2|vizitator|7300203|10-12
```

Output:

```
7300203 ## 10-12 ## new guide: surname=Maria, name=Ionescu, role=ghid, age=45, ...
7300203 ## 10-12 ## new member: surname=Alex, name=Popescu, role=vizitator, age=20, ...
```

## Build & run

Requires a JDK. The Gradle wrapper is included.

```bash
./gradlew build
./gradlew test          # runs the 9 scenarios in src/main/resources and compares with *.ref
```

Running directly (paths are given without the `.in`/`.out` extension):

```bash
java -cp build/classes/java/main org.example.Main museums src/main/resources/01-basic-museums-load/museums_01
java -cp build/classes/java/main org.example.Main groups  src/main/resources/03-basic-groups/groups_01
# all three inputs at once (museums, groups, events):
java -cp build/classes/java/main org.example.Main listener <museums> <groups> <events>
```

## Repository layout

```
src/main/java/org/example/
  Database.java       Singleton registry + input parsing / command dispatch
  Museum.java         Builder + Observer subject
  Location.java       Builder
  Group.java          Observer, guide/member rules, custom exceptions
  Person.java         base class for Student and Professor
  PersonFactory.java  Factory Method
  Main.java           entry point
src/main/resources/   test scenarios (*.in inputs, *.ref expected outputs)
src/test/java/        JUnit tests
```

## Credits

Developed as a project for the *Object-Oriented Programming* course at the Faculty of Automatic Control and Computers, Politehnica University of Bucharest (2025). The course team provided the specification and the test scenarios. The implementation is my own work.
