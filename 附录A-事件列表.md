## 概述

本部分总结了Minecraft 1.8版本下的Forge提供的所有Minecraft事件。该部分尚未完成。

2016-4-19 更新：

* 已完成字母A～R。
* 已完成事件**189**个。

2016-4-10 更新：

* 已完成字母A～P。
* 已完成事件**171**个。

2016-2-23 更新：

* 已完成字母A～M。
* 已完成事件**154**个。

2015-12-13 更新：

* 已完成字母A～F。
* 已完成事件**131**个。

2015-11-18 更新：

* 已完成字母A～E。
* 已完成事件**113**个。

## 说明

* `[F] :` <br> 该事件或其存在子事件应当在`FMLCommonHandler.getInstance().bus()`中注册。
* `[E] :` <br> 该事件或其存在子事件应当在`MinecraftForge.EVENT_BUS`中注册。
* `[T] :` <br> 该事件或其存在子事件应当在`MinecraftForge.TERRAIN_BUS`中注册。
* `[O] :` <br> 该事件或其存在子事件应当在`MinecraftForge.ORE_GEN_BUS`中注册。
* `[N] :` <br> 该事件或其存在子事件应当在`FMLEventChannel.eventBus`中注册。
* `[C] :` <br> 该事件拥有`@Cancelable`注解，换言之该事件可取消。
* `[R] :` <br> 该事件拥有`@HasResult`注解，换言之该事件拥有结果。

#### [EC] AnvilUpdateEvent

当玩家将物品放置在铁砧上时触发。若事件被取消，则玩家不能修复物品。

常量：

* `public final ItemStack left;`
* `public final ItemStack right;`
* `public final String name;`

变量：

* `public ItemStack output;`
* `public int cost;`
* `public int materialCost;`

#### [T] BiomeEvent

当与生物群系相关的操作执行时触发。

常量：

* `public final BiomeGenBase biome;`

#### [T] BiomeEvent.BiomeColor extends BiomeEvent

当所有与生物群系颜色相关的操作执行时触发。

常量：

* `public final int originalColor;`

变量：

* `public int newColor;`

#### [T] BiomeEvent.GetFoliageColor extends BiomeEvent.BiomeColor

当一个生物群系试图去查找其中绿色植物方块（如藤蔓、树叶等）的颜色时触发。

#### [T] BiomeEvent.GetGrassColor extends BiomeEvent.BiomeColor

当一个生物群系试图去查找其中草方块上方覆盖的草的颜色时触发。

#### [T] BiomeEvent.GetWaterColor extends BiomeEvent.BiomeColor

当一个生物群系试图去查找其中水的颜色时触发。

#### [T] BiomeEvent.CreateDecorator extends BiomeEvent

当一个`BiomeDecorator`类被实例化时触发。

常量：

* `public final BiomeDecorator originalBiomeDecorator;`

变量：

* `public BiomeDecorator newBiomeDecorator;`

#### [TR] BiomeEvent.GetVillageBlockID extends BiomeEvent

当村庄生成器试图去选择一个对应生物群系的方块（如平原的木头、沙漠的沙石等）时触发。你可以覆盖这个方块的默认值。

常量：

* `public final IBlockState original;`

变量：

* `public IBlockState replacement;`

#### [E] BlockEvent

当所有与方块状态变化的操作执行时触发。

常量：

* `public final World world;`
* `public final BlockPos pos;`
* `public final IBlockState state;`

#### [EC] BlockEvent.Breakevent extends BlockEvent

当方块即将被破坏时触发。取消该事件会阻止方块被破坏。

方法：

* `public EntityPlayer getPlayer()` <br> 破坏该方块的玩家
* `public int getExpToDrop()` <br> 获取破坏该方块得到的经验
* `public void setExpToDrop(int exp)` <br> 设置破坏该方块得到的经验

#### [E] BlockEvent.HarvestDropsEvent extends BlockEvent

当方块破坏后即将掉落对应物品时触发。`fortuneLevel`常量表示附魔等级，`drops`常量表示掉落物，`isSilkTouching`常量表示是否为精准采集，`dropChance`变量表示掉落机率，设为`1.0F`可以保证总会掉落，`harvester`常量表示破坏方块的玩家，如果爆炸或者机器破坏则为`null`。

常量：

* `public final int fortuneLevel;`
* `public final List<ItemStack> drops;`
* `public final boolean isSilkTouching;`
* `public final EntityPlayer harvester;`

变量：

* `public float dropChance;`

#### [EC] BlockEvent.NeighborNotifyEvent extends BlockEvent

当方块被周围方块提醒进行了一次方块更新时触发。取消该事件会阻止这一行为。

方法：

* `public EnumSet<EnumFacing> getNotifiedSides()` <br> 获取提醒更新的面

#### [E] NoteBlockEvent extends BlockEvent

当与音符盒方块相关的操作执行时触发。

方法：

* `public Note getNote()` <br> 获取音调（C C# D D# E F F# G G# A A# B）。
* `public Octave getOctave()` <br> 获取音调所在八度（低 中 高）
* `public int getVanillaNoteId()` <br> 获取音调id
* `public void setNote(Note note, Octave octave)` <br> 设置音调和音调所在八度

#### [EC] NoteBlockEvent.Change extends NoteBlockEvent

当音符盒即将被改变时触发。取消该事件会阻止音符盒的改变和演奏行为。

常量：

* `public final Note oldNote;`
* `public final Octave oldOctave;`

#### [EC] NoteBlockEvent.Play extends NoteBlockEvent

当音符盒即将被演奏时触发。取消该事件会阻止音符盒的演奏行为。

变量：

* `public Instrument instrument;`

#### [EC] BlockEvent.PlaceEvent extends BlockEvent

当方块即将被玩家放置时触发。取消该事件会阻止放置方块的行为。

常量：

* `public final EntityPlayer player;`
* `public final ItemStack itemInHand;`
* `public final BlockSnapshot blockSnapshot;`
* `public final IBlockState placedBlock;`
* `public final IBlockState placedAgainst;`

#### [EC] BlockEvent.MultiPlaceEvent extends BlockEvent.PlaceEvent

当多个方块即将被玩家放置时触发（如床），取消该事件会阻止放置方块的行为。

方法：

* `public List<BlockSnapshot> getReplacedBlockSnapshots()` <br> 获取即将被替换的方块列表，多数可能会是空气。

#### [ET] ChunkProviderEvent

当所有和区块生成相关的操作执行时触发。

常量：

* `public final IChunkProvider chunkProvider;`

#### [TR] ChunkProviderEvent.InitNoiseField extends ChunkProviderEvent

当区块地形噪声场即将初始化的时候触发。通过设置`Result`为`DENY`可以自定义区块地形噪声场（`noisefield`）。

常量：

* `public final int posX;`
* `public final int posY;`
* `public final int posZ;`
* `public final int sizeX;`
* `public final int sizeY;`
* `public final int sizeZ;`

变量：

* `public double[] noisefield;`

#### [ET] PopulateChunkEvent extends ChunkProviderEvent

当包含区块地形填充相关的操作执行时触发。其中`world`常量表示事件所在的世界，`rand`常量表示一个用于生成这个区块的随机数发生器实例，`chunkX`常量表示区块的X坐标，`chunkZ`常量表示区块的Z坐标，`hasVillageGenerated`常量表示这个区块是否已经有村庄在上方生成。

常量：

* `public final World world;`
* `public final Random rand;`
* `public final int chunkX;`
* `public final int chunkZ;`
* `public final boolean hasVillageGenerated;`

#### [TR] PopulateChunkEvent.Populate extends PopulateChunkEvent

当区块地形填充的时候触发。详见方法“`ChunkProviderEnd#populate(IChunkProvider, int, int)`”，“`ChunkProviderGenerate#populate(IChunkProvider, int, int)`”，和“`ChunkProviderHell#populate(IChunkProvider, int, int)`”。

常量：

* `public final EventType type;`

#### [E] PopulateChunkEvent.Post extends PopulateChunkEvent

当区块地形填充完成的时候触发。详见方法“`ChunkProviderEnd#populate(IChunkProvider, int, int)`”，“`ChunkProviderGenerate#populate(IChunkProvider, int, int)`”，和“`ChunkProviderHell#populate(IChunkProvider, int, int)`”。

#### [E] PopulateChunkEvent.Pre extends PopulateChunkEvent

当即将进行区块地形填充的时候触发。详见方法“`ChunkProviderEnd#populate(IChunkProvider, int, int)`”，“`ChunkProviderGenerate#populate(IChunkProvider, int, int)`”，和“`ChunkProviderHell#populate(IChunkProvider, int, int)`”。

#### [ER] ChunkProviderEvent.ReplaceBiomeBlocks extends ChunkProviderEvent

当区块即将被替换（如生成基岩）的时候触发。通过设置`Result`为`DENY`可以自定义替换方式。

#### [E] ChunkWatchEvent

当包含和监视区块相关的操作执行时触发。`chunk`常量表示被监视的区块，而`player`常量表示监视的玩家。

常量：

* `public final ChunkCoordIntPair chunk;`
* `public final EntityPlayerMP player;`

#### [E] ChunkWatchEvent.UnWatch extends ChunkWatchEvent

当玩家停止监视区块时触发。

#### [E] ChunkWatchEvent.Watch extends ChunkWatchEvent

当玩家开始监视区块时触发。

#### [EC] ClientChatReceivedEvent

当客户端收到聊天消息时触发。`message`变量表示传送的消息，`type`常量表示文字消息的类型。

常量：

* `public final byte type;`

变量：

* `public IChatComponent message;`

#### [EC] CommandEvent

当控制台命令即将被执行时触发。取消这个事件将导致这个命令不再被执行。

常量：

* `public final ICommand command;`
* `public final ICommandSender sender;`

变量：

* `public String[] parameters;`
* `public Throwable exception;`

#### [FC] ConfigChangedEvent

当Mod的图形界面配置相关的操作执行的时候触发。

常量：

* `public final String modID;`
* `public final boolean isWorldRunning;`
* `public final boolean requiresMcRestart;`
* `public final String configID;`
    
#### [FC] ConfigChangedEvent.OnConfigChangedEvent extends ConfigChangedEvent

当Mod的图形界面配置刚刚完成的时候触发。通过设置`Result`为`DENY`可以阻止触发`ConfigChangedEvent.PostConfigChangedEvent`事件。

#### [FC] ConfigChangedEvent.PostConfigChangedEvent extends ConfigChangedEvent

当事件`ConfigChangedEvent.OnConfigChangedEvent`完成的时候触发。检查Mod的图形界面配置情况，只有`ConfigChangedEvent.OnConfigChangedEvent`的`Result`没有被设置成`DENY`才可以被触发。

#### [N] FMLNetworkEvent.CustomNetworkEvent &lt;T extends INetHandler&gt;

当诸如`NetworkHandshakeEstablished`的自定义事件被触发时触发。

常量：

* `public final Object wrappedEvent;`

#### [T] DecorateBiomeEvent

当一个`BiomeDecorator`类在`DeferredBiomeDecorator#fireCreateEventAndReplace(BiomeGenBase)`中被实例化时触发。`world`常量表示事件发生时的世界，`rand`常量表示一个随机数生成器的实例，`pos`常量示正在被点缀的区块坐标。

常量：

* `public final World world;`
* `public final Random rand;`
* `public final BlockPos pos;`

#### [TR] DecorateBiomeEvent.Decorate extends DecorateBiomeEvent

当一个区块被生物群系特性点缀（如树、水和岩浆池）的时候触发。通过设置`Result`为`DENY`可以阻止默认的区块点缀。

常量：

* `public final EventType type;`

#### [T] DecorateBiomeEvent.Post extends DecorateBiomeEvent

当一个区块被生物群系点缀完成的时候触发。

#### [T] DecorateBiomeEvent.Pre extends DecorateBiomeEvent

当一个区块即将被生物群系点缀的时候触发。

#### [EC] DrawBlockHighlightEvent

当一个方块被玩家用鼠标选中的时候触发。取消这一事件会阻止客户端绘制选择框。

常量：

* `public final RenderGlobal context;`
* `public final EntityPlayer player;`
* `public final MovingObjectPosition target;`
* `public final int subID;`
* `public final ItemStack currentItem;`
* `public final float partialTicks;`

#### [E] EntityEvent

当与实体相关的操作执行时触发，`entity`常量表示该实体。

常量：

* `public final Entity entity;`

#### [E] EntityEvent.CanUpdate extends EntityEvent

当实体生成时触发，`canUpdate`变量表示是否允许该实体更新，默认为假。

变量：

* `public boolean canUpdate;`

#### [E] EntityEvent.EnteringChunk extends EntityEvent

当实体进入一个区块时触发。

变量：

* `public int newChunkX;`
* `public int newChunkZ;`
* `public int oldChunkX;`
* `public int oldChunkZ;`

#### [E] EntityEvent.EntityConstructing extends EntityEvent

当实体即将生成时触发。

#### [EC] EntityJoinWorldEvent extends EntityEvent

当实体即将加入世界时触发。取消该事件会阻止该实体加入世界。

常量：

* `public final World world;`

#### [EC] EntityMountEvent extends EntityEvent

当实体即将挂载（如上马）或卸载（如下马）另一实体时触发。取消该事件会阻止该实体的这一操作。其中的`entityBeingMounted`常量可能为空。

常量：

* `public final Entity entityMounting;`
* `public final Entity entityBeingMounted;`
* `public final World worldObj;`

方法：

* `public boolean isMounting()` <br> 返回是否为挂载事件
* `public boolean isDismounting()` <br> 返回是否为卸载事件

#### [EC] EntityStruckByLightningEvent extends EntityEvent

当实体即将被闪电击中时触发。取消该事件会阻止该实体被闪电击中。

常量：

* `public final EntityLightningBolt lightning;`

#### [E] ItemEvent extends EntityEvent

当与物品实体（如落在地上的物品）相关的操作执行时触发。`entityItem`常量表示该物品实体。

常量：

* `public final EntityItem entityItem;`

#### [EC] ItemExpireEvent extends ItemEvent

当掉落后的物品实体达到其生命周期时触发。取消该事件会阻止其消失，并继续存活一段给定的时间。`extraLife`变量表示该继续存活的时间，只在取消该事件时有效。

变量：

* `public int extraLife;`

#### [EC] ItemTossEvent extends ItemEvent

当玩家扔掉（如Q键）一个物品时触发。取消该事件会阻止其加入世界，但不会阻止其从容器中消失。换句话说，这个物品永远地消失了。

常量：

* `public final EntityPlayer player;`

#### [E] LivingEvent extends EntityEvent

当与有生命实体相关的操作执行时触发。`entityLiving`常量表示该有生命实体。

常量：

* `public final EntityLivingBase entityLiving;`

#### [EC] EnderTeleportEvent extends LivingEvent

当有生命实体即将进行末影瞬移（如玩家使用末影珍珠和末影人的自身行为）时触发。取消该事件将阻止这一行为。

变量：

* `public double targetX;`
* `public double targetY;`
* `public double targetZ;`
* `public float attackDamage;`

#### [EC] LivingAttackEvent extends LivingEvent

当有生命实体被攻击（包括跌落、溺水等自然伤害）时触发。取消这一事件会使得该次攻击无效。`source`常量表示伤害类型，而`ammount`（一定是拼写错误！一定是！！！）常量表示伤害点数。

常量：

* `public final DamageSource source;`
* `public final float ammount;`

#### [EC] LivingDeathEvent extends LivingEvent

当有生命实体濒临死亡时触发。取消这一事件会使其不会死亡。

常量：

* `public final DamageSource source;`

#### [EC] LivingDropsEvent extends LivingEvent

当有生命实体死亡后即将掉落物品时触发。取消这一事件会使其不会掉落物品。`source`常量表示伤害类型，`drops`常量表示掉落物品实体列表，`lootingLevel`常量表示抢夺等级，`recentlyHit`表示该实体最近是否受到攻击。

常量：

* `public final DamageSource source;`
* `public final List<EntityItem> drops;`
* `public final int lootingLevel;`
* `public final boolean recentlyHit;`

#### [EC] PlayerDropsEvent extends LivingDropsEvent

当玩家死亡后即将掉落物品时触发。取消这一事件会使其重生时所有物品不减少。

常量：

* `public final EntityPlayer entityPlayer;`

#### [EC] LivingExperienceDropEvent extends LivingEvent

当有生命实体被玩家杀死后即将掉落经验时触发。取消这一事件会使其不会掉落经验。

方法：

* `public int getDroppedExperience()` <br> 获取掉落经验
* `public void setDroppedExperience(int droppedExperience)` <br> 设置掉落经验
* `public EntityPlayer getAttackingPlayer()` <br> 获取杀死该有生命实体的玩家
* `public int getOriginalExperience()` <br> 获取原来应该掉落的经验

#### [EC] LivingFallEvent extends LivingEvent

当有生命实体跌落时触发。取消这一事件会阻止其跌落。

变量：

* `public float distance;`
* `public float damageMultiplier;`

#### [EC] LivingHealEvent extends LivingEvent

当有生命实体治疗时触发。取消这一事件会阻止其治疗。

变量：

* `public float amount;`

#### [EC] LivingHurtEvent extends LivingEvent

当有生命实体受到伤害（包括跌落、溺水等自然伤害）时触发。取消这一事件会使其没有被伤害。`source`常量表示伤害类型，而`ammount`（一定是拼写错误！一定是！！！）常量表示伤害点数。

常量：

* `public final DamageSource source;`
* `public float ammount;`

#### [EC] LivingEvent.LivingJumpEvent extends LivingEvent

当有生命实体跳跃时触发。取消这一事件会阻止其跳跃。

#### [ER] LivingPackSizeEvent extends LivingEvent

当存在有生命实体即将因为系统对同时生成数量的最大限制而拒绝生成时触发。通过设置`Result`为`ALLOW`可以使该有生命实体同时生成数量的最大限制为指定值。`maxPackSize`变量表示指定的同时生成数量的最大限制。

变量：

* `public int maxPackSize;`

#### [E] LivingSetAttackTargetEvent extends LivingEvent

当有生命实体选择一个实体作为攻击对象时触发。`target`常量表示选择的对象。

常量：

* `public final EntityLivingBase target;`

#### [E] LivingSpawnEvent extends LivingEvent

当与有生命实体在世界生成和消失相关的操作执行时触发。`world`常量表示生成的世界，`x`常量表示其X坐标，`y`常量表示其Y坐标，`z`常量表示其Z坐标。

常量：

* `public final World world;`
* `public final float x;`
* `public final float y;`
* `public final float z;`

#### [ER] LivingSpawnEvent.AllowDespawn extends LivingSpawnEvent

当有生命实体即将在世界消失时触发。通过设置`Result`为`DEFAULT`可以使其正常消失，通过设置`Result`为`ALLOW`可以使其强制消失，通过设置`Result`为`DENY`可以使其强制继续存活。

#### [ER] LivingSpawnEvent.CheckSpawn extends LivingSpawnEvent

当有生命实体即将在世界生成时触发。通过设置`Result`为`DEFAULT`可以使其正常生成，通过设置`Result`为`ALLOW`可以使其强制生成，通过设置`Result`为`DENY`可以使其强制直接消失。

#### [EC] LivingSpawnEvent.SpecialSpawn extends LivingSpawnEvent

当有生命实体即将通过刷怪笼刷出时触发。取消该事件可以使其不会刷出。

#### [EC] LivingEvent.LivingUpdateEvent extends LivingEvent

当有生命实体更新时触发。取消该事件会阻止该次更新。

#### [E] PlayerEvent extends LivingEvent

当与玩家相关的操作执行时触发。`entityPlayer`常量表示该玩家。

常量：

* `public final EntityPlayer entityPlayer;`

#### [EC] AchievementEvent extends PlayerEvent 

当玩家即将获得成就时触发。取消该事件会阻止玩家获得该成就。

常量：

* `public final Achievement achievement;`

#### [E] AnvilRepairEvent extends PlayerEvent

当玩家将修复过的物品从铁砧中拿出时触发。`breakChance`参数表示铁砧破损的概率，默认为12%。

常量：

* `public final ItemStack left;`
* `public final ItemStack right;`
* `public final ItemStack output;`

变量：

* `public float breakChance;`

#### [EC] ArrowLooseEvent extends PlayerEvent

当玩家即将把箭射出的时候触发。`bow`常量表示玩家使用的弓，而`charge`变量表示玩家的蓄力。

常量：

* `public final ItemStack bow;`

变量：

* `public int charge;`

#### [EC] ArrowNockEvent extends PlayerEvent

当玩家即将开始使用弓的时候触发。`result`变量表示玩家使用的弓的物品栏。

变量：

* `public ItemStack result;`

#### [EC] AttackEntityEvent extends PlayerEvent

当玩家即将攻击一个实体时触发。取消该事件会阻止玩家的攻击行为。

常量：

* `public final Entity target;`

#### [ECR] BonemealEvent extends PlayerEvent

当玩家即将使用骨粉催熟方块时触发。取消该事件会阻止玩家的催熟行为，通过设置`Result`为`ALLOW`可以使骨粉被使用而不产生催熟效果。`world`常量表示事件所在的世界，`pos`常量表示发生事件的坐标，`block`常量表示即将被催熟的方块。

常量：

* `public final World world;`
* `public final BlockPos pos;`
* `public final IBlockState block;`

#### [EC] PlayerEvent.BreakSpeed extends PlayerEvent

当玩家即将破坏方块时触发。`newSpeed`变量可用于设定新的挖掘速度，而`pos`常量的Y坐标如果为-1，则代表方块位置未知。

常量：

* `public final IBlockState state;`
* `public final float originalSpeed;`
* `public final BlockPos pos;`

变量：

* `public float newSpeed = 0.0f;`

#### [E] PlayerEvent.Clone extends PlayerEvent

当玩家被要求重新生成时触发，可能的情况包括死亡重生，和从末界返回主世界。

常量：

* `public final EntityPlayer original;`
* `public final boolean wasDeath;`

#### [EC] EntityInteractEvent extends PlayerEvent

当玩家即将和一个实体交互时触发。取消该事件可以阻止玩家和实体交互的行为。

常量：

* `public final Entity target;`

#### [ECR] EntityItemPickupEvent extends PlayerEvent

当玩家试图捡起物品时触发。取消该事件可以阻止玩家捡起物品的行为，通过设置`Result`为`ALLOW`可以使成就仍可以被达成、事件仍可以被触发、声音仍可以被播放，但是物品不会被捡起。

常量：

* `public final EntityItem item;`

#### [ECR] FillBucketEvent extends PlayerEvent

当玩家试图使用空桶时触发。取消该事件可以阻止玩家使用空桶的行为，通过设置`Result`为`ALLOW`可以设置自定义使用空桶的产生物，`result`变量表示自定义的产生物。

常量：

* `public final ItemStack current;`
* `public final World world;`
* `public final MovingObjectPosition target;`

变量：

* `public ItemStack result;`

#### [E] PlayerEvent.HarvestCheck extends PlayerEvent

当玩家即将挖掘一个方块时触发。`block`常量表示被挖掘的方块，而`success`变量可以用来设定该方块是否被成功挖掘。

常量：

* `public final Block block;`

变量：

* `public boolean success;`

#### [E] ItemTooltipEvent extends PlayerEvent

当关于物品的提示信息即将被显示时触发。`showAdvancedItemTooltips`常量表示是否显示详细的提示信息（如按F3+H时显示的信息），`itemStack`常量表示用于显示提示信息的物品，`toolTip`常量表示即将显示的提示信息列表。

常量：

* `public final boolean showAdvancedItemTooltips;`
* `public final ItemStack itemStack;`
* `public final List<String> toolTip;`

#### [E] PlayerEvent.LoadFromFile extends PlayerEvent

当玩家数据刚刚结束从文件中加载时触发。注意这个时候玩家还没有被载入世界，该事件存在的意义是允许Mod加载更多的玩家相关信息。`playerDirectory`常量表示加载玩家数据的文件夹，`playerUUID`常量表示玩家的UUID。

常量：

* `public final File playerDirectory;`
* `public final String playerUUID;`

方法：

* `public File getPlayerFile(String suffix)` <br> 新建或读取一个Forge推荐的文件位置并返回该文件

#### [E] PlayerEvent.NameFormat extends PlayerEvent

当玩家的显示名称被检索到时触发。该事件存在的意义是允许Mod更改玩家的显示名称。

常量：

* `public final String username;`

变量：

* `public String displayname;`

#### [E] PlayerDestroyItemEvent extends PlayerEvent

当玩家即将损毁物品（如工具使用损坏、矿物激活信标等等）时触发。`original`常量表示损毁前的物品。

常量：

* `public final ItemStack original;`

#### [E] PlayerFlyableFallEvent extends PlayerEvent

当玩家在可飞行状态下落时触发。该事件仅仅用于提示等功能，请不要尝试取消。

变量：

* `public float distance;`
* `public float multipler;`

#### [EC] PlayerInteractEvent extends PlayerEvent

当玩家试图通过右键空气、右键方块、和左键方块的方法尝试与世界交流时触发。取消该事件会阻止这一行为。

常量：

* `public final Action action;`
* `public final World world;`
* `public final BlockPos pos;`
* `public final EnumFacing face;`

变量：

* `public Result useBlock = DEFAULT;`
* `public Result useItem = DEFAULT;`

#### [ER] PlayerOpenContainerEvent extends PlayerEvent

当玩家试图与容器方块交流时触发。通过设置`Result`为`ALLOW`可以使该容器方块保持开启状态，通过设置`Result`为`DENY`可以使该容器强制关闭。

常量：

* `public final boolean canInteractWith;`

#### [EC] PlayerPickupXpEvent extends PlayerEvent

当玩家试图收集经验球时触发。取消该事件会阻止玩家收集经验球。

常量：

* `public final EntityXPOrb orb;`

#### [E] PlayerSleepInBedEvent extends PlayerEvent

当玩家在床上进入梦乡时触发。`pos`常量表示事件发生的位置，而`result`变量表示结果，可以设置成`OK`、`NOT_POSSIBLE_HERE`、`NOT_POSSIBLE_NOW`、`TOO_FAR_AWAY`、`OTHER_PROBLEM`、和`NOT_SAFE`六种。

常量：

* `public final BlockPos pos;`

变量：

* `public EnumStatus result;`

#### [E] PlayerUseItemEvent extends PlayerEvent

当包含与玩家使用物品相关的操作执行时触发。`item`常量表示玩家使用的物品，而`duration`变量表示玩家使用物品的持续时间。

常量：

* `public final ItemStack item;`

变量：

* `public int duration;`

#### [E] PlayerUseItemEvent.Finish extends PlayerUseItemEvent

当玩家刚刚结束使用物品时触发。`result`变量表示使用后的物品。

变量：

* `public ItemStack result;`

#### [EC] PlayerUseItemEvent.Start extends PlayerUseItemEvent

当玩家即将开始使用物品时触发。取消该事件可以阻止这一行为，将`duration`变量设置成0或负数也可以起到相同的效果。

#### [EC] PlayerUseItemEvent.Stop extends PlayerUseItemEvent

当玩家即将中断使用物品（如食物食用到一半终止）时触发。取消该事件可以使被使用的物品不会被影响，将`duration`变量设置成0或负数也可以起到相同的效果，原版的唯一作用是使弓不会射出箭。

#### [EC] PlayerUseItemEvent.Tick extends PlayerUseItemEvent

当一个Game Tick检测到玩家正在使用物品时触发。取消该事件会阻止玩家使用物品，将`duration`变量设置成0或负数也可以起到相同的效果。

#### [E] PlayerWakeUpEvent extends PlayerEvent

当玩家醒来时触发。

常量：

* `public final boolean wakeImmediatly;`
* `public final boolean updateWorld;`
* `public final boolean setSpawn;`

#### [E] RenderPlayerEvent extends PlayerEvent

当玩家被绘制时触发。

常量：

* `public final RenderPlayer renderer;`
* `public final float partialRenderTick;`
* `public final double x;`
* `public final double y;`
* `public final double z;`

#### [E] PlayerEvent.SaveToFile extends PlayerEvent

当玩家数据刚刚结束保存到文件中时触发。注意这个时候玩家已经不再被加载于世界中，该事件存在的意义是允许Mod加载更多的玩家相关信息。`playerDirectory`常量表示加载玩家数据的文件夹，`playerUUID`常量表示玩家的UUID。

常量：

* `public final File playerDirectory;`
* `public final String playerUUID;`

方法：

* `public File getPlayerFile(String suffix)` <br> 新建或读取一个Forge推荐的文件位置并返回该文件

#### [E] PlayerEvent.StartTracking extends PlayerEvent

当玩家开始追踪一个实体（即玩家获取到了实体的更新事件如运动）时触发。

常量：

* `public final Entity target;`

#### [E] PlayerEvent.StopTracking extends PlayerEvent

当玩家停止追踪一个实体（即玩家不再获取实体的更新事件如运动）时触发。

常量：

* `public final Entity target;`

#### [ECR] UseHoeEvent extends PlayerEvent

当玩家使用锄子点击方块时触发。取消该事件会阻止这一行为，通过设置`Result`为`ALLOW`会使得方块不会发生变化，而锄子会被使用一次（如带来耐久的减少）。

常量：

* `public final ItemStack current;`
* `public final World world;`
* `public final BlockPos pos;`

#### [E] MinecartEvent extends EntityEvent

当与矿车相关的操作被执行时触发。`minecart`常量表示该矿车。

常量：

* `public final EntityMinecart minecart;`

#### [E] MinecartCollisionEvent extends MinecartEvent

当矿车撞击实体时触发。`collider`常量表示被撞击的实体。

常量：

* `public final Entity collider;`

#### [EC] MinecartInteractEvent extends MinecartEvent

当玩家和矿车交互时触发。取消该事件会阻止这一行为。`player`常量表示与矿车交互的玩家。

常量：

* `public final EntityPlayer player;`

#### [E] MinecartUpdateEvent extends MinecartEvent

当矿车更新时触发。`pos`常量表示矿车的位置。

常量：

* `public final BlockPos pos;`

#### [EC] PlaySoundAtEntityEvent extends EntityEvent

当实体即将发出一个声音时触发。取消该事件会阻止实体发出声音。

常量：

* `public final float volume;`
* `public final float pitch;`

变量：

* `public String name;`
* `public float newVolume;`
* `public float newPitch;`

#### [E] ZombieEvent extends EntityEvent

当僵尸被激怒时即将生成其他僵尸时触发。

方法：

* `public EntityZombie getSummoner()` <br> 获取被激怒的僵尸

#### [ER] ZombieEvent.SummonAidEvent extends ZombieEvent

当僵尸即将因为其他僵尸的怒气生成时触发。

常量：

* `public final World world;`
* `public final int x;`
* `public final int y;`
* `public final int z;`
* `public final EntityLivingBase attacker;`
* `public final double summonChance;`

变量：

* `public EntityZombie customSummonedAid;`

#### [E] EntityViewRenderEvent

当与`EntityRenderer`类绘制相关的操作执行时触发。该事件允许开发者自定义玩家所见的迷雾（如基岩附近、地平线附近、水中等）。

常量：

* `public final EntityRenderer renderer;`
* `public final Entity entity;`
* `public final Block block;`
* `public final double renderPartialTicks;`

#### [E] EntityViewRenderEvent.FogColors extends EntityViewRenderEvent

当`EntityRenderer`类试图获取迷雾的颜色时触发。开发者可以通过更改RGB值来更改迷雾的颜色。

变量：

* `public float red;`
* `public float green;`
* `public float blue;`

#### [EC] EntityViewRenderEvent.FogDensity extends EntityViewRenderEvent

当`EntityRenderer`类试图获取迷雾的浓度时触发。取消该事件以起到效果，开发者如果想起到效果必须取消事件。

变量：

* `public float density;`

#### [ER] EntityViewRenderEvent.RenderFogEvent extends EntityViewRenderEvent

当`EntityRenderer`类试图绘制迷雾时触发。通过设置`Result`为任何值都不会有效果（作者严重怀疑这是一个bug）。

常量：

* `public final int fogMode;`
* `public final float farPlaneDistance;`

#### [E] ExplosionEvent

当世界发生爆炸时触发。

常量：

* `public final World world;`
* `public final Explosion explosion;`

#### [E] ExplosionEvent.Detonate extends ExplosionEvent

当世界发生的爆炸影响到方块和实体时触发。可以通过修改方块和实体的列表以对结果产生影响。

方法：

* `public List<BlockPos> getAffectedBlocks()` <br> 获取被影响的方块列表
* `public List<Entity> getAffectedEntities()` <br> 获取被影响的实体列表

#### [EC] ExplosionEvent.Start extends ExplosionEvent

当爆炸即将发生时触发。取消该事件会阻止爆炸的发生。

#### [E] FluidContainerRegistry.FluidContainerRegisterEvent

当注册`FluidContainer`即将完成时触发。

常量：

* `public final FluidContainerData data;`

#### [E] FluidEvent

当所有和流体行为相关的操作执行时触发。

常量：

* `public final FluidStack fluid;`
* `public final World world;`
* `public final BlockPos pos;`

#### [E] FluidEvent.FluidDrainingEvent extends FluidEvent

当流体从一个`FluidTank`流出时触发。

常量：

* `public final IFluidTank tank;`
* `public final int amount;`

#### [E] FluidEvent.FluidFillingEvent extends FluidEvent

当流体被装到一个`FluidTank`时触发。

常量：

* `public final IFluidTank tank;`
* `public final int amount;`

#### [E] FluidEvent.FluidMotionEvent extends FluidEvent

当流体流动时触发。

#### [E] FluidEvent.FluidSpilledEvent extends FluidEvent

当流体溢出时触发。

#### [E] FluidRegistry.FluidRegisterEvent

当流体被注册时触发。

常量：

* `public final String fluidName;`
* `public final int fluidID;`

#### [FN] FMLNetworkEvent &lt;T extends INetHandler&gt;

当所有和FML相关的网络事件执行时触发。

常量：

* `public final T handler;`
* `public final NetworkManager manager;`

#### [F] FMLNetworkEvent.ClientConnectedToServerEvent extends FMLNetworkEvent &lt;INetHandlerPlayClient&gt;

当FML客户端连接到服务端时触发。

常量：

* `public final boolean isLocal;`
* `public final String connectionType;`

#### [F] FMLNetworkEvent.ClientDisconnectionFromServerEvent extends FMLNetworkEvent &lt;INetHandlerPlayClient&gt;

当FML客户端和服务端断开时触发。

#### [N] FMLNetworkEvent.CustomPacketEvent &lt;T extends INetHandler&gt; extends FMLNetworkEvent &lt;T&gt;

当所有和自定义数据包相关的操作执行时触发。`packet`常量表示收到的数据包，而`reply`变量表示返回的数据包。

常量：

* `public final FMLProxyPacket packet;`

变量：

* `public FMLProxyPacket reply;`

方法：

* `public Side side();` <br> 所处的客户端

#### [N] FMLNetworkEvent.ClientCustomPacketEvent extends FMLNetworkEvent.CustomPacketEvent &lt;INetHandlerPlayClient&gt;

当客户端收到自定义数据包时触发。

#### [N] FMLNetworkEvent.ServerCustomPacketEvent extends FMLNetworkEvent.CustomPacketEvent &lt;INetHandlerPlayServer&gt;

当服务端收到自定义数据包时触发。

#### [F] FMLNetworkEvent.CustomPacketRegistrationEvent &lt;T extends INetHandler&gt; extends FMLNetworkEvent &lt;T&gt;

当自定义包被注册或注销时触发。

常量：

* `public final ImmutableSet<String> registrations;`
* `public final String operation;`
* `public final Side side;`

#### [F] FMLNetworkEvent.ServerConnectionFromClientEvent extends FMLNetworkEvent &lt;INetHandlerPlayServer&gt;

当FML服务端连接到客户端时触发。

常量：

* `public final boolean isLocal;`

#### [F] FMLNetworkEvent.ServerDisconnectionFromClientEvent extends FMLNetworkEvent &lt;INetHandlerPlayServer&gt;

当FML服务端和客户端断开时触发。

#### [E] ForgeChunkManager.ForceChunkEvent

当区块被Mod强制加载时触发。常量`ticket`表示该Mod请求的Ticket，而常量`location`表示区块的坐标。

常量：

* `public final Ticket ticket;`
* `public final ChunkCoordIntPair location;`

#### [E] FOVUpdateEvent

当游戏的FOV变化时触发。常量`entity`表示变化FOV的玩家，常量`fov`表示旧的FOV，变量`newfov`表示新的FOV。

常量：

* `public final EntityPlayer entity;`
* `public final float fov;`
* `public float newfov;`

#### [EC] GuiOpenEvent

当一个GUI即将被打开时触发。取消该事件会使得GUI不会被打开。变量`gui`表示被操作的GUI

变量：

* `public GuiScreen gui;`

#### [E] GuiScreenEvent

当一个和`GuiScreen`类或其子类相关的操作执行时触发。常量`gui`表示被操作的GUI

常量：

* `public final GuiScreen gui;`

#### [E] GuiScreenEvent.ActionPerformedEvent extends GuiScreenEvent

当一个和`GuiScreen`类或其子类的按钮被点击时触发。变量`button`表示被点击的按钮，而变量`buttonList`表示这个`GuiScreen`类提供的按钮列表。

变量：

* `public GuiButton button;`
* `public List buttonList;`

#### [E] GuiScreenEvent.ActionPerformedEvent.Post extends GuiScreenEvent.ActionPerformedEvent

当一个和`GuiScreen`类或其子类的按钮即将点击完成时触发。

#### [EC] GuiScreenEvent.ActionPerformedEvent.Pre extends GuiScreenEvent.ActionPerformedEvent

当一个和`GuiScreen`类或其子类的按钮即将被点击时触发。取消该事件会阻止这一行为。

#### [E] GuiScreenEvent.DrawScreenEvent extends GuiScreenEvent

当一个和`GuiScreen`类或其子类的图形绘制时触发。常量`mouseX`表示绘制时鼠标的横坐标，常量`mouseY`表示绘制时鼠标的纵坐标，常量`renderPartialTicks`表示绘制一个GUI所花时间，为gametick的倍数。

常量：

* `public final int mouseX;`
* `public final int mouseY;`
* `public final float renderPartialTicks;`

#### [E] GuiScreenEvent.DrawScreenEvent.Post extends GuiScreenEvent.DrawScreenEvent

当一个和`GuiScreen`类或其子类即将绘制完成时触发。

#### [EC] GuiScreenEvent.DrawScreenEvent.Pre extends GuiScreenEvent.DrawScreenEvent

当一个和`GuiScreen`类或其子类即将绘制时触发。取消该事件会阻止这一行为。

#### [E] GuiScreenEvent.InitGuiEvent extends GuiScreenEvent

当一个和`GuiScreen`类或其子类初始化GUI时触发。变量`buttonList`表示这个`GuiScreen`类提供的按钮列表。

变量：

* `public List buttonList;`

#### [E] GuiScreenEvent.InitGuiEvent.Post extends GuiScreenEvent.InitGuiEvent

当一个和`GuiScreen`类或其子类即将初始化GUI完成时触发。

#### [EC] GuiScreenEvent.InitGuiEvent.Pre extends GuiScreenEvent.InitGuiEvent

当一个和`GuiScreen`类或其子类即将初始化GUI时触发。取消该事件会阻止这一行为。

#### [E] GuiScreenEvent.KeyboardInputEvent extends GuiScreenEvent

当一个和`GuiScreen`类或其子类检测到键盘操作时触发。

#### [E] GuiScreenEvent.KeyboardInputEvent.Post extends GuiScreenEvent.KeyboardInputEvent

当一个和`GuiScreen`类或其子类即将应用键盘操作完成时触发。

#### [EC] GuiScreenEvent.KeyboardInputEvent.Pre extends GuiScreenEvent.KeyboardInputEvent

当一个和`GuiScreen`类或其子类即将应用键盘操作时触发。取消该事件会阻止这一行为。

#### [E] GuiScreenEvent.MouseInputEvent extends GuiScreenEvent

当一个和`GuiScreen`类或其子类检测到鼠标操作时触发。

#### [E] GuiScreenEvent.MouseInputEvent.Post extends GuiScreenEvent.MouseInputEvent

当一个和`GuiScreen`类或其子类即将应用鼠标操作完成时触发。

#### [EC] GuiScreenEvent.MouseInputEvent.Pre extends GuiScreenEvent.MouseInputEvent

当一个和`GuiScreen`类或其子类即将应用鼠标操作时触发。取消该事件会阻止这一行为。

#### [T] InitMapGenEvent

当一个世界的一种类型的地形生成即将完成时触发。可用的地形包括洞穴、村庄等。

常量：

* `public final EventType type;`
* `public final MapGenBase originalGen;`

变量：

* `public MapGenBase newGen;`

#### [F] InputEvent

当输入相关的操作执行时触发。

#### [F] InputEvent.MouseInputEvent extends InputEvent

当鼠标输入相关的操作执行时触发。

#### [F] InputEvent.KeyInputEvent extends InputEvent

当键盘输入相关的操作执行时触发。

#### [E] ModelBakeEvent

当一个模型即将被加载完成时触发。

常量：

* `public final ModelManager modelManager;`
* `public final IRegistry modelRegistry;`
* `@Deprecated` <br> `public final ModelBakery modelBakery;`
* `public final ModelLoader modelLoader;`

#### [EC] MouseEvent

当游戏探测到鼠标移动时触发。取消该事件会阻止这一行为。常量`x`表示鼠标的横坐标，常量`y`表示鼠标的纵坐标，常量`dx`表示鼠标移动的横坐标，常量`dy`表示鼠标移动的纵坐标，常量`dwheel`表示鼠标滚轮移动坐标，常量`button`表示鼠标按键，常量`buttonState`表示鼠标按键状态，常量`nanoseconds`表示事件发生时间，以毫秒计。

常量：

* `public final int x;`
* `public final int y;`
* `public final int dx;`
* `public final int dy;`
* `public final int dwheel;`
* `public final int button;`
* `public final boolean buttonstate;`
* `public final long nanoseconds;`

#### [O] OreGenEvent

当所有和原版世界生成相关的操作执行时触发。常量`world`表示生成矿物的世界，常量`rand`表示生成时所使用的随机数生成器，常量`pos`表示生成矿物所在区块对应的方块坐标。

常量：

* `public final World world;`
* `public final Random rand;`
* `public final BlockPos pos;`

#### [OR] OreGenEvent.GenerateMinable extends OreGenEvent

当特定世界生成时触发。通过设置`Result`为`DENY`可以取消该生成。常量`type`表示生成的方块种类，而常量`generator`表示对应的世界生成器。

常量：

* `public final EventType type;`
* `public final WorldGenerator generator;`

#### [O] OreGenEvent.Post extends OreGenEvent

当世界生成即将完成时触发。

#### [O] OreGenEvent.pre extends OreGenEvent

当世界生成即将开始时触发。

#### [E] OreDictionary.OreRegisterEvent

当一个矿物字典即将被注册时触发。

常量：

* `public final String Name;`
* `public final ItemStack Ore;`

#### [F] PlayerEvent

当所有在FML的事件系统中注册的玩家操作相关的操作执行时触发。只不过该类的所有子类所代表的事件都应该在`FMLCommonHandler.getInstance().bus()`中注册。

常量：

* `public final EntityPlayer player;`

#### [F] PlayerEvent.ItemCraftedEvent extends PlayerEvent

当玩家即将完成工作台的合成时触发。常量`crafting`表示最终成品，常量`craftMatrix`表示合成表。

常量：

* `public final ItemStack crafting;`
* `public final IInventory craftMatrix;`

#### [F] PlayerEvent.ItemPickupEvent extends PlayerEvent

当玩家即将捡起物品时触发。常量`pickedUp`表示被捡起的物品。

常量：

* `public final EntityItem pickedUp;`

#### [F] PlayerEvent.ItemSmeltedEvent extends PlayerEvent

当玩家即将完成从熔炉中取出被烧炼的物品时触发。常量`smelting`表示被烧炼的物品。

常量：

* `public final ItemStack smelting;`

#### [F] PlayerEvent.PlayerChangedDimensionEvent extends PlayerEvent

当玩家即将被传送到另一个维度世界时触发。常量`fromDim`表示传送前维度id，常量`toDim`表示传送后维度id。

常量：

* `public final int fromDim;`
* `public final int toDim;`

#### [F] PlayerEvent.PlayerLoggedInEvent extends PlayerEvent

当玩家登入时触发。

#### [F] PlayerEvent.PlayerLoggeOutEvent extends PlayerEvent

当玩家登出时触发。

#### [F] PlayerEvent.PlayerRespawnEvent extends PlayerEvent

当玩家重生时触发。

#### [E] PotionBrewEvent

当所有和药水酿造相关的操作执行时触发。常量`stacks`表示当前操作的酿造台中的药水，包括未酿造的药水和和已酿造的药水。

#### `@Deprecated` <br> [E] PotionBrewedEvent extends PotionBrewEvent

当酿造操作即将完成时触发。变量`brewingStacks`表示当前操作的酿造台中的药水。

变量：

* `@Deprecated` <br> `public ItemStack[] brewingStacks;`

#### [E] PotionBrewEvent.Post extends PotionBrewedEvent

当酿造操作即将完成时触发。作用和`PotionBrewedEvent`相同，不过前者已被废弃。

#### [E] PotionBrewEvent.Post extends PotionBrewEvent

当酿造操作即将进行时触发。取消该事件会阻止这一行为。

#### [EC] RenderBlockOverlayEvent

当一个方块的贴图即将以HUD的方式（包括玩家在水中的HUD、玩家着火时的HUD、玩家窒息时的方块HUD等）渲染时触发。取消这一事件会阻止原版的渲染行为。

常量：

* `public final EntityPlayer player;`
* `public final float renderPartialTicks;`
* `public final OverlayType overlayType;`
* `public final IBlockState blockForOverlay;`
* `public final BlockPos blockPos;`

#### [EC] RenderGameOverlayEvent

当一种类型的HUD渲染相关的操作执行时触发。取消这一事件有可能会阻止原版的渲染行为。常量`type`使用枚举表示丰富的渲染类型。

常量：

* `public final float partialTicks;`
* `public final ScaledResolution resolution;`
* `public final ElementType type;`

#### [EC] RenderGameOverlayEvent.Post extends RenderGameOverlayEvents

当一种类型的HUD渲染即将完成时触发。注意该事件不支持取消，如果强行取消会出错。

#### [EC] RenderGameOverlayEvent.Pre extends RenderGameOverlayEvents

当一种类型的HUD渲染即将开始时触发。取消这一事件会阻止原版的渲染行为。

#### [EC] RenderGameOverlayEvent.Text extends RenderGameOverlayEvents.Pre

当一种类型的文字HUD渲染即将开始时触发。由于这一事件还未见在原版中触发过，故不明其用处。

常量：

* `public final ArrayList<String> left;`
* `public final ArrayList<String> right;`

#### [EC] RenderGameOverlayEvent.Chat extends RenderGameOverlayEvents.Pre

当一种类型的聊天HUD渲染即将开始时触发。由于这一事件还未见在原版中触发过，故不明其用处。

常量：

* `public int posX;`
* `public int posY;`

#### [EC] RenderHandEvent

当玩家从屏幕上伸出的肉色手臂即将被渲染时触发。取消这一事件会阻止原版的渲染行为。

常量：

* `public final RenderGlobal context;`
* `public final float partialTicks;`
* `public final int renderPass;`

#### [EC] RenderItemInFrameEvent

当一个物品展示框中的物品即将被渲染时触发。取消这一事件会阻止原版的渲染行为。常量`item`表示该物品，常量`entityItemFrame`表示物品展示框代表的实体，常量`renderer`表示渲染物品展示框的渲染器。

常量：

* `public final ItemStack item;`
* `public final EntityItemFrame entityItemFrame;`
* `public final RenderItemFrame renderer;`

#### [E] RenderLivingEvent

当所有和渲染实体生物相关的操作执行时触发。常量`entity`表示被渲染的实体，常量`renderer`表示对应的实体渲染器。

常量：

* `public final EntityLivingBase entity;`
* `public final RendererLivingEntity renderer;`
* `public final double x;`
* `public final double y;`
* `public final double z;`

#### [E] RenderLivingEvent.post extends RenderLivingEvent

当渲染实体生物相关的操作即将执行完成时触发。

#### [EC] RenderLivingEvent.pre extends RenderLivingEvent

当渲染实体生物相关的操作即将执行时触发。取消这一事件会阻止原版的渲染行为。

#### [E] RenderLivingEvent.Specials.post extends RenderLivingEvent

当所有和渲染实体生物的特殊部分（比如名称）相关的操作执行时触发。

#### [E] RenderLivingEvent.Specials.post extends RenderLivingEvent.Special

当渲染实体生物的特殊部分（比如名称）相关的操作即将执行完成时触发。

#### [EC] RenderLivingEvent.Specials.pre extends RenderLivingEvent.Special

当渲染实体生物的特殊部分（比如名称）相关的操作即将执行时触发。取消这一事件会阻止原版的渲染行为。

#### [E] RenderWorldEvent

当所有和渲染世界相关的操作执行时触发。暂时未找到该事件触发的条件，故监听该事件可能暂时无意义。

常量：

* `public final WorldRenderer renderer;`
* `public final ChunkCache chunkCache;`
* `public final int pass;`

#### [E] RenderWorldEvent.Post extends RenderWorldEvent

当渲染世界相关的操作即将执行完成时触发。暂时未找到该事件触发的条件，故监听该事件可能暂时无意义。

#### [E] RenderWorldEvent.Pre extends RenderWorldEvent

当渲染世界相关的操作即将执行时触发。暂时未找到该事件触发的条件，故监听该事件可能暂时无意义。

#### [E] RenderWorldLastEvent

当整个世界的渲染即将结束时触发。

常量：

* `public final RenderGlobal context;`
* `public final float partialTicks;`

## 未完待续。。。
