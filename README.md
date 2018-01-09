# Minecraft 1.8.9 FML Mod 开发教程

**[蓝色](#)**的链接表示这部分已写完，**黑色**的文本表示正在填坑。。。

如果该教程和源代码存在问题，可以通过电邮的方式：[zzzz@infstudio.net](mailto:zzzz@infstudio.net)，或者发送[GitHub Issue](https://github.com/ustc-zzzz/fmltutor/issues)。

教程所属的代码仓库的`master`分支是根据所有维护的patch自动生成的，因此**请不要向`master`分支发送Pull Request**。教程的文字部分由`book`分支维护，代码部分由`patch`分支维护。如有必要，请向这两个分支发送Pull Request。

## 目录

### 0 [绪论](0-绪论.md)

### 1 [基础部分](#1-基础部分)

* [配置你的工作环境](1.1-配置你的工作环境.md)
* [主类、代理和Mod信息](1.2-主类、代理和Mod信息.md)
* [第一个物品](1.3-第一个物品.md)
* [第一个方块](1.4-第一个方块.md)
* [创造模式物品栏](1.5-创造模式物品栏.md)
* [第一份合成表、烧炼规则和燃料](1.6-第一份合成表、烧炼规则和燃料.md)
* [本地化和国际化](1.7-本地化和国际化.md)
* [创建一份配置文件](1.8-创建一份配置文件.md)

### 2 [初级部分](#2-初级部分)

#### 2.1 [事件](#21-事件)

* [注册已有的事件](2.1.1-注册已有的事件.md)
* [自定义新的事件](2.1.2-自定义新的事件.md)

#### 2.2 [高级物品](#22-高级物品)

* [新的工具](2.2.1-新的工具.md)
* [新的食物](2.2.2-新的食物.md)
* [新的盔甲](2.2.3-新的盔甲.md)

#### 2.3 [生物状态](#23-生物状态)

* [新的伤害类型](2.3.1-新的伤害类型.md)
* [新的附魔属性](2.3.2-新的附魔属性.md)
* [新的药水效果](2.3.3-新的药水效果.md)

#### 2.4 [玩家体验与系统](#24-玩家体验与系统)

* [热键绑定](2.4.1-热键绑定.md)
* [成就系统](2.4.2-成就系统.md)
* [系统命令](2.4.3-系统命令.md)
* [声音系统](2.4.4-声音系统.md)

#### 2.5 [矿物](#25-矿物)

* [在世界生成矿物](2.5.1-在世界生成矿物.md)
* [注册和使用矿物字典](2.5.2-注册和使用矿物字典.md)

#### 2.6 [流体](#26-流体)

* [创建并注册一份流体](2.6.1-创建并注册一份流体.md)
* [为流体添加对应的桶](2.6.2-为流体添加对应的桶.md)

### 3 [中级部分](#3-中级部分)

#### 3.1 [生物](#31-生物)

* [新的生物和对应的刷怪蛋](3.1.1-新的生物和对应的刷怪蛋.md)
* [新的生物渲染模型](3.1.2-新的生物渲染模型.md)
* [生物的固有属性和自然生成](3.1.3-生物的固有属性和自然生成.md)
* [生物的自动同步和数据存储](3.1.4-生物的自动同步和数据存储.md)
* [为生物绑定AI](3.1.5-为生物绑定AI.md)
* [投掷物的注册和生成](3.1.6-投掷物的注册和生成.md)
* [FakePlayer的获取与使用](3.1.7-FakePlayer的获取与使用.md)

#### 3.2 [高级方块](#32-高级方块)

* [BlockState和Metadata](3.2.1-BlockState和Metadata.md)
* [TileEntity与数据更新](3.2.2-TileEntity与数据更新.md)
* [BlockState的简化映射](3.2.3-BlockState的简化映射.md)
* [扩展BlockState和第三方模型格式](3.2.4-扩展BlockState和第三方模型格式.md)

#### 3.3 [附加数据与同步](#33-附加数据与同步)

* [Capability系统与已有实体附加属性](3.3.1-Capability系统与已有实体附加属性.md)
* [使用SimpleImpl同步数据](3.3.2-使用SimpleImpl同步数据.md)
* [世界附加数据存储](3.3.3-世界附加数据存储.md)

#### 3.4 [GUI](#34-gui)

* [创建一个新的GUI界面](3.4.1-创建一个新的GUI界面.md)
* [GUI界面的个性化与物品槽的添加](3.4.2-GUI界面的个性化与物品槽的添加.md)
* [GUI界面中的交互](3.4.3-GUI界面中的交互.md)
* [GUI中的数据同步](3.4.4-GUI中的数据同步.md)

#### 3.5 [与其他Mod交互](#35-与其他mod交互)

* [使用其他Mod的API](3.5.1-使用其他Mod的API.md)
* [向其他Mod提供API](3.5.2-向其他Mod提供API.md)

### 4 高级部分

#### 4.1 渲染器

* Minecraft对OpenGL的包装
* Tessellator的使用
* TileEntity渲染器
* 实体渲染器

#### 4.2 世界生成

* 生成一棵树
* 新的生物群系
* 新的世界

#### 4.3 ASM与Coremod

* 中立字节码与ASM
* Coremod的制作

### [附录](#附录)

* [事件列表](附录A-事件列表.md)
* [配置Gradle的一些小技巧](附录B-配置Gradle的一些小技巧.md)
* [混淆与反射](附录C-混淆与反射.md)
* [使用AccessTransformers](附录D-使用AccessTransformers.md)
* [使用Scala编写Mod](附录E-使用Scala编写Mod.md)

## 版权声明

本作品作者为ustc_zzzz。

[Infinity Studio小组](https://www.infstudio.net/)与本作品作者共有版权。

<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/">
    <img alt="知识共享许可协议" style="border-width:0" src="https://i.creativecommons.org/l/by-sa/4.0/88x31.png" />
</a><br />本作品采用
<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/">
    知识共享署名-相同方式共享 4.0 国际许可协议
</a>进行许可。

转载请附上本作品链接：
<https://fmltutor.ustc-zzzz.net/>

另外，本作品同时提供[源代码](https://github.com/ustc-zzzz/fmltutor/tree/master)，所有源代码使用[MIT协议](https://github.com/ustc-zzzz/fmltutor/blob/master/LICENSE)开源。

<p>Hosted by <a href="https://pages.coding.me" style="font-weight: bold">Coding Pages</a></p>

