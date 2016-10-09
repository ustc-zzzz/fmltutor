## 概述

相信在mod开发中很容易遇到一个令人头疼的问题，你需要访问的字段没有提供对应的setter以及getter方法。在这种情况下forge给我们提供了一个廉价的解决方案，不需要通过操作字节码来完成需求，这便是`Access Transformers`，为方便讲解将在下文中简称为“AT”。

AT能够修改字段或方法的访问修饰符以及是否被final修饰，并且能将修改结果体现在开发环境中，所以我们使用AT将访问修饰符修改为public后可以在源代码中直接调用该字段或方法。

为什么需要AT，反射不够么？

使用反射API需要额外的代码，并且由于Java的安全机制使得反射将带来性能下滑。

具体实现可以参阅`net.minecraftforge.fml.common.asm.transformers.ModAccessTransformer`中的代码。

## 准备工作

首先打开工作目录下的`build.gradle`文件，添加以下代码：

**`build.gradle（部分）:`**

```
jar {
    manifest {
	   attributes 'FMLAT': 'fmltutor_at.cfg'
    }
}
```

如果已存在对应节点可以直接把对应内容添加进去，其中的fmltutor为modid，为确保正常工作，文件名务必以`_at.cfg`结尾。

在`src/main/resources`下新建`META-INF`文件夹，在`META-INF`文件夹下新建`fmltutor_at.cfg`文件，这就是接下来我们需要处理的，AT的配置文件。

## 转换修饰符

在AT的配置文件中使用的是srgName，所以我们需要打开`mcp-srg.srg`来完成配置，这一映射表通常在个人用户文件夹下，下面是本教程中的对应位置：`.gradle/caches/minecraft/de/oceanlabs/mcp/mcp_stable/20/srgs/mcp-srg.srg`，关于srgName，以及后面提到的mcpName的更多信息，可以参见[这里](附录C-混淆与反射.html)。

以`net.minecraft.entity.Entity.fire`字段为例，在配置文件中添加以下内容：

**`src/main/resources/META-INF/fmltutor_at.cfg:`**

```
public net.minecraft.entity.Entity field_70151_c
```

因为在srgName中类名跟mcpName一致，所以只需要查询字段或方法对应的srgName即可。

其中public为改变后的访问修饰符，如果还需要增减final修饰符，在public后面加上+f或-f即可，例如：

**`src/main/resources/META-INF/fmltutor_at.cfg（部分）:`**

```
public+f net.minecraft.entity.Entity field_70151_c
```

在配置文件中注释以`#`字符开头，为方便阅读，可以在结尾加上对应的mcpName，例如：

**`src/main/resources/META-INF/fmltutor_at.cfg（部分）:`**

```
public net.minecraft.entity.Entity field_70151_c # fire
```

以`net.minecraft.entity.EntityPlayer.setRenderOffsetForSleep(EnumFacing p_175139_1_)`方法为例，在配置文件中添加以下内容：

**`src/main/resources/META-INF/fmltutor_at.cfg（部分）:`**

```
public net.minecraft.entity.EntityPlayer func_175139_a(Lnet/minecraft/util/EnumFacing;)V # setRenderOffsetForSleep
```

请注意在`func_175139_a`并没有空格，而在srg文件中有一个空格，`func_175139_a`后面的部分从srg文件中复制即可。

编写完配置文件保存一下，还剩下最后一步就大功告成了：重新配置一遍开发环境，也就是[这一节](1.1-配置你的工作环境.md)的内容：

```
./gradlew setupDecompWorkspace
```

然后再根据自己的IDE配置一下就好了。
