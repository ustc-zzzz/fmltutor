## 概述

在教程中我们说到，Forge使用了一个名为Gradle的工具，还对其进行了修改，也就是名为ForgeGradle的Gradle插件。

由于Gradle本身的特性，包括作者在内的大部分开发者都是对其比较生疏的，Gradle的配置文件的核心是一个名为`build.gradle`的文件，作者今天就在这里讲讲通过对这个文件的简单修改，可以完成什么方便的、个性化的设置。

## 更新你的Forge版本和MCP版本

如果你正在使用着旧版本的Forge（这里使用11.15.1.1722举例），想要升级到新版本（这里使用11.15.1.2318-1.8.9举例）的话，我们先看一下旧版本的`build.gradle`的部分配置文件：

**`build.gradle（部分）:`**

    minecraft {
        version = "1.8.9-11.15.1.1722"
        runDir = "run"
        
        // the mappings can be changed at any time, and must be in the following format.
        // snapshot_YYYYMMDD   snapshot are built nightly.
        // stable_#            stables are built at the discretion of the MCP team.
        // Use non-default mappings at your own risk. they may not allways work.
        // simply re-run your setup task after changing the mappings to update your workspace.
        mappings = "stable_20"
        // makeObfSourceJar = false // an Srg named sources jar is made by default. uncomment this to disable.
    }


更新Forge版本和MCP版本的重点就在这里，我们在这里可以肆意地换掉Forge版本，也就是变成下面这个样子（这里删去了注释）：

**`build.gradle（部分）:`**

    minecraft {
        version = "1.8.9-11.15.1.2318-1.8.9"
        runDir = "run"
        mappings = "stable_20"
    }


新版本的Forge往往有着新的特性，所以，很多时候更新Forge版本是很有必要的。不过一般情况下Forge版本也不必更到最新，因为往往使用Mod的玩家的Forge版本还没有那么新。

除此之外，有的时候，我们需要更新MCP版本，`mappings`字段表示的就是MCP版本，我们可以对其进行更新：

**`build.gradle（部分）:`**

    minecraft {
        version = "1.8.9-11.15.1.2318-1.8.9"
        runDir = "run"
        mappings = "stable_22"
    }

MCP版本有两种，一种是快照版本，以snapshot开头，后面标注着快照的日期，比如`snapshot_20141130`。MCP每天晚上都会更新快照，所以原则上配置日期过去的每一天都是可行的。还有一种，就是稳定版本，这当然是推荐的版本，以stable开头，后面标注着版本号，针对Minecraft 1.8.9的最新稳定版本是stable_22。

那么，更新MCP版本有何意义呢？我们知道，MCP除了反编译Minecraft源代码之外，还负责反混淆代码，如果一个方法、变量等，MCP想不出适合的名称怎么办？MCP会使用一个唯一的标识符（比如针对方法大概就是`func_xxxxxx_x`的结构）来标注这个方法、变量等，随着时间的发展，想不出适合的名称的会越来越少，类似`func_xxxxxx_x`的结构的方法，等等，也会越来越少，与此同时，一些在过去定下来，而在不久后MCP认为不适当的名称，也会有变化，显然，这也有更新的必要。

作为举例，我们先看一下目前默认使用的`stable_20`版本的MCP下的`S3EPacketTeams`类的所有方法名称：

* `func_149306_d()`
* `func_149307_h()`
* `func_149308_i()`
* `func_149309_f()`
* `func_149310_g()`
* `func_149311_e()`
* `func_149312_c()`
* `func_179813_h()`
* `func_179814_i()`
* `processPacket(INetHandlerPlayClient)`
* `readPacketData(PacketBuffer)`
* `writePacketData(PacketBuffer)`

下面是`stable_22`版本的MCP下的对应方法名称：

* `getDisplayName()`
* `getAction()`
* `getFriendlyFlags()`
* `getSuffix()`
* `getPlayers()`
* `getPrefix()`
* `getName()`
* `getColor()`
* `getNameTagVisibility()`
* `processPacket(INetHandlerPlayClient)`
* `readPacketData(PacketBuffer)`
* `writePacketData(PacketBuffer)`

我们注意到，和旧版本的MCP相比，新版本的MCP更加具体化了方法名，实际上，有的时候，MCP还会为部分方法指定了泛型。

为减少读者的困惑，作者不会去更新MCP版本，也就是说，作者在教程中，会仍然使用对应Forge版本默认的MCP版本，对于不同MCP版本带来的名称差异，作者也会在教程中予以指出。

在上面的操作都完成之前，请执行下面的命令（请注意，这里相对于基础的配置命令添加了`--refresh-dependencies`参数），同样，Microsoft Windows用户请将后面的所有命令中的`./gradlew`替换成`gradlew.bat`：

    ./gradlew setupDecompWorkspace --refresh-dependencies

这里强烈建议添加`--info`（简写为`-i`）参数：

    ./gradlew setupDecompWorkspace --refresh-dependencies -i

上面的命令完成后再重新进行有关IDE的配置，如果你使用Eclipse：

    ./gradlew eclipse

如果你使用IntellijIDEA：

    ./gradlew idea

## 方便地管理Mod版本号

我们注意到，在Mod中，我们需要一个@Mod注解，来注解这一个Mod，然而，这个注解需要传递一个Mod版本参数，而我们在build.gradle文件中也需要一个Mod版本参数，所以每次升版本的时候都要改两次。

很明显，这里是有改进的空间的，这里我们介绍的就是这一种方法：首先，在这个教程的示例Mod中，@Mod注解是这么写的：

**`src/main/java/com/github/ustc_zzzz/fmltutor/FMLTutor.java（部分）:`**

    @Mod(modid = FMLTutor.MODID, name = FMLTutor.NAME, version = FMLTutor.VERSION, acceptedMinecraftVersions = "1.8.9")

我们的version是传递的名为`VERSION`的静态变量，也就是下面这个：

**`src/main/java/com/github/ustc_zzzz/fmltutor/FMLTutor.java（部分）:`**

        public static final String VERSION = "1.0.0";

现在我们对上面这句话做一个修改：

**`src/main/java/com/github/ustc_zzzz/fmltutor/FMLTutor.java（部分）:`**

        public static final String VERSION = "@version@";

然后我们修改一下Gradle的配置文件：

**`build.gradle（部分）:`**

    minecraft {
        version = "1.8.9-11.15.1.2318-1.8.9"
        runDir = "run"
        mappings = "stable_20"

        replace "@version@", project.version
    }

自然，核心就在于这句话：

**`build.gradle（部分）:`**

        replace "@version@", project.version

这句话的意思就是，遍历Mod的文件，把所有名为`@version@`的字段，都简单替换成Mod的版本，在教程里就是1.0.0。

当然，在调试的时候，这个替换操作不会运行，也就是说看到的Mod版本号仍旧是`@version@`，不过在生成Jar的时候，这个替换操作就会进行了，也就是说，最终发布出去的Mod，版本已经被替换过了。

## 使用`gradle.properties`把一些重要信息独立出来

一些在Mod开发和使用中包含的重要信息包括但不限于：Mod版本号、Minecraft版本号、Forge版本号、依赖库（包括一些用于支持的Mod等）的版本号等。

Mod的版本号更新其实是十分频繁的，每做一次bug修复、添加新特性等，都会更新Mod的版本号，如果我们直接在`build.gradle`文件中修改的话，未免显得有些杂乱。

随着Minecraft版本、Forge版本等的更新，往往一些引人注目的新特性得以加入，很多时候Mod也需要更新这些新特性，所以说，很多时候，对这些版本的更新，往往也是很频繁的。

所以说，有没有办法把上面所提到的这些信息独立到一个配置文件中，在更新的时候只需要把相应的配置更新掉就可以了，这既方便，又容易防止一些误操作。

答案当然是肯定的，我们可以把一些变量的值存储到`gradle.properties`文件中，在Gradle运行的时候，会自动读取这个文件里的信息，并使得`build.gradle`文件可以读取并使用这些信息：

我们先来写一个最简单的配置文件：

**`gradle.properties:`**

    # Mod Information
    mod_version = 1.0.0
    mod_group = com.github.ustc_zzzz

    # Minecraft & Forge
    minecraft_version = 1.8.9
    forge_version = 11.15.1.2318-1.8.9
    mcp_mapping = stable_20

    # Other properties you want ... 

我们在这里的示例中加入了几个比较常用的信息，然后我们就可以对`build.gradle`文件作出修改了，修改其实很简单，只需要把该替换掉的地方替换成对应的变量就可以了：

**`build.gradle（部分）:`**

    version = mod_version
    group = mod_group
    archivesBaseName = "fmltutor"

    minecraft {
        version = minecraft_version + "-" + forge_version
        runDir = "run"
        mappings = mcp_mapping

        replace "@version@", project.version
    }

对应的地方就被`gradle.properties`文件里的配置替换掉了，以后更新版本的时候只需要更新`gradle.properties`文件就可以了。
