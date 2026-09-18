# StyleExample

The important idea is that these tools can overlap significantly (and will in this course),
but they are ultimately two entirely different tools.

## What each command does

| Tool or task     | Main responsibility                                                                | Changes source code? | Belongs to |
|------------------|------------------------------------------------------------------------------------|----------------------|------------|
| `spotlessCheck`  | Checks whether files already match the configured Spotless steps                   | No                   | g-j-f      |
| `spotlessApply`  | Applies the configured Spotless formatting steps                                   | Yes                  | g-j-f      |
| `checkstyleMain` | Checks production Java files against `checkstyle.xml`                              | No                   | Checkstyle |
| `checkstyleTest` | Checks test Java files against `checkstyle.xml`                                    | No                   | Checkstyle |
| `check`          | Runs the project's verification tasks, including formatting, Checkstyle, and tests | No                   | Gradle     |

(g-j-f == "google-java-format", the IntelliJ plugin). Checkstyle is also a plugin if that wasn't clear from lecture.

They all have different jobs, and, when run in order + some light manual fixing, it's very quick to
get the repo in a state where at least style won't cause the build to fail.

## General flow

1. Attempt to build the code as it exists. The build should fail due to two things:
   1. checkstyleMain
   2. spotlessJavaCheck
2. First thing I'd do is check the checkstyle report. We currently have 77 violations.
3. Since our checkstyle guide is very similar to what google-java-format is using, we can fix most of this with spotless
4. I'll run `spotlessApply` first. We can run spotlessCheck (or spotlessJavaCheck, since we're only working with non-test files right now) to confirm that g-j-f did its job
5. I'll then run `checkstyleMain` (in the `other` group in the gradle tool window) to confirm what we're working with now.
6. g-j-f only left 1 checkstyle problem behind, and it's parameter names in this instance, since that's not something the formatter is willing to touch because who's to say what is the "right" parameter name
   1. this means it gets left for humans to go in and fix based on linter feedback
7. I'll manually fix whatever's left and run `checkstyleMain` again. In this case, if you've never seen regular expressions, the issue is that parameter names should also be camelCased, to which 'X' is not
   1. requiring it to be longer than 1 char is something that can be adjusted too with this expression added to checkstyle.xml "^[a-z][a-zA-Z0-9]+$"
8. I typically will run the spotless commands again just in case, and then try to build again. Even without running g-j-f again, the build passes now that style is maintained.


## Full breakdown of relevant gradle commands

### Check formatting only

```bash
./gradlew spotlessCheck
```

This checks the Spotless configuration. It does not run Checkstyle.

### Apply formatting fixes

```bash
./gradlew spotlessApply
```

This modifies the Java files to match the Spotless steps, including
`google-java-format` and unused-import cleanup.

### Run Checkstyle on production code

```bash
./gradlew checkstyleMain
```

This analyzes Java files under `src/main/java` using
`config/checkstyle/checkstyle.xml`. It reports violations but does not change
the files.

### Run Checkstyle on test code

```bash
./gradlew checkstyleTest
```

This analyzes Java files under `src/test/java` using the same configuration.

### Run the combined verification task

```bash
./gradlew check
```

`check` normally runs the Spotless check, Checkstyle tasks, and tests. A
failure in one task can prevent later tasks from running, so run the individual
tasks when demonstrating a particular tool.

To let Gradle continue to other independent verification tasks after a
failure, use:

```bash
./gradlew check --continue
```

### Run the full build

```bash
./gradlew build
```

`build` includes `check`, along with the assembly tasks.

## Viewing the Checkstyle report

After running:

```bash
./gradlew checkstyleMain
```

open the generated HTML report:

[Open the `checkstyleMain` HTML report](build/reports/checkstyle/main.html)

The report is generated locally under `build/`, so the link works after the
task has run in the repository. The `build/` directory is normally ignored by
Git and the report is not intended to be committed.

If the report is not present, run the task directly with:

```bash
./gradlew clean checkstyleMain --rerun-tasks
```


## Source documentation

- [Gradle Checkstyle plugin](https://docs.gradle.org/current/userguide/checkstyle_plugin.html)
- [Spotless Gradle plugin](https://github.com/diffplug/spotless/blob/main/plugin-gradle/README.md)
- [google-java-format](https://github.com/google/google-java-format)
- [Checkstyle documentation](https://checkstyle.org/)
