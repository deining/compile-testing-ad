/*
 * Copyright (C) 2013 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * This package contains classes that let you compile Java source code in tests and make assertions
 * about the results. This lets you easily test {@linkplain javax.annotation.processing.Processor
 * annotation processors} without forking {@code javac} or creating separate integration test
 * projects.
 *
 * <ul>
 * <li>{@link Compiler} lets you choose command-line options, annotation processors, and source
 *     files to compile.
 * <li>{@link Compilation} represents the immutable result of compiling source files: diagnostics
 *     and generated files.
 * <li>{@link CompilationSubject} lets you make assertions about {@link Compilation} objects.
 * <li>{@link JavaFileObjectSubject} lets you make assertions about {@link
 *     javax.tools.JavaFileObject} objects.
 * </ul>
 *
 * <p>A simple example that tests that compiling a source file succeeded is:
 *
 * <pre>
 * Compilation compilation =
 *     javac().compile(JavaFileObjects.forSourceString("HelloWorld", "final class HelloWorld {}");
 * assertThat(compilation).succeeded();
 * </pre>
 *
 * <p>A similar example that tests that compiling a source file with an annotation processor
 * succeeded without errors or warnings (including compiling any source files generated by the
 * annotation processor) is:
 *
 * <pre>
 * Compilation compilation =
 *     javac()
 *         .withProcessors(new MyAnnotationProcessor())
 *         .compile(JavaFileObjects.forSourceString("HelloWorld", "final class HelloWorld {}");
 * assertThat(compilation).succeededWithoutWarnings();
 * </pre>
 *
 * <p>You can make assertions about the files generated during the compilation as well. For example,
 * the following snippet tests that compiling a source file with an annotation processor generates a
 * source file equivalent to a golden file:
 *
 * <pre>
 * Compilation compilation =
 *     javac()
 *         .withProcessors(new MyAnnotationProcessor())
 *         .compile(JavaFileObjects.forResource("HelloWorld.java"));
 * assertThat(compilation).succeeded();
 * assertThat(compilation)
 *     .generatedSourceFile("GeneratedHelloWorld")
 *     .hasSourceEquivalentTo(JavaFileObjects.forResource("GeneratedHelloWorld.java"));
 * </pre>
 *
 * <p>You can also test that errors or other diagnostics were reported. The following tests that
 * compiling a source file with an annotation processor reported an error:
 *
 * <pre>
 * JavaFileObject helloWorld = JavaFileObjects.forResource("HelloWorld.java");
 * Compilation compilation =
 *     javac()
 *         .withProcessors(new NoHelloWorld())
 *         .compile(helloWorld);
 * assertThat(compilation).failed();
 * assertThat(compilation)
 *     .hadErrorContaining("No types named HelloWorld!")
 *     .inFile(helloWorld)
 *     .onLine(23)
 *     .atColumn(5);
 * </pre>
 */
@CheckReturnValue
package com.google.testing.compile;

import javax.annotation.CheckReturnValue;
