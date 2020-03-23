package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import net.minecraft.client.Minecraft;

public class abCleanChat extends CheatModule {

    public abCleanChat() {
        super("ChatCleaner", Category.MISC, PerformMode.SINGLE);
    }

    @Override
    public void onPerform(PerformSource src) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages();
    }

    @Override
    public String moduleDesc() {
        return "Чистка чата.";
    }

}
