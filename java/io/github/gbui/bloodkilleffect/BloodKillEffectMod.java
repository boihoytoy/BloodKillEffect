package io.github.gbui.bloodkilleffect;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = BloodKillEffectMod.MODID,
     name = BloodKillEffectMod.NAME,
     version = BloodKillEffectMod.VERSION,
     guiFactory = "io.github.gbui.bloodkilleffect.client.BKEGuiFactory",
     acceptedMinecraftVersions = "*")
public class BloodKillEffectMod {
    public static final String MODID = "BloodKillEffect";
    public static final String NAME = "Blood Kill Effect Mod";
    public static final String VERSION = "0.0.2";

    public static boolean playersOnly;

    private static Configuration config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig(true);

        MinecraftForge.EVENT_BUS.register(this);
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
        prop = config.get(category, "playersOnly", true);
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
    public void onLivingDeath(LivingDeathEvent event) {
        EntityLivingBase entity = event.entityLiving;
        if (!playersOnly || entity instanceof EntityPlayer) {
            World world = entity.worldObj;
            BlockPos pos = new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
            IBlockState blockState = Blocks.redstone_block.getDefaultState();
            world.playAuxSFX(2001, pos, Block.getStateId(blockState)); // Used in World.destroyBlock
        }
    }
}
