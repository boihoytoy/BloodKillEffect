package io.github.gbui.bloodkilleffect;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = BloodKillEffectMod.MODID, name = BloodKillEffectMod.NAME, version = BloodKillEffectMod.VERSION, acceptedMinecraftVersions = "*")
public class BloodKillEffectMod {
    public static final String MODID = "BloodKillEffect";
    public static final String NAME = "Blood Kill Effect Mod";
    public static final String VERSION = "0.0.1";

    public static boolean playersOnly;

    public BloodKillEffectMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        playersOnly = config.get(Configuration.CATEGORY_GENERAL, "playersOnly", true).getBoolean();
        config.save();
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        EntityLivingBase entity = event.entityLiving;
        if (!playersOnly || entity instanceof EntityPlayer) {
            World world = entity.worldObj;
            double x = entity.posX;
            double y = entity.posY + entity.getEyeHeight();
            double z = entity.posZ;
            world.playAuxSFX(2001, new BlockPos(x, y, z), Block.getStateId(Blocks.redstone_block.getDefaultState()));
        }
    }
}
