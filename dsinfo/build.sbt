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

// Documentation

// Link the documentation to the source in the main repository

scalacOptions in (Compile, doc) ++=
    Seq (
        "-doc-source-url",
            "https://bitbucket.org/inkytonik/dsinfo/src/default€{FILE_PATH}.scala"
    )

// Publishing

publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (version.value.trim.endsWith ("SNAPSHOT"))
        Some ("snapshots" at nexus + "content/repositories/snapshots")
    else
        Some ("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := true

pomIncludeRepository := { x => false }

pomExtra := (
    <url>https://bitbucket.org/inkytonik/dsinfo</url>
    <licenses>
        <license>
            <name>LGPL 3.0 license</name>
            <url>http://www.opensource.org/licenses/lgpl-3.0.html</url>
            <distribution>repo</distribution>
        </license>
    </licenses>
    <scm>
        <url>https://bitbucket.org/inkytonik/dsinfo</url>
        <connection>scm:hg:https://bitbucket.org/inkytonik/dsinfo</connection>
    </scm>
    <developers>
        <developer>
           <id>inkytonik</id>
           <name>Tony Sloane</name>
           <url>https://bitbucket.org/inkytonik</url>
        </developer>
    </developers>
)
