package forgefuck.team.xenobyte.modules;

import cpw.mods.fml.common.Loader;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;

public class TinkerChest extends CheatModule {

	public TinkerChest() {
		super("TinkerChest", Category.MODS, PerformMode.SINGLE);
	}

	@Override
	public void onPerform(PerformSource src) {
		utils.sendPacket("TConstruct", (byte) 1, 102);
	}

	@Override
	public boolean isWorking() {
		return Loader.isModLoaded("TConstruct");
	}

	@Override
	public String moduleDesc() {
		return "Открытие дорожной сумки без её наличия в инвентаре";
	}

}
