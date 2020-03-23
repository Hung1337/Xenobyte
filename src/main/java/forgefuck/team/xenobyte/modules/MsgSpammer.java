package forgefuck.team.xenobyte.modules;

import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.gui.InputType;
import forgefuck.team.xenobyte.api.gui.WidgetMode;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.module.PerformSource;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.swing.UserInput;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MsgSpammer extends CheatModule {

    @Cfg("message")
    private List<String> message;
    @Cfg("players")
    private List<String> players;
    @Cfg("repeats")
    private List<String> repeats;
    private SpamListInput input;
    private Thread sendThread;
    private int count;

    public MsgSpammer() {
        super("MsgSpammer", Category.MISC, PerformMode.SINGLE);
        players = new ArrayList<String>();
        message = new ArrayList<String>();
        message.add("WOOF");
        repeats = new ArrayList<String>();
        repeats.add("1");
    }

    @Override
    public void onPostInit() {
        input = new SpamListInput(players);
    }

    @Override
    public void onPerform(PerformSource src) {
        if (sendThread != null && sendThread.isAlive()) {
            widgetMessage("Рассылка еще ведется..", WidgetMode.FAIL);
        } else {
            String mess = message.get(0);
            if (!mess.isEmpty()) {
                count = Integer.valueOf(repeats.get(0));
                sendThread = new Thread(() -> {
                    List<String> out = new ArrayList<String>();
                    List<String> tab = utils.tabList();
                    if (input.white.isSelected()) {
                        out = tab.stream().filter(players::contains).collect(Collectors.toList());
                    } else if (input.black.isSelected()) {
                        out = tab.stream().filter(p -> !players.contains(p)).collect(Collectors.toList());
                    } else {
                        out = tab;
                    }
                    for (String player : out) {
                        if (!utils.isInGame()) {
                            break;
                        }
                        sendMessage(player, mess);
                        if (count == 0) {
                            sendMessage(player, mess);
                        } else {
                            while (count != 0) {
                                sendMessage(player, mess);
                                count--;
                            }
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                    }
                });
                sendThread.start();
            } else {
                widgetMessage("Сообщение не задано", WidgetMode.FAIL);
            }
        }
    }

    private void sendMessage(String player, String mess) {
        if (!utils.myName().equals(player)) {
            utils.serverChatMessage(String.format("/m %s %s", player, mess));
        }
    }

    @Override
    public String moduleDesc() {
        return "Рассылка сообщений заданным игрокам с задержкой в секунду.";
    }

    @Override
    public Panel settingPanel() {
        return new Panel(new Button("Message") {
            @Override
            public void onLeftClick() {
                new UserInput("Сообщение", message, InputType.SINGLE_STRING).showFrame();
            }

            @Override
            public String elementDesc() {
                return "Сообщение, которое нужно отправить";
            }
        }, new Button("Repeats") {
            @Override
            public void onLeftClick() {
                new UserInput("Сколько раз написать?", repeats, InputType.SINGLE_STRING).showFrame();
            }

            @Override
            public String elementDesc() {
                return "Сообщение отправится столько раз, сколько вы укажите тут.";
            }
        }, new Button("Players") {
            @Override
            public void onLeftClick() {
                input.showFrame();
            }

            @Override
            public String elementDesc() {
                return "Кому отправить?";
            }
        });
    }

    class SpamListInput extends UserInput {

        JRadioButton white, black, all;
        ButtonGroup group;
        JPanel panel;

        public SpamListInput(List<String> list) {
            super("Кому отправить?", list, InputType.CUSTOM);
            all = new JRadioButton("Игнорируя список", true);
            white = new JRadioButton("Игрокам из списка");
            black = new JRadioButton("Всем, кроме списка");
            group = new ButtonGroup();
            panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            group.add(white);
            group.add(black);
            group.add(all);
            panel.add(white, GBC);
            panel.add(black, GBC);
            panel.add(all, GBC);
            add(panel);
            pack();
        }
    }

}
