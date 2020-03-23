package forgefuck.team.xenobyte;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.handlers.ModuleHandler;

@Mod(modid = Xeno.mod_id, name = "S3X", version = Xeno.mod_version)
public class XenoByte {

    @EventHandler
    public void init(FMLInitializationEvent e) {
        if (e == null) {
            starter(null);
        }
    }

    @EventHandler
    public void starter(FMLLoadCompleteEvent e) {
        new ModuleHandler();
    }

}