import com.typesafe.sbt.pgp.PgpKeys.{publishSigned, publishLocalSigned}

// Main settings

version in ThisBuild := "0.4.0"

organization in ThisBuild := "org.bitbucket.inkytonik.dsinfo"

// Scala compiler settings

scalaVersion in ThisBuild := "2.12.8"

crossScalaVersions in ThisBuild := Seq ("2.12.8", "2.13.0-RC3")

scalacOptions in ThisBuild := Seq ("-deprecation", "-unchecked", "-Xlint")

scalacOptions in ThisBuild in Compile ++= {
    val versionOptions =
        if (scalaVersion.value.startsWith ("2.9"))
            Seq ()
        else
            Seq ("-feature")
    versionOptions ++ Seq (
        "-sourcepath", baseDirectory.value.getAbsolutePath
    )
}

scalacOptions in ThisBuild in Test ++= (scalacOptions in ThisBuild in Compile).value

// Dependency resolution

resolvers in ThisBuild ++= Seq (
    Resolver.sonatypeRepo ("releases"),
    Resolver.sonatypeRepo ("snapshots")
)

// Dependencies

libraryDependencies in ThisBuild ++=
    Seq (
        "org.scala-lang" % "scala-reflect" % scalaVersion.value
    )

// Interactive settings

logLevel in ThisBuild := Level.Info

shellPrompt in ThisBuild := {
    state =>
        Project.extract(state).currentRef.project + " " + version.value +
            " " + scalaVersion.value + "> "
}

// Sub-projects

lazy val root =
    Project (
        id = "root",
        base = file (".")
    ) aggregate (dsinfo, egs, tests)

lazy val dsinfo =
    Project (
        id = "dsinfo",
        base = file ("dsinfo")
    )

lazy val egs =
    Project (
        id = "egs",
        base = file ("egs")
    ) dependsOn (dsinfo)

lazy val tests =
    Project (
        id = "tests",
        base = file ("tests")
    ) dependsOn (egs)

// No main class since dsinfo is a library

mainClass in ThisBuild := None

// Don't run tests in parallel because some bits are not thread safe yet

parallelExecution in ThisBuild in Test := false

// Publishing

publish := (publish in dsinfo).value

publishLocal := (publishLocal in dsinfo).value

publishSigned := (publishSigned in dsinfo).value

publishLocalSigned := (publishLocalSigned in dsinfo).value
