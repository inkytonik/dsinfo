// Main settings

version in ThisBuild := "0.3.0-SNAPSHOT"

organization in ThisBuild := "org.bitbucket.inkytonik.dsinfo"

// Scala compiler settings

scalaVersion in ThisBuild := "2.10.3"

scalacOptions in ThisBuild := Seq ("-deprecation", "-unchecked")

scalacOptions in ThisBuild in Compile <<= (scalaVersion, scalacOptions, baseDirectory) map {
    (version, options, bd) =>
        val versionOptions =
            if (version.startsWith ("2.9"))
                Seq ()
            else
                Seq ("-feature")
        options ++ versionOptions ++ Seq (
            "-sourcepath", bd.getAbsolutePath
        )
}

scalacOptions in ThisBuild in Test <<= scalacOptions in ThisBuild in Compile

// Dependencies

libraryDependencies in ThisBuild <++= scalaVersion {
    version =>
        Seq (
            "org.scala-lang" % "scala-reflect" % version
        )
}

// Interactive settings

logLevel in ThisBuild := Level.Info

shellPrompt <<= (name, version) { (n, v) =>
     _ => "dsinfo " + n + " " + v + "> "
}

// No main class since dsprofile is a library

mainClass in ThisBuild := None

// Don't run tests in parallel because some bits are not thread safe yet

parallelExecution in ThisBuild in Test := false
