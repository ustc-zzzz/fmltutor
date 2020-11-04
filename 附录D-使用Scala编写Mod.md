## 概述

[Scala](http://scala-lang.org/)是一门基于JVM的，多范式的编程语言，它同时集成了面向对象和函数式两个特点，因此深受许多开发者喜爱。因为Scala基于JVM，所以Minecraft Mod其实也可以使用Scala写就。Forge对Scala语言提供了支持，一些知名度比较高的Mod也是由Scala编写而成的。当然，一些其他的基于JVM的语言如[Kotlin](http://kotlinlang.org/)等其实也可以用于编写Minecraft Mod，不过Forge本身目前还没有提供相应的支持。

本部分将带领读者使用Scala语言以类似的方式把1.1节和1.2节的部分完成一遍。当然，读者在阅读本节之前，自然要对Scala语言有一定的了解。

## 配置工作环境

我们只需要在配置工作环境前在`build.gradle`文件的前半部分加入一行代码：

```
apply plugin: 'scala'
```

就可以了。目前这个`build.gradle`文件的前半部分看起来应该是这个样子：

**`build.gradle（部分）:`**

```
buildscript {
    repositories {
        jcenter()
        maven {
            name = "forge"
            url = "http://files.minecraftforge.net/maven"
        }
    }
    dependencies {
        classpath 'net.minecraftforge.gradle:ForgeGradle:2.1-SNAPSHOT'
    }
}
apply plugin: 'scala'
apply plugin: 'net.minecraftforge.gradle.forge'
```

然后照着1.1节的内容构建开发环境就可以了。

## 新建一个主类

和Java版本不同，Gradle默认的Scala代码位于`src/main/scala`目录，而不是`src/main/java`目录下。

我们先在其中新建一个包（这里是`com.github.ustc_zzzz.fmltutor`），并在其中新建一个名为`FMLTutor.scala`的文件：

**`src/main/scala/com/github/ustc_zzzz/fmltutor/FMLTutor.scala:`**

```scala
package com.github.ustc_zzzz.fmltutor

import net.minecraftforge.fml.common._
import net.minecraftforge.fml.common.event._

/**
  * @author ustc_zzzz
  */
@Mod(
  modid = FMLTutorScala.MODID,
  name = FMLTutorScala.NAME,
  version = FMLTutorScala.VERSION,
  modLanguage = "scala",
  acceptedMinecraftVersions = "1.8.9"
)
object FMLTutorScala {
  final val MODID = "fmltutor"
  final val NAME = "FML Tutor"
  final val VERSION = "1.0.0"

  @Mod.EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit = ??? // TODO

  @Mod.EventHandler
  def init(event: FMLInitializationEvent): Unit = ??? // TODO

  @Mod.EventHandler
  def postInit(event: FMLPostInitializationEvent): Unit = ??? // TODO
}
```

当然，这里我们和Java版本一样，仍然建议类名和你的Mod名称相同。这里只不过为了和Java版本相区分而使用了`FMLTutorScala`的类名。

这里读者可能不难发现和Java版本不同的两点：

* Scala版本的Mod使用Object而不是Class来定义Mod的主类
* Scala版本的Mod在添加`@Mod`注解的时候需要添加一个`modLanguage = "scala"`的属性

剩下的部分就和Java版本类似了。

## 添加代理

代理的添加方式和Java版本类似，只不过这里作者偷懒，把三个类写进了同一个文件里：

**`src/main/scala/com/github/ustc_zzzz/fmltutor/FMLTutor.scala（部分）:`**

```scala
  @SidedProxy(
    clientSide = "com.github.ustc_zzzz.fmltutor.CommonProxyScala",
    serverSide = "com.github.ustc_zzzz.fmltutor.ClientProxyScala"
  )
  var proxy: CommonProxyScala = _

  @Mod.EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit = proxy.preInit(event)

  @Mod.EventHandler
  def init(event: FMLInitializationEvent): Unit = proxy.init(event)

  @Mod.EventHandler
  def postInit(event: FMLPostInitializationEvent): Unit = proxy.postInit(event)
```

**`src/main/scala/com/github/ustc_zzzz/fmltutor/FMLTutor.scala（部分）:`**

```scala
class CommonProxyScala {
  def preInit(event: FMLPreInitializationEvent) = ()

  def init(event: FMLInitializationEvent) = ()

  def postInit(event: FMLPostInitializationEvent) = ()
}

class ClientProxyScala extends CommonProxyScala {
  override def preInit(event: FMLPreInitializationEvent) = super.preInit(event)

  override def init(event: FMLInitializationEvent) = super.init(event)

  override def postInit(event: FMLPostInitializationEvent) = super.postInit(event)
}
```

试着运行一下，如果正常，你应该就能看到你的Mod已经加载了。
