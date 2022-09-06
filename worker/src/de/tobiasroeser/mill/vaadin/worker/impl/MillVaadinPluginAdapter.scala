package de.tobiasroeser.mill.vaadin.worker.impl

import com.vaadin.flow.plugin.base.{BuildFrontendUtil, PluginAdapterBase, PluginAdapterBuild}
import com.vaadin.flow.server.frontend.scanner.ClassFinder
import de.tobiasroeser.mill.vaadin.worker.MillVaadinConfig

import java.io.File
import java.net.URI
import java.nio.file.Path
import java.util
import scala.jdk.CollectionConverters.{SeqHasAsJava, SetHasAsJava}

class MillVaadinPluginAdapter(config: MillVaadinConfig) extends PluginAdapterBase {

  // PluginAdapterBase

  override def applicationProperties(): File = config.applicationPropertiesPath.toIO
  override def eagerServerLoad(): Boolean = config.eagerServerLoad
  override def frontendDirectory(): File = config.frontendPath.toIO
  override def generatedFolder(): File = config.generatedPath.toIO
  override def generatedTsFolder(): File = config.generatedTsPath.toIO
  override def getClassFinder: ClassFinder = {
    val classpathElements = config.classpath
    BuildFrontendUtil.getClassFinder(classpathElements.map(_.toString()).asJava)
  }
  override def getJarFiles: util.Set[File] = config.classpath.toSet.filter(_.ext == "jar").map(_.toIO).asJava
  override def isJarProject: Boolean = true
  override def getUseDeprecatedV14Bootstrapping: String = ""
  override def isDebugEnabled: Boolean = config.debugEnabled
  override def javaSourceFolder(): File = (config.sourcePath.toIO)
  override def javaResourceFolder(): File = config.resourcePath.toIO
  override def logDebug(debugMessage: CharSequence): Unit = config.log.debug(debugMessage.toString)
  override def logInfo(infoMessage: CharSequence): Unit = config.log.info(infoMessage.toString)
  override def logWarn(warningMessage: CharSequence): Unit = config.log.error(warningMessage.toString)
  override def logWarn(warningMessage: CharSequence, throwable: Throwable): Unit =
    config.log.error(warningMessage.toString + "\n" + throwable.toString)
  override def logError(warning: CharSequence, e: Throwable): Unit =
    config.log.error(warning.toString + "\n" + e.toString)
  override def nodeDownloadRoot(): URI = new URI("https://nodejs.org/dist/")
  override def nodeAutoUpdate(): Boolean = false
  override def nodeVersion(): String = "v16.16.0"
  override def npmFolder(): File = config.npmWorkPath.toIO
  override def openApiJsonFile(): File = (config.vaadinBuildOutputPath / "generated-resources" / "openapi.json").toIO
  override def pnpmEnable(): Boolean = false
  override def useGlobalPnpm(): Boolean = false
  override def productionMode(): Boolean = config.productionMode
  override def projectBaseDirectory(): Path = config.projectBasePath.toNIO
  override def requireHomeNodeExec(): Boolean = false
  override def servletResourceOutputDirectory(): File = ???
  override def webpackOutputDirectory(): File = config.webpackOutPath.toIO
  override def buildFolder(): String = config.buildPath.toString
  override def postinstallPackages(): util.List[String] = List().asJava

}

//  // PluginAdapterBuild
//
//  override def frontendResourcesDirectory(): File = ???
//  override def generateBundle(): Boolean = ???
//  override def generateEmbeddableWebComponents(): Boolean = ???
//  override def optimizeBundle(): Boolean = ???
//  override def runNpmInstall(): Boolean = ???
//}