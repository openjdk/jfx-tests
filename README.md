# JFX-Tests

This repository contains the tests and tools for Java FX.


## Layout
- tools - test tools
- tools/Jemmy - JemmyFX tool and accompanying tools.
- bigapps - test suites based on using real world applications.[Not working yet]
- bigapps/EnsembleTests - tests using the Ensemble app [Not working yet]
- functional - This directory contains functional test suites for JavaFX.

## Dependencies

1) Bash Shell
2) JDK (version 19)
3) JavaFX sdk (SDK built out of the latest javafx-mainline or downloaded)
4) ant (version 1.10 and above)
5) Jtreg - We need a jtreg that contains lib/junit.jar file. e.g. version jtreg-6.2.1. See [Jtreg](https://openjdk.org/jtreg)
6) Jemmy-v3 library
   - git clone https://github.com/openjdk/jemmy-v3
   - cd jemmy-v3/core
   - ant build

   This builds 4 Jemmy jar files in jemmy-v3/core/build 


## Building and Running tests

### Runing tests present under `functional` directory
As of now, 3DTests, ControlsTests, FxmlTests and SceneGraphTests compile and can be run from these respective directories.
NOTE : They do not run from the top level `jfx-tests` directory.

1) Change to bash shell
2) export SHELL=/bin/bash
3) Set JAVA_HOME
4) Set PATH to java and ant executables
5) Clone the test repo
git clone https://github.com/openjdk/jfx-tests.git
(OR - clone your own personal fork of the - https://github.com/openjdk/jfx-tests repository)

6) **To execute 'SceneGraphTests' tests**
- cd jfx-tests/functional/SceneGraphTests
- Issue following command (replace the local paths appropriately) to run a set of SceneGraphTests tests

`ant -v -Djemmy-v3.jars=<Path to Jemmy repository>/jemmy-v3/core/build -Djavafx.home=<Path to a locally built OR downloaded JavaFX sdk> -Djtreg.home=<Path to Jtreg tool> test`

- To run a single test provide - `-Dtests=<Path and test file name from SceneGraphTests/tests>` before `test` in above command

7) **Generating golden images**

A golden image is a manually verified image of the expected graphical output of a test. Many of the javafx functional tests depend upon golden images for image comparison and assert.
In the absence of a centrally hosted directory of golden images, it is imperative that one needs to generate these golden images once and then subsequently run the tests. Here are the steps to generate golden images

a) Run the required tests (e.g. functional/SceneGraphTests) as described in step (6) above. This test run results in multiple test failures, but generates screenshots of test window in `build/images` directory. If satisfied with the expected graphical output, these images can be used as golden images by copying them to `build/golden/SceneGraphTests/prism/mac` directory (for a test run on MacOS). Note - this directory structure needs to be created if not present.

b) After placing the golden images in the directory mentioned above, re-run the tests using step (6).

### Runing tests present under `bigapps` directory

These tests do not run

TODO: Make these tests runnable

### Runing tests present under `bigapps/EnsembleTests` directory

These tests do not run

TODO: Make these tests runnable
