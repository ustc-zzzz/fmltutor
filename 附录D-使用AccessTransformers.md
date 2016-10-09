概述

相信在mod开发中很容易遇到一个令人头疼的问题，你需要访问的字段没有提供对应的setter以及getter方法。在这种情况下forge给我们提供了一个廉价的解决方案，不需要通过操作字节码来完成需求，这便是<code>Access Transformers</code>，为方便讲解将在下文中简称为“AT”。

AT能够修改字段或方法的访问修饰符以及是否被final修饰，并且能将修改结果体现在开发环境中，所以我们使用AT将访问修饰符修改为public后可以在源代码中直接调用该字段或方法。

为什么需要AT，反射不够么？

使用反射API需要额外的代码，并且由于Java的安全机制使得反射将带来性能下滑。

具体实现可以参阅<code>net.minecraftforge.fml.common.asm.transformers.ModAccessTransformer</code>中的代码。

准备工作

首先打开工作目录下的<code>build.gradle</code>文件，添加以下代码：

<code>
jar {
	   manifest {
	   attributes 'FMLAT': 'fmltutor_at.cfg'
   }
}
</code>

如果已存在对应节点可以直接把对应内容添加进去，其中的fmltutor为modid，为确保正常工作，文件名务必以<code>_at.cfg</code>结尾。

在<code>src/main/resources</code>下新建<code>META-INF</code>文件夹，在<code>META-INF</code>文件夹下新建<code>fmltutor_at.cfg</code>文件。

转换修饰符

在AT的配置文件中使用的是srgName，所以我们需要打开<code>srg-mcp.srg</code>来完成配置，gradle缓存中的映射表通常在个人用户文件夹下<code>.gradle/caches/minecraft/de/oceanlabs/mcp</code>

以<code>net.minecraft.entity.Entity.fire</code>字段为例，在配置文件中添加以下内容：

<code>
public net.minecraft.entity.Entity field_70151_c
</code>

因为在srgName中类名跟mcpName一致，所以只需要查询字段或方法对应的srgName即可。

其中public为改变后的访问修饰符，如果还需要增减final修饰符，在public后面加上+f或-f即可，例如：

<code>
public+f net.minecraft.entity.Entity field_70151_c
</code>

在配置文件中注释以<code>#</code>字符开头，为方便阅读，可以在结尾加上对应的mcpName，例如：

<code>
public net.minecraft.entity.Entity field_70151_c # fire
</code>

以<code>net.minecraft.entity.EntityPlayer.setRenderOffsetForSleep(EnumFacing p_175139_1_)</code>方法为例，在配置文件中添加以下内容：

<code>
public net.minecraft.entity.EntityPlayer func_175139_a(Lnet/minecraft/util/EnumFacing;)V # setRenderOffsetForSleep
</code>

请注意在<code>func_175139_a</code>并没有空格，而在srg文件中有一个空格，<code>func_175139_a</code>后面的部分从srg文件中复制即可。

编写完配置文件保存一下，接下来参照教程1.1重新配置一遍环境即可，在setupDecompWorkspace之后别忘了设置对应IDE的环境。
