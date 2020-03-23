package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.Xeno;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.InputType;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.swing.UserInput;

import java.util.ArrayList;
import java.util.List;

public class saveAss extends CheatModule {

    @Cfg("hp")
    private List<String> hp;
    private String cmd = "";

    public saveAss() {
        super("SaveAss", Category.PLAYER, PerformMode.TOGGLE);
        hp = new ArrayList<>();
        hp.add("6");
    }

    @Override
    public void onTick(boolean inGame) {
        int HP = Integer.parseInt(hp.get(0));
        if (inGame) {
            if (HP > 0 && HP < 20) {
                if (Xeno.utils.player().getHealth() <= HP && !Xeno.utils.isInCreative()) {
                    Xeno.utils.serverChatMessage("/home");
                    this.moduleHandler().disable(this);
                }
            }
        }
    }

    @Override
    public String moduleDesc() {
        return "Пиздец, я заебался, спасай мой зад(После телепортации модуль выключается)";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(new Button("HP") {
            @Override
            public void onLeftClick() {
                new UserInput("Количество ХП", hp, InputType.SINGLE_STRING).showFrame();
            }

            @Override
            public String elementDesc() {
                return "Количество ХП, при котором тебя отправляют на /home";
            }
        });
    }
}
