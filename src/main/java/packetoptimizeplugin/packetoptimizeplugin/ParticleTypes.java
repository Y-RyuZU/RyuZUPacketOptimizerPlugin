package packetoptimizeplugin.packetoptimizeplugin;

public class ParticleTypes {
    public enum ParticleType {
        AMBIENT_ENTITY_EFFECT(0),
        SPELL_MOB_AMBIENT(0),
        ANGRY_VILLAGER(1),
        VILLAGER_ANGRY(1),
        BARRIER(2),
        BLOCK_CRACK(3),
        BUBBLE(4),
        WATER_BUBBLE(4),
        CLOUD(5),
        CRIT(6),
        DAMAGE_INDICATOR(7),
        DRAGON_BREATH(8),
        DRIPPING_LAVA(9),
        DRIP_LAVA(9),
        FALLING_LAVA(10),
        LANDING_LAVA(11),
        DRIPPING_WATER(12),
        DRIP_WATER(12),
        FALLING_WATER(13),
        REDSTONE(14),
        EFFECT(15),
        SPELL(15),
        ELDER_GUARDIAN(16),
        ENCHANTED_HIT(17),
        CRIT_MAGIC(17),
        ENCHANT(18),
        END_ROD(19),
        ENTITY_EFFECT(20),
        SPELL_MOB(20),
        EXPLOSION_EMITTER(21),
        EXPLOSION_HUGE(21),
        EXPLOSION(22),
        EXPLOSION_LARGE(22),
        FALLING_DUST(23),
        FIREWORK(24),
        FIREWORKS_SPARK(24),
        RAIN(25),
        WATER_WAKE(25),
        FLAME(26),
        FIRE(26),
        SOUL_FIRE_FLAME(27),
        SOUL_FIRE(27),
        SOUL(28),
        FLASH(29),
        HAPPY_VILLAGER(30),
        VILLAGER_HAPPY(30),
        COMPOSTER(31),
        HEART(32),
        INSTANT_EFFECT(33),
        SPELL_INSTANT(33),
        ITEM_CRACK(34),
        ITEM_SLIME(35),
        SLIME(35),
        ITEM_SNOWBALL(36),
        SNOWBALL(36),
        LARGE_SMOKE(37),
        SMOKE_LARGE(37),
        LAVA(38),
        MYCELIUM(39),
        NOTE(40),
        POOF(41),
        EXPLOSION_NORMAL(41),
        PORTAL(42),
        FISHING(43),
        WATER_DROP(43),
        SMOKE(44),
        SMOKE_NORMAL(44),
        SNEEZE(45),
        SPIT(46),
        SQUID_INK(47),
        SWEEP_ATTACK(48),
        TOTEM_OF_UNDYING(49),
        TOTEM(49),
        UNDERWATER(50),
        SPLASH(51),
        WATER_SPLASH(51),
        WITCH(52),
        SPELL_WITCH(52),
        BUBBLE_POP(53),
        CURRENT_DOWN(54),
        BUBBLE_COLUMN_UP(55),
        NAUTILUS(56),
        DOLPHIN(57),
        CAMPFIRE_COSY_SMOKE(58),
        CAMPFIRE_SIGNAL_SMOKE(59),
        DRIPPING_HONEY(60),
        FALLING_HONEY(61),
        LANDING_HONEY(62),
        FALLING_NECTAR(63),
        ASH(64),
        CRIMSON_SPORE(65),
        WARPED_SPORE(66),
        DRIPPING_OBSIDIAN_TEAR(67),
        FALLING_OBSIDIAN_TEAR(68),
        LANDING_OBSIDIAN_TEAR(69),
        REVERSE_PORTAL(70),
        WHITE_ASH(71);;

        private final int id;

        ParticleType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public enum MaterialTypes {
        AIR(0),
        STONE(1),
        GRANITE(2),
        POLISHED_GRANITE(3),
        DIORITE(4),
        POLISHED_DIORITE(5),
        ANDESITE(6),
        POLISHED_ANDESITE(7),
        GRASS_BLOCK(8),
        DIRT(9),
        COARSE_DIRT(10),
        PODZOL(11),
        CRIMSON_NYLIUM(12),
        WARPED_NYLIUM(13),
        COBBLESTONE(14),
        OAK_PLANKS(15),
        SPRUCE_PLANKS(16),
        BIRCH_PLANKS(17),
        JUNGLE_PLANKS(18),
        ACACIA_PLANKS(19),
        DARK_OAK_PLANKS(20),
        CRIMSON_PLANKS(21),
        WARPED_PLANKS(22),
        OAK_SAPLING(23),
        SPRUCE_SAPLING(24),
        BIRCH_SAPLING(25),
        JUNGLE_SAPLING(26),
        ACACIA_SAPLING(27),
        DARK_OAK_SAPLING(28),
        BEDROCK(29),
        SAND(30),
        RED_SAND(31),
        GRAVEL(32),
        GOLD_ORE(33),
        IRON_ORE(34),
        COAL_ORE(35),
        NETHER_GOLD_ORE(36),
        OAK_LOG(37),
        SPRUCE_LOG(38),
        BIRCH_LOG(39),
        JUNGLE_LOG(40),
        ACACIA_LOG(41),
        DARK_OAK_LOG(42),
        CRIMSON_STEM(43),
        WARPED_STEM(44),
        STRIPPED_OAK_LOG(45),
        STRIPPED_SPRUCE_LOG(46),
        STRIPPED_BIRCH_LOG(47),
        STRIPPED_JUNGLE_LOG(48),
        STRIPPED_ACACIA_LOG(49),
        STRIPPED_DARK_OAK_LOG(50),
        STRIPPED_CRIMSON_STEM(51),
        STRIPPED_WARPED_STEM(52),
        STRIPPED_OAK_WOOD(53),
        STRIPPED_SPRUCE_WOOD(54),
        STRIPPED_BIRCH_WOOD(55),
        STRIPPED_JUNGLE_WOOD(56),
        STRIPPED_ACACIA_WOOD(57),
        STRIPPED_DARK_OAK_WOOD(58),
        STRIPPED_CRIMSON_HYPHAE(59),
        STRIPPED_WARPED_HYPHAE(60),
        OAK_WOOD(61),
        SPRUCE_WOOD(62),
        BIRCH_WOOD(63),
        JUNGLE_WOOD(64),
        ACACIA_WOOD(65),
        DARK_OAK_WOOD(66),
        CRIMSON_HYPHAE(67),
        WARPED_HYPHAE(68),
        OAK_LEAVES(69),
        SPRUCE_LEAVES(70),
        BIRCH_LEAVES(71),
        JUNGLE_LEAVES(72),
        ACACIA_LEAVES(73),
        DARK_OAK_LEAVES(74),
        SPONGE(75),
        WET_SPONGE(76),
        GLASS(77),
        LAPIS_ORE(78),
        LAPIS_BLOCK(79),
        DISPENSER(80),
        SANDSTONE(81),
        CHISELED_SANDSTONE(82),
        CUT_SANDSTONE(83),
        NOTE_BLOCK(84),
        POWERED_RAIL(85),
        DETECTOR_RAIL(86),
        STICKY_PISTON(87),
        COBWEB(88),
        GRASS(89),
        FERN(90),
        DEAD_BUSH(91),
        SEAGRASS(92),
        SEA_PICKLE(93),
        PISTON(94),
        WHITE_WOOL(95),
        ORANGE_WOOL(96),
        MAGENTA_WOOL(97),
        LIGHT_BLUE_WOOL(98),
        YELLOW_WOOL(99),
        LIME_WOOL(100),
        PINK_WOOL(101),
        GRAY_WOOL(102),
        LIGHT_GRAY_WOOL(103),
        CYAN_WOOL(104),
        PURPLE_WOOL(105),
        BLUE_WOOL(106),
        BROWN_WOOL(107),
        GREEN_WOOL(108),
        RED_WOOL(109),
        BLACK_WOOL(110),
        DANDELION(111),
        POPPY(112),
        BLUE_ORCHID(113),
        ALLIUM(114),
        AZURE_BLUET(115),
        RED_TULIP(116),
        ORANGE_TULIP(117),
        WHITE_TULIP(118),
        PINK_TULIP(119),
        OXEYE_DAISY(120),
        CORNFLOWER(121),
        LILY_OF_THE_VALLEY(122),
        WITHER_ROSE(123),
        BROWN_MUSHROOM(124),
        RED_MUSHROOM(125),
        CRIMSON_FUNGUS(126),
        WARPED_FUNGUS(127),
        CRIMSON_ROOTS(128),
        WARPED_ROOTS(129),
        NETHER_SPROUTS(130),
        WEEPING_VINES(131),
        TWISTING_VINES(132),
        SUGAR_CANE(133),
        KELP(134),
        BAMBOO(135),
        GOLD_BLOCK(136),
        IRON_BLOCK(137),
        OAK_SLAB(138),
        SPRUCE_SLAB(139),
        BIRCH_SLAB(140),
        JUNGLE_SLAB(141),
        ACACIA_SLAB(142),
        DARK_OAK_SLAB(143),
        CRIMSON_SLAB(144),
        WARPED_SLAB(145),
        STONE_SLAB(146),
        SMOOTH_STONE_SLAB(147),
        SANDSTONE_SLAB(148),
        CUT_SANDSTONE_SLAB(149),
        PETRIFIED_OAK_SLAB(150),
        COBBLESTONE_SLAB(151),
        BRICK_SLAB(152),
        STONE_BRICK_SLAB(153),
        NETHER_BRICK_SLAB(154),
        QUARTZ_SLAB(155),
        RED_SANDSTONE_SLAB(156),
        CUT_RED_SANDSTONE_SLAB(157),
        PURPUR_SLAB(158),
        PRISMARINE_SLAB(159),
        PRISMARINE_BRICK_SLAB(160),
        DARK_PRISMARINE_SLAB(161),
        SMOOTH_QUARTZ(162),
        SMOOTH_RED_SANDSTONE(163),
        SMOOTH_SANDSTONE(164),
        SMOOTH_STONE(165),
        BRICKS(166),
        TNT(167),
        BOOKSHELF(168),
        MOSSY_COBBLESTONE(169),
        OBSIDIAN(170),
        TORCH(171),
        END_ROD(172),
        CHORUS_PLANT(173),
        CHORUS_FLOWER(174),
        PURPUR_BLOCK(175),
        PURPUR_PILLAR(176),
        PURPUR_STAIRS(177),
        SPAWNER(178),
        OAK_STAIRS(179),
        CHEST(180),
        DIAMOND_ORE(181),
        DIAMOND_BLOCK(182),
        CRAFTING_TABLE(183),
        FARMLAND(184),
        FURNACE(185),
        LADDER(186),
        RAIL(187),
        COBBLESTONE_STAIRS(188),
        LEVER(189),
        STONE_PRESSURE_PLATE(190),
        OAK_PRESSURE_PLATE(191),
        SPRUCE_PRESSURE_PLATE(192),
        BIRCH_PRESSURE_PLATE(193),
        JUNGLE_PRESSURE_PLATE(194),
        ACACIA_PRESSURE_PLATE(195),
        DARK_OAK_PRESSURE_PLATE(196),
        CRIMSON_PRESSURE_PLATE(197),
        WARPED_PRESSURE_PLATE(198),
        POLISHED_BLACKSTONE_PRESSURE_PLATE(199),
        REDSTONE_ORE(200),
        REDSTONE_TORCH(201),
        SNOW(202),
        ICE(203),
        SNOW_BLOCK(204),
        CACTUS(205),
        CLAY(206),
        JUKEBOX(207),
        OAK_FENCE(208),
        SPRUCE_FENCE(209),
        BIRCH_FENCE(210),
        JUNGLE_FENCE(211),
        ACACIA_FENCE(212),
        DARK_OAK_FENCE(213),
        CRIMSON_FENCE(214),
        WARPED_FENCE(215),
        PUMPKIN(216),
        CARVED_PUMPKIN(217),
        NETHERRACK(218),
        SOUL_SAND(219),
        SOUL_SOIL(220),
        BASALT(221),
        POLISHED_BASALT(222),
        SOUL_TORCH(223),
        GLOWSTONE(224),
        JACK_O_LANTERN(225),
        OAK_TRAPDOOR(226),
        SPRUCE_TRAPDOOR(227),
        BIRCH_TRAPDOOR(228),
        JUNGLE_TRAPDOOR(229),
        ACACIA_TRAPDOOR(230),
        DARK_OAK_TRAPDOOR(231),
        CRIMSON_TRAPDOOR(232),
        WARPED_TRAPDOOR(233),
        INFESTED_STONE(234),
        INFESTED_COBBLESTONE(235),
        INFESTED_STONE_BRICKS(236),
        INFESTED_MOSSY_STONE_BRICKS(237),
        INFESTED_CRACKED_STONE_BRICKS(238),
        INFESTED_CHISELED_STONE_BRICKS(239),
        STONE_BRICKS(240),
        MOSSY_STONE_BRICKS(241),
        CRACKED_STONE_BRICKS(242),
        CHISELED_STONE_BRICKS(243),
        BROWN_MUSHROOM_BLOCK(244),
        RED_MUSHROOM_BLOCK(245),
        MUSHROOM_STEM(246),
        IRON_BARS(247),
        CHAIN(248),
        GLASS_PANE(249),
        MELON(250),
        VINE(251),
        OAK_FENCE_GATE(252),
        SPRUCE_FENCE_GATE(253),
        BIRCH_FENCE_GATE(254),
        JUNGLE_FENCE_GATE(255),
        ACACIA_FENCE_GATE(256),
        DARK_OAK_FENCE_GATE(257),
        CRIMSON_FENCE_GATE(258),
        WARPED_FENCE_GATE(259),
        BRICK_STAIRS(260),
        STONE_BRICK_STAIRS(261),
        MYCELIUM(262),
        LILY_PAD(263),
        NETHER_BRICKS(264),
        CRACKED_NETHER_BRICKS(265),
        CHISELED_NETHER_BRICKS(266),
        NETHER_BRICK_FENCE(267),
        NETHER_BRICK_STAIRS(268),
        ENCHANTING_TABLE(269),
        ENCHANTMENT_TABLE(269),
        END_PORTAL_FRAME(270),
        END_STONE(271),
        END_STONE_BRICKS(272),
        DRAGON_EGG(273),
        REDSTONE_LAMP(274),
        SANDSTONE_STAIRS(275),
        EMERALD_ORE(276),
        ENDER_CHEST(277),
        TRIPWIRE_HOOK(278),
        EMERALD_BLOCK(279),
        SPRUCE_STAIRS(280),
        BIRCH_STAIRS(281),
        JUNGLE_STAIRS(282),
        CRIMSON_STAIRS(283),
        WARPED_STAIRS(284),
        COMMAND_BLOCK(285),
        BEACON(286),
        COBBLESTONE_WALL(287),
        MOSSY_COBBLESTONE_WALL(288),
        BRICK_WALL(289),
        PRISMARINE_WALL(290),
        RED_SANDSTONE_WALL(291),
        MOSSY_STONE_BRICK_WALL(292),
        GRANITE_WALL(293),
        STONE_BRICK_WALL(294),
        NETHER_BRICK_WALL(295),
        ANDESITE_WALL(296),
        RED_NETHER_BRICK_WALL(297),
        SANDSTONE_WALL(298),
        END_STONE_BRICK_WALL(299),
        DIORITE_WALL(300),
        BLACKSTONE_WALL(301),
        POLISHED_BLACKSTONE_WALL(302),
        POLISHED_BLACKSTONE_BRICK_WALL(303),
        STONE_BUTTON(304),
        OAK_BUTTON(305),
        SPRUCE_BUTTON(306),
        BIRCH_BUTTON(307),
        JUNGLE_BUTTON(308),
        ACACIA_BUTTON(309),
        DARK_OAK_BUTTON(310),
        CRIMSON_BUTTON(311),
        WARPED_BUTTON(312),
        POLISHED_BLACKSTONE_BUTTON(313),
        ANVIL(314),
        CHIPPED_ANVIL(315),
        DAMAGED_ANVIL(316),
        TRAPPED_CHEST(317),
        LIGHT_WEIGHTED_PRESSURE_PLATE(318),
        HEAVY_WEIGHTED_PRESSURE_PLATE(319),
        DAYLIGHT_DETECTOR(320),
        REDSTONE_BLOCK(321),
        NETHER_QUARTZ_ORE(322),
        HOPPER(323),
        CHISELED_QUARTZ_BLOCK(324),
        QUARTZ_BLOCK(325),
        QUARTZ_BRICKS(326),
        QUARTZ_PILLAR(327),
        QUARTZ_STAIRS(328),
        ACTIVATOR_RAIL(329),
        DROPPER(330),
        WHITE_TERRACOTTA(331),
        ORANGE_TERRACOTTA(332),
        MAGENTA_TERRACOTTA(333),
        LIGHT_BLUE_TERRACOTTA(334),
        YELLOW_TERRACOTTA(335),
        LIME_TERRACOTTA(336),
        PINK_TERRACOTTA(337),
        GRAY_TERRACOTTA(338),
        LIGHT_GRAY_TERRACOTTA(339),
        CYAN_TERRACOTTA(340),
        PURPLE_TERRACOTTA(341),
        BLUE_TERRACOTTA(342),
        BROWN_TERRACOTTA(343),
        GREEN_TERRACOTTA(344),
        RED_TERRACOTTA(345),
        BLACK_TERRACOTTA(346),
        BARRIER(347),
        IRON_TRAPDOOR(348),
        HAY_BLOCK(349),
        WHITE_CARPET(350),
        ORANGE_CARPET(351),
        MAGENTA_CARPET(352),
        LIGHT_BLUE_CARPET(353),
        YELLOW_CARPET(354),
        LIME_CARPET(355),
        PINK_CARPET(356),
        GRAY_CARPET(357),
        LIGHT_GRAY_CARPET(358),
        CYAN_CARPET(359),
        PURPLE_CARPET(360),
        BLUE_CARPET(361),
        BROWN_CARPET(362),
        GREEN_CARPET(363),
        RED_CARPET(364),
        BLACK_CARPET(365),
        TERRACOTTA(366),
        COAL_BLOCK(367),
        PACKED_ICE(368),
        ACACIA_STAIRS(369),
        DARK_OAK_STAIRS(370),
        SLIME_BLOCK(371),
        GRASS_PATH(372),
        SUNFLOWER(373),
        LILAC(374),
        ROSE_BUSH(375),
        PEONY(376),
        TALL_GRASS(377),
        LARGE_FERN(378),
        WHITE_STAINED_GLASS(379),
        ORANGE_STAINED_GLASS(380),
        MAGENTA_STAINED_GLASS(381),
        LIGHT_BLUE_STAINED_GLASS(382),
        YELLOW_STAINED_GLASS(383),
        LIME_STAINED_GLASS(384),
        PINK_STAINED_GLASS(385),
        GRAY_STAINED_GLASS(386),
        LIGHT_GRAY_STAINED_GLASS(387),
        CYAN_STAINED_GLASS(388),
        PURPLE_STAINED_GLASS(389),
        BLUE_STAINED_GLASS(390),
        BROWN_STAINED_GLASS(391),
        GREEN_STAINED_GLASS(392),
        RED_STAINED_GLASS(393),
        BLACK_STAINED_GLASS(394),
        WHITE_STAINED_GLASS_PANE(395),
        ORANGE_STAINED_GLASS_PANE(396),
        MAGENTA_STAINED_GLASS_PANE(397),
        LIGHT_BLUE_STAINED_GLASS_PANE(398),
        YELLOW_STAINED_GLASS_PANE(399),
        LIME_STAINED_GLASS_PANE(400),
        PINK_STAINED_GLASS_PANE(401),
        GRAY_STAINED_GLASS_PANE(402),
        LIGHT_GRAY_STAINED_GLASS_PANE(403),
        CYAN_STAINED_GLASS_PANE(404),
        PURPLE_STAINED_GLASS_PANE(405),
        BLUE_STAINED_GLASS_PANE(406),
        BROWN_STAINED_GLASS_PANE(407),
        GREEN_STAINED_GLASS_PANE(408),
        RED_STAINED_GLASS_PANE(409),
        BLACK_STAINED_GLASS_PANE(410),
        PRISMARINE(411),
        PRISMARINE_BRICKS(412),
        DARK_PRISMARINE(413),
        PRISMARINE_STAIRS(414),
        PRISMARINE_BRICK_STAIRS(415),
        DARK_PRISMARINE_STAIRS(416),
        SEA_LANTERN(417),
        RED_SANDSTONE(418),
        CHISELED_RED_SANDSTONE(419),
        CUT_RED_SANDSTONE(420),
        RED_SANDSTONE_STAIRS(421),
        REPEATING_COMMAND_BLOCK(422),
        CHAIN_COMMAND_BLOCK(423),
        MAGMA_BLOCK(424),
        NETHER_WART_BLOCK(425),
        WARPED_WART_BLOCK(426),
        RED_NETHER_BRICKS(427),
        BONE_BLOCK(428),
        STRUCTURE_VOID(429),
        OBSERVER(430),
        SHULKER_BOX(431),
        WHITE_SHULKER_BOX(432),
        ORANGE_SHULKER_BOX(433),
        MAGENTA_SHULKER_BOX(434),
        LIGHT_BLUE_SHULKER_BOX(435),
        YELLOW_SHULKER_BOX(436),
        LIME_SHULKER_BOX(437),
        PINK_SHULKER_BOX(438),
        GRAY_SHULKER_BOX(439),
        LIGHT_GRAY_SHULKER_BOX(440),
        CYAN_SHULKER_BOX(441),
        PURPLE_SHULKER_BOX(442),
        BLUE_SHULKER_BOX(443),
        BROWN_SHULKER_BOX(444),
        GREEN_SHULKER_BOX(445),
        RED_SHULKER_BOX(446),
        BLACK_SHULKER_BOX(447),
        WHITE_GLAZED_TERRACOTTA(448),
        ORANGE_GLAZED_TERRACOTTA(449),
        MAGENTA_GLAZED_TERRACOTTA(450),
        LIGHT_BLUE_GLAZED_TERRACOTTA(451),
        YELLOW_GLAZED_TERRACOTTA(452),
        LIME_GLAZED_TERRACOTTA(453),
        PINK_GLAZED_TERRACOTTA(454),
        GRAY_GLAZED_TERRACOTTA(455),
        LIGHT_GRAY_GLAZED_TERRACOTTA(456),
        CYAN_GLAZED_TERRACOTTA(457),
        PURPLE_GLAZED_TERRACOTTA(458),
        BLUE_GLAZED_TERRACOTTA(459),
        BROWN_GLAZED_TERRACOTTA(460),
        GREEN_GLAZED_TERRACOTTA(461),
        RED_GLAZED_TERRACOTTA(462),
        BLACK_GLAZED_TERRACOTTA(463),
        WHITE_CONCRETE(464),
        ORANGE_CONCRETE(465),
        MAGENTA_CONCRETE(466),
        LIGHT_BLUE_CONCRETE(467),
        YELLOW_CONCRETE(468),
        LIME_CONCRETE(469),
        PINK_CONCRETE(470),
        GRAY_CONCRETE(471),
        LIGHT_GRAY_CONCRETE(472),
        CYAN_CONCRETE(473),
        PURPLE_CONCRETE(474),
        BLUE_CONCRETE(475),
        BROWN_CONCRETE(476),
        GREEN_CONCRETE(477),
        RED_CONCRETE(478),
        BLACK_CONCRETE(479),
        WHITE_CONCRETE_POWDER(480),
        ORANGE_CONCRETE_POWDER(481),
        MAGENTA_CONCRETE_POWDER(482),
        LIGHT_BLUE_CONCRETE_POWDER(483),
        YELLOW_CONCRETE_POWDER(484),
        LIME_CONCRETE_POWDER(485),
        PINK_CONCRETE_POWDER(486),
        GRAY_CONCRETE_POWDER(487),
        LIGHT_GRAY_CONCRETE_POWDER(488),
        CYAN_CONCRETE_POWDER(489),
        PURPLE_CONCRETE_POWDER(490),
        BLUE_CONCRETE_POWDER(491),
        BROWN_CONCRETE_POWDER(492),
        GREEN_CONCRETE_POWDER(493),
        RED_CONCRETE_POWDER(494),
        BLACK_CONCRETE_POWDER(495),
        TURTLE_EGG(496),
        DEAD_TUBE_CORAL_BLOCK(497),
        DEAD_BRAIN_CORAL_BLOCK(498),
        DEAD_BUBBLE_CORAL_BLOCK(499),
        DEAD_FIRE_CORAL_BLOCK(500),
        DEAD_HORN_CORAL_BLOCK(501),
        TUBE_CORAL_BLOCK(502),
        BRAIN_CORAL_BLOCK(503),
        BUBBLE_CORAL_BLOCK(504),
        FIRE_CORAL_BLOCK(505),
        HORN_CORAL_BLOCK(506),
        TUBE_CORAL(507),
        BRAIN_CORAL(508),
        BUBBLE_CORAL(509),
        FIRE_CORAL(510),
        HORN_CORAL(511),
        DEAD_BRAIN_CORAL(512),
        DEAD_BUBBLE_CORAL(513),
        DEAD_FIRE_CORAL(514),
        DEAD_HORN_CORAL(515),
        DEAD_TUBE_CORAL(516),
        TUBE_CORAL_FAN(517),
        BRAIN_CORAL_FAN(518),
        BUBBLE_CORAL_FAN(519),
        FIRE_CORAL_FAN(520),
        HORN_CORAL_FAN(521),
        DEAD_TUBE_CORAL_FAN(522),
        DEAD_BRAIN_CORAL_FAN(523),
        DEAD_BUBBLE_CORAL_FAN(524),
        DEAD_FIRE_CORAL_FAN(525),
        DEAD_HORN_CORAL_FAN(526),
        BLUE_ICE(527),
        CONDUIT(528),
        POLISHED_GRANITE_STAIRS(529),
        SMOOTH_RED_SANDSTONE_STAIRS(530),
        MOSSY_STONE_BRICK_STAIRS(531),
        POLISHED_DIORITE_STAIRS(532),
        MOSSY_COBBLESTONE_STAIRS(533),
        END_STONE_BRICK_STAIRS(534),
        STONE_STAIRS(535),
        SMOOTH_SANDSTONE_STAIRS(536),
        SMOOTH_QUARTZ_STAIRS(537),
        GRANITE_STAIRS(538),
        ANDESITE_STAIRS(539),
        RED_NETHER_BRICK_STAIRS(540),
        POLISHED_ANDESITE_STAIRS(541),
        DIORITE_STAIRS(542),
        POLISHED_GRANITE_SLAB(543),
        SMOOTH_RED_SANDSTONE_SLAB(544),
        MOSSY_STONE_BRICK_SLAB(545),
        POLISHED_DIORITE_SLAB(546),
        MOSSY_COBBLESTONE_SLAB(547),
        END_STONE_BRICK_SLAB(548),
        SMOOTH_SANDSTONE_SLAB(549),
        SMOOTH_QUARTZ_SLAB(550),
        GRANITE_SLAB(551),
        ANDESITE_SLAB(552),
        RED_NETHER_BRICK_SLAB(553),
        POLISHED_ANDESITE_SLAB(554),
        DIORITE_SLAB(555),
        SCAFFOLDING(556),
        IRON_DOOR(557),
        OAK_DOOR(558),
        SPRUCE_DOOR(559),
        BIRCH_DOOR(560),
        JUNGLE_DOOR(561),
        ACACIA_DOOR(562),
        DARK_OAK_DOOR(563),
        CRIMSON_DOOR(564),
        WARPED_DOOR(565),
        REPEATER(566),
        COMPARATOR(567),
        STRUCTURE_BLOCK(568),
        JIGSAW(569),
        TURTLE_HELMET(570),
        SCUTE(571),
        FLINT_AND_STEEL(572),
        APPLE(573),
        BOW(574),
        ARROW(575),
        COAL(576),
        CHARCOAL(577),
        DIAMOND(578),
        IRON_INGOT(579),
        GOLD_INGOT(580),
        NETHERITE_INGOT(581),
        NETHERITE_SCRAP(582),
        WOODEN_SWORD(583),
        WOODEN_SHOVEL(584),
        WOODEN_PICKAXE(585),
        WOODEN_AXE(586),
        WOODEN_HOE(587),
        STONE_SWORD(588),
        STONE_SHOVEL(589),
        STONE_PICKAXE(590),
        STONE_AXE(591),
        STONE_HOE(592),
        GOLDEN_SWORD(593),
        GOLDEN_SHOVEL(594),
        GOLDEN_PICKAXE(595),
        GOLDEN_AXE(596),
        GOLDEN_HOE(597),
        IRON_SWORD(598),
        IRON_SHOVEL(599),
        IRON_PICKAXE(600),
        IRON_AXE(601),
        IRON_HOE(602),
        DIAMOND_SWORD(603),
        DIAMOND_SHOVEL(604),
        DIAMOND_PICKAXE(605),
        DIAMOND_AXE(606),
        DIAMOND_HOE(607),
        NETHERITE_SWORD(608),
        NETHERITE_SHOVEL(609),
        NETHERITE_PICKAXE(610),
        NETHERITE_AXE(611),
        NETHERITE_HOE(612),
        STICK(613),
        BOWL(614),
        MUSHROOM_STEW(615),
        STRING(616),
        FEATHER(617),
        GUNPOWDER(618),
        WHEAT_SEEDS(619),
        WHEAT(620),
        BREAD(621),
        LEATHER_HELMET(622),
        LEATHER_CHESTPLATE(623),
        LEATHER_LEGGINGS(624),
        LEATHER_BOOTS(625),
        CHAINMAIL_HELMET(626),
        CHAINMAIL_CHESTPLATE(627),
        CHAINMAIL_LEGGINGS(628),
        CHAINMAIL_BOOTS(629),
        IRON_HELMET(630),
        IRON_CHESTPLATE(631),
        IRON_LEGGINGS(632),
        IRON_BOOTS(633),
        DIAMOND_HELMET(634),
        DIAMOND_CHESTPLATE(635),
        DIAMOND_LEGGINGS(636),
        DIAMOND_BOOTS(637),
        GOLDEN_HELMET(638),
        GOLDEN_CHESTPLATE(639),
        GOLDEN_LEGGINGS(640),
        GOLDEN_BOOTS(641),
        NETHERITE_HELMET(642),
        NETHERITE_CHESTPLATE(643),
        NETHERITE_LEGGINGS(644),
        NETHERITE_BOOTS(645),
        FLINT(646),
        PORKCHOP(647),
        COOKED_PORKCHOP(648),
        PAINTING(649),
        GOLDEN_APPLE(650),
        ENCHANTED_GOLDEN_APPLE(651),
        OAK_SIGN(652),
        SPRUCE_SIGN(653),
        BIRCH_SIGN(654),
        JUNGLE_SIGN(655),
        ACACIA_SIGN(656),
        DARK_OAK_SIGN(657),
        CRIMSON_SIGN(658),
        WARPED_SIGN(659),
        BUCKET(660),
        WATER_BUCKET(661),
        LAVA_BUCKET(662),
        MINECART(663),
        SADDLE(664),
        REDSTONE(665),
        SNOWBALL(666),
        OAK_BOAT(667),
        LEATHER(668),
        MILK_BUCKET(669),
        PUFFERFISH_BUCKET(670),
        SALMON_BUCKET(671),
        COD_BUCKET(672),
        TROPICAL_FISH_BUCKET(673),
        BRICK(674),
        CLAY_BALL(675),
        DRIED_KELP_BLOCK(676),
        PAPER(677),
        BOOK(678),
        SLIME_BALL(679),
        CHEST_MINECART(680),
        FURNACE_MINECART(681),
        EGG(682),
        COMPASS(683),
        FISHING_ROD(684),
        CLOCK(685),
        GLOWSTONE_DUST(686),
        COD(687),
        SALMON(688),
        TROPICAL_FISH(689),
        PUFFERFISH(690),
        COOKED_COD(691),
        COOKED_SALMON(692),
        INK_SAC(693),
        COCOA_BEANS(694),
        LAPIS_LAZULI(695),
        WHITE_DYE(696),
        ORANGE_DYE(697),
        MAGENTA_DYE(698),
        LIGHT_BLUE_DYE(699),
        YELLOW_DYE(700),
        LIME_DYE(701),
        PINK_DYE(702),
        GRAY_DYE(703),
        LIGHT_GRAY_DYE(704),
        CYAN_DYE(705),
        PURPLE_DYE(706),
        BLUE_DYE(707),
        BROWN_DYE(708),
        GREEN_DYE(709),
        RED_DYE(710),
        BLACK_DYE(711),
        BONE_MEAL(712),
        BONE(713),
        SUGAR(714),
        CAKE(715),
        WHITE_BED(716),
        ORANGE_BED(717),
        MAGENTA_BED(718),
        LIGHT_BLUE_BED(719),
        YELLOW_BED(720),
        LIME_BED(721),
        PINK_BED(722),
        GRAY_BED(723),
        LIGHT_GRAY_BED(724),
        CYAN_BED(725),
        PURPLE_BED(726),
        BLUE_BED(727),
        BROWN_BED(728),
        GREEN_BED(729),
        RED_BED(730),
        BLACK_BED(731),
        COOKIE(732),
        FILLED_MAP(733),
        SHEARS(734),
        MELON_SLICE(735),
        DRIED_KELP(736),
        PUMPKIN_SEEDS(737),
        MELON_SEEDS(738),
        BEEF(739),
        COOKED_BEEF(740),
        CHICKEN(741),
        COOKED_CHICKEN(742),
        ROTTEN_FLESH(743),
        ENDER_PEARL(744),
        BLAZE_ROD(745),
        GHAST_TEAR(746),
        GOLD_NUGGET(747),
        NETHER_WART(748),
        POTION(749),
        GLASS_BOTTLE(750),
        SPIDER_EYE(751),
        FERMENTED_SPIDER_EYE(752),
        BLAZE_POWDER(753),
        MAGMA_CREAM(754),
        BREWING_STAND(755),
        CAULDRON(756),
        ENDER_EYE(757),
        GLISTERING_MELON_SLICE(758),
        BAT_SPAWN_EGG(759),
        BEE_SPAWN_EGG(760),
        BLAZE_SPAWN_EGG(761),
        CAT_SPAWN_EGG(762),
        CAVE_SPIDER_SPAWN_EGG(763),
        CHICKEN_SPAWN_EGG(764),
        COD_SPAWN_EGG(765),
        COW_SPAWN_EGG(766),
        CREEPER_SPAWN_EGG(767),
        DOLPHIN_SPAWN_EGG(768),
        DONKEY_SPAWN_EGG(769),
        DROWNED_SPAWN_EGG(770),
        ELDER_GUARDIAN_SPAWN_EGG(771),
        ENDERMAN_SPAWN_EGG(772),
        ENDERMITE_SPAWN_EGG(773),
        EVOKER_SPAWN_EGG(774),
        FOX_SPAWN_EGG(775),
        GHAST_SPAWN_EGG(776),
        GUARDIAN_SPAWN_EGG(777),
        HOGLIN_SPAWN_EGG(778),
        HORSE_SPAWN_EGG(779),
        HUSK_SPAWN_EGG(780),
        LLAMA_SPAWN_EGG(781),
        MAGMA_CUBE_SPAWN_EGG(782),
        MOOSHROOM_SPAWN_EGG(783),
        MULE_SPAWN_EGG(784),
        OCELOT_SPAWN_EGG(785),
        PANDA_SPAWN_EGG(786),
        PARROT_SPAWN_EGG(787),
        PHANTOM_SPAWN_EGG(788),
        PIG_SPAWN_EGG(789),
        PIGLIN_SPAWN_EGG(790),
        PIGLIN_BRUTE_SPAWN_EGG(791),
        PILLAGER_SPAWN_EGG(792),
        POLAR_BEAR_SPAWN_EGG(793),
        PUFFERFISH_SPAWN_EGG(794),
        RABBIT_SPAWN_EGG(795),
        RAVAGER_SPAWN_EGG(796),
        SALMON_SPAWN_EGG(797),
        SHEEP_SPAWN_EGG(798),
        SHULKER_SPAWN_EGG(799),
        SILVERFISH_SPAWN_EGG(800),
        SKELETON_SPAWN_EGG(801),
        SKELETON_HORSE_SPAWN_EGG(802),
        SLIME_SPAWN_EGG(803),
        SPIDER_SPAWN_EGG(804),
        SQUID_SPAWN_EGG(805),
        STRAY_SPAWN_EGG(806),
        STRIDER_SPAWN_EGG(807),
        TRADER_LLAMA_SPAWN_EGG(808),
        TROPICAL_FISH_SPAWN_EGG(809),
        TURTLE_SPAWN_EGG(810),
        VEX_SPAWN_EGG(811),
        VILLAGER_SPAWN_EGG(812),
        VINDICATOR_SPAWN_EGG(813),
        WANDERING_TRADER_SPAWN_EGG(814),
        WITCH_SPAWN_EGG(815),
        WITHER_SKELETON_SPAWN_EGG(816),
        WOLF_SPAWN_EGG(817),
        ZOGLIN_SPAWN_EGG(818),
        ZOMBIE_SPAWN_EGG(819),
        ZOMBIE_HORSE_SPAWN_EGG(820),
        ZOMBIE_VILLAGER_SPAWN_EGG(821),
        ZOMBIFIED_PIGLIN_SPAWN_EGG(822),
        EXPERIENCE_BOTTLE(823),
        FIRE_CHARGE(824),
        WRITABLE_BOOK(825),
        WRITTEN_BOOK(826),
        EMERALD(827),
        ITEM_FRAME(828),
        FLOWER_POT(829),
        CARROT(830),
        POTATO(831),
        BAKED_POTATO(832),
        POISONOUS_POTATO(833),
        MAP(834),
        GOLDEN_CARROT(835),
        SKELETON_SKULL(836),
        WITHER_SKELETON_SKULL(837),
        PLAYER_HEAD(838),
        ZOMBIE_HEAD(839),
        CREEPER_HEAD(840),
        DRAGON_HEAD(841),
        CARROT_ON_A_STICK(842),
        WARPED_FUNGUS_ON_A_STICK(843),
        NETHER_STAR(844),
        PUMPKIN_PIE(845),
        FIREWORK_ROCKET(846),
        FIREWORK_STAR(847),
        ENCHANTED_BOOK(848),
        NETHER_BRICK(849),
        QUARTZ(850),
        TNT_MINECART(851),
        HOPPER_MINECART(852),
        PRISMARINE_SHARD(853),
        PRISMARINE_CRYSTALS(854),
        RABBIT(855),
        COOKED_RABBIT(856),
        RABBIT_STEW(857),
        RABBIT_FOOT(858),
        RABBIT_HIDE(859),
        ARMOR_STAND(860),
        IRON_HORSE_ARMOR(861),
        GOLDEN_HORSE_ARMOR(862),
        DIAMOND_HORSE_ARMOR(863),
        LEATHER_HORSE_ARMOR(864),
        LEAD(865),
        NAME_TAG(866),
        COMMAND_BLOCK_MINECART(867),
        MUTTON(868),
        COOKED_MUTTON(869),
        WHITE_BANNER(870),
        ORANGE_BANNER(871),
        MAGENTA_BANNER(872),
        LIGHT_BLUE_BANNER(873),
        YELLOW_BANNER(874),
        LIME_BANNER(875),
        PINK_BANNER(876),
        GRAY_BANNER(877),
        LIGHT_GRAY_BANNER(878),
        CYAN_BANNER(879),
        PURPLE_BANNER(880),
        BLUE_BANNER(881),
        BROWN_BANNER(882),
        GREEN_BANNER(883),
        RED_BANNER(884),
        BLACK_BANNER(885),
        END_CRYSTAL(886),
        CHORUS_FRUIT(887),
        POPPED_CHORUS_FRUIT(888),
        BEETROOT(889),
        BEETROOT_SEEDS(890),
        BEETROOT_SOUP(891),
        DRAGON_BREATH(892),
        SPLASH_POTION(893),
        SPECTRAL_ARROW(894),
        TIPPED_ARROW(895),
        LINGERING_POTION(896),
        SHIELD(897),
        ELYTRA(898),
        SPRUCE_BOAT(899),
        BIRCH_BOAT(900),
        JUNGLE_BOAT(901),
        ACACIA_BOAT(902),
        DARK_OAK_BOAT(903),
        TOTEM_OF_UNDYING(904),
        SHULKER_SHELL(905),
        IRON_NUGGET(906),
        KNOWLEDGE_BOOK(907),
        DEBUG_STICK(908),
        MUSIC_DISC_13(909),
        MUSIC_DISC_CAT(910),
        MUSIC_DISC_BLOCKS(911),
        MUSIC_DISC_CHIRP(912),
        MUSIC_DISC_FAR(913),
        MUSIC_DISC_MALL(914),
        MUSIC_DISC_MELLOHI(915),
        MUSIC_DISC_STAL(916),
        MUSIC_DISC_STRAD(917),
        MUSIC_DISC_WARD(918),
        MUSIC_DISC_11(919),
        MUSIC_DISC_WAIT(920),
        MUSIC_DISC_PIGSTEP(921),
        TRIDENT(922),
        PHANTOM_MEMBRANE(923),
        NAUTILUS_SHELL(924),
        HEART_OF_THE_SEA(925),
        CROSSBOW(926),
        SUSPICIOUS_STEW(927),
        LOOM(928),
        FLOWER_BANNER_PATTERN(929),
        CREEPER_BANNER_PATTERN(930),
        SKULL_BANNER_PATTERN(931),
        MOJANG_BANNER_PATTERN(932),
        GLOBE_BANNER_PATTERN(933),
        PIGLIN_BANNER_PATTERN(934),
        COMPOSTER(935),
        BARREL(936),
        SMOKER(937),
        BLAST_FURNACE(938),
        CARTOGRAPHY_TABLE(939),
        FLETCHING_TABLE(940),
        GRINDSTONE(941),
        LECTERN(942),
        SMITHING_TABLE(943),
        STONECUTTER(944),
        BELL(945),
        LANTERN(946),
        SOUL_LANTERN(947),
        SWEET_BERRIES(948),
        CAMPFIRE(949),
        SOUL_CAMPFIRE(950),
        SHROOMLIGHT(951),
        HONEYCOMB(952),
        BEE_NEST(953),
        BEEHIVE(954),
        HONEY_BOTTLE(955),
        HONEY_BLOCK(956),
        HONEYCOMB_BLOCK(957),
        LODESTONE(958),
        NETHERITE_BLOCK(959),
        ANCIENT_DEBRIS(960),
        TARGET(961),
        CRYING_OBSIDIAN(962),
        BLACKSTONE(963),
        BLACKSTONE_SLAB(964),
        BLACKSTONE_STAIRS(965),
        GILDED_BLACKSTONE(966),
        POLISHED_BLACKSTONE(967),
        POLISHED_BLACKSTONE_SLAB(968),
        POLISHED_BLACKSTONE_STAIRS(969),
        CHISELED_POLISHED_BLACKSTONE(970),
        POLISHED_BLACKSTONE_BRICKS(971),
        POLISHED_BLACKSTONE_BRICK_SLAB(972),
        POLISHED_BLACKSTONE_BRICK_STAIRS(973),
        CRACKED_POLISHED_BLACKSTONE_BRICKS(974),
        RESPAWN_ANCHOR(975);

        private final int id;

        MaterialTypes(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
