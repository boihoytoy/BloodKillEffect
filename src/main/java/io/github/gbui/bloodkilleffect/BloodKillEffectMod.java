package io.github.gbui.bloodkilleffect;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = BloodKillEffectMod.MODID,
     name = BloodKillEffectMod.NAME,
     version = BloodKillEffectMod.VERSION,
     guiFactory = "io.github.gbui.bloodkilleffect.BKEGuiFactory",
     acceptedMinecraftVersions = "*")
public class BloodKillEffectMod {
    public static final String MODID = "BloodKillEffect";
    public static final String NAME = "Blood Kill Effect Mod";
    public static final String VERSION = "0.0.4";

    public static boolean enabled;
    public static boolean playersOnly;

    private static Configuration config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (event.getSide().isServer()) {
            return;
        }

        config = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig(true);

        FMLCommonHandler.instance().bus().register(this);
    }

    public static Configuration getConfig() {
        return config;
    }

    private static void syncConfig(boolean load) {
        if (load) {
            config.load();
        }

        String category = Configuration.CATEGORY_GENERAL;

        Property prop;
        prop = config.get(category, "enabled", true);
        prop.setLanguageKey("bloodkilleffect.configgui.enabled");
        enabled = prop.getBoolean();

        prop = config.get(category, "playersOnly", false);
        prop.setLanguageKey("bloodkilleffect.configgui.playersOnly");
        playersOnly = prop.getBoolean();

        config.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (MODID.equals(event.modID)) {
            syncConfig(false);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!enabled) {
            return;
        }
        World world = Minecraft.getMinecraft().theWorld;
        if (world != null) {
            for (Entity entity : world.loadedEntityList) {
                if (entity instanceof EntityLivingBase && (!playersOnly || entity instanceof EntityPlayer)) {
                    EntityLivingBase livingEntity = (EntityLivingBase) entity;
                    if (livingEntity.deathTime == 1) {
                        BlockPos pos = new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
                        IBlockState blockState = Blocks.redstone_block.getDefaultState();
                        Minecraft.getMinecraft().renderGlobal.playAuxSFX(null, 2001, pos, Block.getStateId(blockState));
                    }
                }
            }
        }
    }
}
