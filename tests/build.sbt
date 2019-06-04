import com.typesafe.sbt.pgp.PgpKeys.{publishSigned, publishLocalSigned}

// Dependencies

libraryDependencies in ThisBuild ++= Seq (
    "org.scalatest" %% "scalatest" % "3.0.8-RC5" % "test"
)

// Specify how to find source and test files.  Main sources are
//    - in src directory
//    - all .scala files, except
// Test sources, which are
//    - files whose names end in Tests.scala, which are actual test sources
//    - Scala files within the examples src

scalaSource in Compile := baseDirectory.value / "src"

scalaSource in Test := (scalaSource in Compile).value

unmanagedSources in Test :=
    (((scalaSource in Test).value) ** "*Tests.scala").get

unmanagedSources in Compile :=
    (((scalaSource in Compile).value ** "*.scala") --- (unmanagedSources in Test).value).get

// Resources

unmanagedResourceDirectories in Compile := Seq((scalaSource in Compile).value)

unmanagedResourceDirectories in Test := (unmanagedResourceDirectories in Compile).value

// There are no compile resources
unmanagedResources in Compile := Seq ()

// Test resources are the non-Scala files in the source that are not hidden
unmanagedResources in Test :=
    ((scalaSource in Test).value ** (-"*.scala" && -HiddenFileFilter)).get

// Publishing

publish := {}

publishLocal := {}

publishSigned := {}

publishLocalSigned := {}
