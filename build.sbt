val ReactImageCropVersion = "^2.0.3"
val ScalaJSReactJSVersion = "[0.13.1,0.14.0["

resolvers += Resolver.mavenLocal

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  isSnapshot.value match {
    case true => Some("snapshots" at nexus + "content/repositories/snapshots")
    case false => Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }
}
publishArtifact := false

lazy val commonSettings = Seq(
  scalaVersion := "2.12.2",
  organization := "io.github.shogowada",
  name := "scalajs-reactjs-image-crop",
  version := "1.0.0",
  ivyScala := ivyScala.value.map {
    _.copy(overrideScalaVersion = true)
  },
  licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT")),
  homepage := Some(url("https://github.com/shogowada/scalajs-reactjs-image-crop")),
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    isSnapshot.value match {
      case true => Some("snapshots" at nexus + "content/repositories/snapshots")
      case false => Some("releases" at nexus + "service/local/staging/deploy/maven2")
    }
  },
  publishArtifact := false,
  pomExtra :=
      <scm>
        <url>git@github.com:shogowada/scalajs-reactjs-image-crop.git</url>
        <connection>scm:git:git@github.com:shogowada/scalajs-reactjs-image-crop.git</connection>
      </scm>
          <developers>
            <developer>
              <id>shogowada</id>
              <name>Shogo Wada</name>
              <url>https://github.com/shogowada</url>
            </developer>
          </developers>
)

lazy val core = (project in file("core"))
    .settings(commonSettings: _*)
    .settings(
      publishArtifact := true,
      libraryDependencies ++= Seq(
        "io.github.shogowada" %%% "scalajs-reactjs" % ScalaJSReactJSVersion
      ),
      npmDependencies in Compile ++= Seq(
        "react-image-crop" -> ReactImageCropVersion
      ),
      (webpack in(Compile, fastOptJS)) := Seq(),
      (webpack in(Compile, fullOptJS)) := Seq()
    )
    .enablePlugins(ScalaJSBundlerPlugin)

lazy val example = (project in file("example"))
    .settings(commonSettings: _*)
    .settings(
      name += "-example",
      (unmanagedResourceDirectories in Compile) += baseDirectory.value / "src" / "main" / "webapp"
    )
    .enablePlugins(ScalaJSBundlerPlugin)
    .dependsOn(core)
