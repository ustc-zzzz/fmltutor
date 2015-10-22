## 概述

众所周知，作为一个商业软件，Minecraft本身对代码进行了混淆，这本身是一件毫不意外的事。不过不幸的是，当这件事被放在修改游戏这件本来商业软件不允许不过后来Minecraft游戏的开发公司却默许的事上的时候，问题就变得十分复杂了。

在修改游戏的过程中，常常会出现一些非公有的属性或方法不能访问的情况，或者因为扩展需要修改一个被声明为final的值。这时代价最小的方案往往是Java自身提供的反射机制。而当反射机制遇上混淆机制的时候，没有全面顾及到混淆的代码又常常会出现一些问题。

附录的这一部分主要讨论的就是不同的混淆名称之间的关系，以及Forge是怎么处理这些名称的，再就是一些使用反射机制时容易出现的问题，和一些利用Forge自身提供的机制可能带来简化的小技巧。

阅读教程中的反射部分需要一些和Java的反射机制相关的知识，对反射机制的相关知识还没有完全理解的读者可以先行阅读下面链接提供的教程，或者自行寻找相应的教程：

<https://docs.oracle.com/javase/tutorial/reflect/>

## 三种混淆名称

如果大家打开过`minecraft.jar`的话，可能会注意到类名等全部都是`afh`、`pe`、`zw`等难记难用的名称，实际上如果我们打开`.class`文件去分析字节码，会发现方法名、属性名等也全都是类似的名称，如`afh/c`，`pe/a`，`zw/e`等，方法名由于不同参数的方法名称可以相同，混淆更为严重。这其实上就是Minecraft游戏本身提供的经混淆过的名称，我们称其为Notch Name。

然而我们作为开发者，在开发Forge Mod的时候，在开发环境看到的，并且使用到的类名称，都是些诸如`net.minecraft.block.Block`（正确记法应为`net/minecraft/block/Block`，后同），`net.minecraft.potion.Potion`，`net.minecraft.item.Item`的好看又好记的类名称，方法名、属性名等也同样类似，如`blockRegistry`、`potionTypes`、`itemRegistry`等。这些名称其实就是MCP（Minecraft Mod Coder Pack）项目根据代码逻辑，为Minecraft原本提供的经混淆过的名称赋予的一个新的可读性强的名称，我们称其为MCP Name。

如果我们打开一个已经编译好并发布成的成型模组的Jar文件，并观赛其中的字节码或者反编译其中的`.class`文件，会注意到本来在模组开发的时候应该使用的，也就是上面提到了的好看又好记的方法名、属性名等名称，变成了一些诸如`field_149771_c`、`field_76425_a`、`field_150901_e`等相对难以理解的名称。我们称这些名称为Srg Name，以纪念MCP项目开发的领导者Searge。Srg Name**大多**以`field`或`func`开头，**一般**最后一个下划线后的名称就是对应的Notch Name。Srg Name**一般**唯一，而不是像Notch Name或者MCP Name一样只有指定了类名和类型才能唯一。

## Forge是如何处理三种混淆名称的

首先，我们在开发模组的时候，使用的是MCP Name，不过有一点问题是，MCP Name因为其本身的性质，往往会有描述不够准确的情况，所以经常发生变动。Forge在构建模组的时候，会将其中的MCP Name（如`blockRegistry`），直接映射到Srg Name（如`field_149771_c`），这种行为我们一般称为**重混淆**（Reobfuscation），而Srg Name是相对Minecraft版本稳定的，同样一个Minecraft版本对应的Srg Name是相同的，不同的Minecraft版本差别也不大。

Forge在加载Minecraft类的时候，会通过修改中立字节码（ASM）的方式，把Minecraft提供的被混淆过的名称，也就是把Notch Name**反混淆**（Deobfuscation）成Srg Name。这正好和构建好的模组使用的名称对上，这样，整个模组就可以正常运行了。

经过重混淆和反混淆两个步骤后的代码，就会按照Srg Name的方式来运行了。

## 如何查找三种名称的对应关系

如果已经配置好了Forge Mod的开发环境，那么在用户目录的`.gradle`文件夹下的某些文件夹，其实就已经有了三种名称的对应关系对应的六个文件了，在作者的电脑中是`.gradle/caches/minecraft/de/oceanlabs/mcp/mcp_snapshot/20141130/srgs/`（1.8默认使用的MCP）文件夹和`.gradle/caches/minecraft/de/oceanlabs/mcp/mcp_stable/20/srgs/`（1.8.9默认使用的MCP）文件夹。请按照`build.gradle`文件内描述的项目本身的MCP版本确定该使用哪一个文件夹：

**`build.gradle（部分）:`**

    minecraft {
        version = "1.8.9-11.15.1.1722"
        runDir = "run"
        mappings = "stable_20" // MCP version comes here
    }

这六个后缀名为`.srg`的文件描述的就是三种名称的对应关系，我们这里打开一个`.gradle/caches/minecraft/de/oceanlabs/mcp/mcp_stable/20/srgs/mcp-srg.srg`文件作为示例，找出一个类的所有名称和方法对应的Srg Name。

这里有几点是显而易见的：

* 构造方法不会被混淆
* 涉及到第三方类（包名非`net.minecraft`开头）的方法不会被混淆
* 不同子类对同一个方法的覆写，混淆名称相同

下面是Forge提供的`BlockLeavesBase`类的源代码（为直观展示，这里删去了不必要的注释和注解）：

    package net.minecraft.block;

    import net.minecraft.block.material.Material;
    import net.minecraft.util.BlockPos;
    import net.minecraft.util.EnumFacing;
    import net.minecraft.world.IBlockAccess;

    public class BlockLeavesBase extends Block
    {
        protected boolean fancyGraphics;

        protected BlockLeavesBase(Material materialIn, boolean fancyGraphics)
        {
            super(materialIn);
            this.fancyGraphics = fancyGraphics;
        }

        public boolean isOpaqueCube()
        {
            return false;
        }

        public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
        {
            return !this.fancyGraphics && worldIn.getBlockState(pos).getBlock() == this ? false : super.shouldSideBeRendered(worldIn, pos, side);
        }
    }

我们先从简单的`fancyGraphics`属性开始，我们在`mcp-srg.srg`文件中搜索“类名/属性名”，也就是`BlockLeavesBase/fancyGraphics`，很容易就找到了一条记录：

    FD: net/minecraft/block/BlockLeavesBase/fancyGraphics net/minecraft/block/BlockLeavesBase/field_150121_P

`field_150121_P`自然就是Srg Name了。方法也是同理，我们在其中查找`BlockLeavesBase/isOpaqueCube`和`BlockLeavesBase/shouldSideBeRendered`：

    MD: net/minecraft/block/BlockLeavesBase/isOpaqueCube ()Z net/minecraft/block/BlockLeavesBase/func_149662_c ()Z
    MD: net/minecraft/block/BlockLeavesBase/shouldSideBeRendered (Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z net/minecraft/block/BlockLeavesBase/func_176225_a (Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z

很容易得到`func_149662_c`和`func_176225_a`两个Srg Name。

不过方法的查找往往并不是那么一帆风顺，比如我们试图查找`CommandBase`类的`parseDouble`方法，就会遇到下面足足五条记录：

    MD: net/minecraft/command/CommandBase/parseDouble (Ljava/lang/String;D)D net/minecraft/command/CommandBase/func_180526_a (Ljava/lang/String;D)D
    MD: net/minecraft/command/CommandBase/parseDouble (Ljava/lang/String;)D net/minecraft/command/CommandBase/func_175765_c (Ljava/lang/String;)D
    MD: net/minecraft/command/CommandBase/parseDouble (DLjava/lang/String;Z)D net/minecraft/command/CommandBase/func_175761_b (DLjava/lang/String;Z)D
    MD: net/minecraft/command/CommandBase/parseDouble (DLjava/lang/String;IIZ)D net/minecraft/command/CommandBase/func_175769_b (DLjava/lang/String;IIZ)D
    MD: net/minecraft/command/CommandBase/parseDouble (Ljava/lang/String;DD)D net/minecraft/command/CommandBase/func_175756_a (Ljava/lang/String;DD)D

这五条记录的Srg Name都不一样，然而这是合理的，因为在Forge Mod开发环境中我们可以非常轻易地看到，`CommandBase`类确实有着五个名为`parseDouble`的同名方法，只不过方法参数不同而已。

我们只能把目标放在这五条纪录剩下的不同地方了：

* `(Ljava/lang/String;D)D`
* `(Ljava/lang/String;)D`
* `(DLjava/lang/String;Z)D`
* `(DLjava/lang/String;IIZ)D`
* `(Ljava/lang/String;DD)D`

这其实描述的就是方法参数，不过这是在`.class`文件中的描述方式，所以感兴趣的读者可以阅读下一部分，也就是属性名和方法名在`.class`文件中的存储方式，对于不感兴趣的读者，因为在代码中使用反射机制往往只需要考虑属性，不需要查找方法的各种混淆名称的映射关系，所以可以跳过下面这一部分。

## 属性名和方法名的存储方式

Notch Name、MCP Name、Srg Name等名称的命名和使用，和这些名称在字节码中的存储方式息息相关，不过这里我们还用不到分析字节码，但是这里为了方便理解，作者还是决定讲一讲属性（Field）和方法（Method）在`.class`文件中存储的名称是什么。

不管是属性，还是方法，在`.class`文件中描述它们的方式都是三个字符串：类名称、自身名称、类型描述符。

我们一个一个来。

#### 类名称

关于类名称需要注意的几点：

* 类名称需要包含包名
* 类名称中的小数点被换成了斜线
* 内部类使用美元符号而非小数点分隔

比如我们最常使用的`Block`类（`net.minecraft.block.Block`）对应的类名称就是：`net/minecraft/block/Block`。其中的一个内部类`SoundType`（`net.minecraft.block.Block.SoundType`）就是：`net/minecraft/block/Block$SoundType`。

#### 自身名称

自身名称就是这个属性或方法使用时的名称，这里只有一点需要说明，就是构造方法的名称为`<init>`。

#### 类型描述符

这里就是比较复杂的地方了。我们先从八种基本数据类型开始。

八种基本数据类型中的每一个都使用一个大写字母表示：

* `byte`：`B`
* `char`：`C`
* `double`：`D`
* `float`：`F`
* `int`：`I`
* `long`：`J`
* `short`：`S`
* `boolean`：`Z`

引用类型使用`L`+类名称（同样需要包含包名和使用斜线）+`;`（分号）的方式去表示，比如如果一个属性是我们最常使用的`Block`类的实例，那么就应当表示为`Lnet/minecraft/block/Block;`

还剩下一种类型就是数组类型，使用`[`（左中括号）+元素类型的方式表示，比如：

* `double[]`：`[D`
* `long[][]`：`[[J`
* `String[]`：`[Ljava/lang/String;`

上面所述就是属性的类型描述符的全部表示方法了，方法的类型描述符就相对复杂些了。

首先，方法的返回值多出一个`void`类型，使用大写字母`V`表示。

其次，因为方法比属性多出方法参数这一性质，所以方法的类型描述符要使用`(`+参数类型描述符的叠加（不需要任何分隔符）+`)`+返回值类型描述符的方式，构造方法的返回类型是`void`（也就是大写字母`V`）。比如：

* `void finalize()`：`()V`
* `boolean equals(Object obj)`:`(Ljava/lang/Object;)Z`
* `String toString()`:`()Ljava/lang/String;`
* `String format(String format, Object ... args)`:`(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;`
* `String(char value[])`：`([C)V`

#### 实战演练

下面是Forge提供的`ItemLeaves`类的源代码（为直观展示，这里删去了不必要的注释和注解）：

    package net.minecraft.item;
    
    import net.minecraft.block.BlockLeaves;
    
    public class ItemLeaves extends ItemBlock
    {
        private final BlockLeaves leaves;
    
        public ItemLeaves(BlockLeaves block)
        {
            super(block);
            this.leaves = block;
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }
    
        public int getMetadata(int damage)
        {
            return damage | 4;
        }
    
        public int getColorFromItemStack(ItemStack stack, int renderPass)
        {
            return this.leaves.getRenderColor(this.leaves.getStateFromMeta(stack.getMetadata()));
        }
    
        public String getUnlocalizedName(ItemStack stack)
        {
            return super.getUnlocalizedName() + "." + this.leaves.getWoodType(stack.getMetadata()).getUnlocalizedName();
        }
    }

请指出每个属性和方法（包括构造方法）的类名称、自身名称和类型描述符：

* `net/minecraft/item/ItemLeaves`，`leaves`，`Lnet/minecraft/block/BlockLeaves;`
* `net/minecraft/item/ItemLeaves`，`<init>`，`(Lnet/minecraft/block/BlockLeaves;)V`
* `net/minecraft/item/ItemLeaves`，`getMetadata`，`(I)I`
* `net/minecraft/item/ItemLeaves`，`getColorFromItemStack`，`(Lnet/minecraft/item/ItemStack;I)I`
* `net/minecraft/item/ItemLeaves`，`getUnlocalizedName`，`(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;`

## 反射的注意事项

以最常见的药水效果为例，如果我们想要修改`potionTypes`数组的大小（这往往是添加药水效果的第一步），因为`potionTypes`数组是被标记为`public static final`的，我们可能会这么做：

    for (Field field : Potion.class.getDeclaredFields())
    {
        field.setAccessible(true);
        try
        {
            if ("potionTypes".equals(field.getName()))
            {
                Field fieldmodifiers = Field.class.getDeclaredField("modifiers");
                fieldmodifiers.setAccessible(true);
                fieldmodifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                Potion[] potionTypes = (Potion[]) field.get(null);
                field.set(null, Arrays.copyOf(potionTypes, 256));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

运行、测试、似乎没有什么问题。然而真的没有问题吗？

我们之前说过，Forge在构建模组时，会将MCP Name重混淆成Srg Name，然而`"potionTypes"`这个**字符串**，有没有被替换掉呢？

我们在开发环境中运行、测试的时候，MCP Name没有被重混淆掉，所以我们没有发现问题，然而构建好的模组在运行时，问题就会突显出来。

所以，我们要先去查找`potionTypes`数组对应的Srg Name：

    FD: net/minecraft/potion/Potion/potionTypes net/minecraft/potion/Potion/field_76425_a

然后做出修改：

    for (Field field : Potion.class.getDeclaredFields())
    {
        field.setAccessible(true);
        try
        {
            if ("potionTypes".equals(field.getName()) || “field_76425_a”.equals(field.getName()))
            {
                Field fieldmodifiers = Field.class.getDeclaredField("modifiers");
                fieldmodifiers.setAccessible(true);
                fieldmodifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                Potion[] potionTypes = (Potion[]) field.get(null);
                field.set(null, Arrays.copyOf(potionTypes, 256));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

或者直接干脆不使用名称标记，转而去使用匹配类型的方式：

    for (Field field : Potion.class.getDeclaredFields())
    {
        field.setAccessible(true);
        try
        {
            if (Potion.class.equals(field.getType().getComponentType()))
            {
                Field fieldmodifiers = Field.class.getDeclaredField("modifiers");
                fieldmodifiers.setAccessible(true);
                fieldmodifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                Potion[] potionTypes = (Potion[]) field.get(null);
                field.set(null, Arrays.copyOf(potionTypes, 256));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

或者直接使用序号的方式来解决：

    Field field = Potion.class.getDeclaredFields()[0];
    field.setAccessible(true);
    try
    {
        Field fieldmodifiers = Field.class.getDeclaredField("modifiers");
        fieldmodifiers.setAccessible(true);
        fieldmodifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        Potion[] potionTypes = (Potion[]) field.get(null);
        field.set(null, Arrays.copyOf(potionTypes, 256));
    }
    catch (Exception e)
    {
        throw new RuntimeException(e);
    }

这样就没有问题了。

最后给出一点提醒：**在使用反射获取方法或者属性的时候，需要同时注意Srg Name和MCP Name两种情况，如无必要，尽量避免涉及方法名或属性名，可以考虑使用类型匹配的方式，或者直接使用序号的方式来解决**。

## Forge为反射提供的工具

Forge为反射提供的工具，可能就是`EnumHelper`、`ReflectionHelper`和`ObfuscationReflectionHelper`三个类了。我们先从`EnumHelper`说起，再对`ReflectionHelper`和`ObfuscationReflectionHelper`进行一点简要的介绍。

#### `EnumHelper`

我们知道，如果想要添加Minecraft的一些特性，就不得不要为一些枚举类添加新的实例，然而这是使用不包括反射机制在内的正常方式做不到的，所以Forge提供了一个名为`EnumHelper`的类（以及其子类`EnumHelperClient`），以实现对枚举类的新实例添加，这就是`addEnum`方法。

然而，`addEnum`方法通过调用的是一个更普遍的，名为`setFailsafeFieldValue`的方法，这个方法通过直接访问Java的反射机制的底层实现方式来完成对枚举类型的添加。通过这个方法，我们可以很方便地设置一些被标记为`public final`甚至`private final`的属性的值。

我们还是以上面的修改`potionTypes`数组为例：

    for (Field field : Potion.class.getDeclaredFields())
    {
        field.setAccessible(true);
        try
        {
            if ("potionTypes".equals(field.getName()) || “field_76425_a”.equals(field.getName()))
            {
                Potion[] potionTypes = (Potion[]) field.get(null);
                EnumHelper.setFailsafeFieldValue(field, null, Arrays.copyOf(potionTypes, 256));
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

代码一下子简洁了很多。

#### `ReflectionHelper`和`ObfuscationReflectionHelper`

`ReflectionHelper`的作用是方便地通过反射的方式获取一些属性或者方法，或者设置被标记为私有的属性的值。而`ObfuscationReflectionHelper`则是把`ReflectionHelper`稍微包装了一下，包括处理异常、反混淆（Notch to Srg）名称等。

我们把上面的代码再次用`ReflectionHelper`简化一下：

    Field field = ReflectionHelper.findField(Potion.class, "potionTypes", “field_76425_a”);
    Potion[] potionTypes = (Potion[]) field.get(null);
    EnumHelper.setFailsafeFieldValue(field, null, Arrays.copyOf(potionTypes, 256));

着实方便了很多。
