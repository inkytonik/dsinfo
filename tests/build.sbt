// Main settings

name := "tests"

version := "0.1.0-SNAPSHOT"

organization := "org.bitbucket.inkytonik.dsname"

// Scala compiler settings

scalaVersion := "2.10.0"

scalacOptions := Seq ("-deprecation", "-unchecked")

scalacOptions in Compile <<= (scalaVersion, scalacOptions) map {
    (version, options) =>
        val versionOptions =
            if (version.startsWith ("2.10"))
                Seq ("-feature")
            else
                Seq ()
        options ++ versionOptions
}

scalacOptions in Test <<= (scalaVersion, scalacOptions) map {
    (version, options) =>
        val versionOptions =
            if (version.startsWith ("2.10"))
                Seq ("-feature")
            else
                Seq ()
        options ++ versionOptions
}

// Interactive settings

logLevel := Level.Info

shellPrompt <<= (name, version) { (n, v) =>
     _ => n + " " + v + "> "
}

// Dependencies

libraryDependencies <++= scalaVersion {
    version =>
        Seq (
            "org.scala-lang" % "scala-reflect" % "2.10.0",
            "org.scalatest" %% "scalatest" % "1.9.1" % "test"
        )
}

// No main class since dsprofile is a library

mainClass := None

// Don't run tests in parallel because some bits are not thread safe yet

parallelExecution in Test := false

// Specify how to find source and test files.  Main sources are
//    - in src directory
//    - all .scala files, except
// Test sources, which are
//    - files whose names end in Tests.scala, which are actual test sources
//    - Scala files within the examples src

scalaSource in Compile <<= baseDirectory { _ / "src" }

scalaSource in Test <<= scalaSource in Compile

unmanagedSources in Test <<= (scalaSource in Test) map { s => {
    (s ** "*Tests.scala").get
}}

unmanagedSources in Compile <<=
    (scalaSource in Compile, unmanagedSources in Test) map { (s, tests) =>
        ((s ** "*.scala") --- tests).get
    }

// Resources

unmanagedResourceDirectories in Compile <<= (scalaSource in Compile) { Seq (_) }

unmanagedResourceDirectories in Test <<= unmanagedResourceDirectories in Compile

// There are no compile resources
unmanagedResources in Compile := Seq ()

// Test resources are the non-Scala files in the source that are not hidden
unmanagedResources in Test <<= (scalaSource in Test) map { s => {
    (s ** (-"*.scala" && -HiddenFileFilter)).get
}}
