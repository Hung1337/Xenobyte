package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class AntiKnockBack extends CheatModule {

    public AntiKnockBack() {
        super("AntiKnockBack", Category.MOVE, PerformMode.TOGGLE);
    }

    @Override
    public boolean doReceivePacket(Packet packet) {
        if (packet instanceof S12PacketEntityVelocity) {
            return !utils.isInGame() || utils.myId() != ((S12PacketEntityVelocity) packet).func_149412_c();
        }
        return true;
    }

    @Override
    public String moduleDesc() {
        return "Выключает эфект отбрасывания у игрока";
    }

}