package forgefuck.team.xenobyte.modules;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forgefuck.team.xenobyte.api.config.Cfg;
import forgefuck.team.xenobyte.api.module.Category;
import forgefuck.team.xenobyte.api.module.CheatModule;
import forgefuck.team.xenobyte.api.module.PerformMode;
import forgefuck.team.xenobyte.api.render.IDraw;
import forgefuck.team.xenobyte.gui.click.elements.Button;
import forgefuck.team.xenobyte.gui.click.elements.Panel;
import forgefuck.team.xenobyte.gui.click.elements.ScrollSlider;
import forgefuck.team.xenobyte.utils.Utils;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class Esp extends CheatModule {

	@Cfg("bindLines")
	private boolean bindLines;
	@Cfg("villagers")
	private boolean villagers;
	@Cfg("minecarts")
	private boolean minecarts;
	@Cfg("customnpc")
	private boolean customnpc;
	@Cfg("monsters")
	private boolean monsters;
	@Cfg("players")
	private boolean players;
	@Cfg("animals")
	private boolean animals;
	@Cfg("blocks")
	private boolean blocks;
	@Cfg("lines")
	private boolean lines;
	@Cfg("radius")
	private int radius;
	@Cfg("drop")
	private boolean drop;
	@Cfg("thaumaura")
	private boolean thaumaura;
	private List<EspObject> objects;
	private double startLines[];
	private boolean bobbing;
	private Utils utils = new Utils();

	public Esp() {
		super("Esp", Category.WORLD, PerformMode.TOGGLE);
		objects = new ArrayList<EspObject>();
		startLines = new double[3];
		bindLines = true;
		players = true;
		blocks = true;
		lines = true;
		thaumaura = false;
		radius = 100;
	}

	@Override
	public void onTick(boolean inGame) {
		if (inGame) {
			List<EspObject> out = new ArrayList<EspObject>();
			utils.nearEntityes(radius).forEach(e -> {
				if (players && utils.isPlayer(e)) {
					out.add(new EspObject(e, 1, 0, 1));
				} else if (monsters && utils.isMonster(e)) {
					out.add(new EspObject(e, 1, 0, 0));
				} else if (animals && utils.isAnimal(e)) {
					out.add(new EspObject(e, 0, 1, 0));
				} else if (drop && utils.isDrop(e)) {
					out.add(new EspObject(e, 1, 1, 0));
				} else if (villagers && utils.isVillager(e)) {
					out.add(new EspObject(e, 0, 1, 1));
				} else if (customnpc && utils.isCustom(e)) {
					out.add(new EspObject(e, 0, 0, 1));
				} else if (minecarts && e instanceof EntityMinecart) {
					out.add(new EspObject(e, 0, 0, 0));
				}
			});
			utils.nearTiles().forEach(e -> {
				if(thaumaura && utils.isNodeAura(e)){
					out.add(new EspObject(e, 0, 0, 0));
				}
			});
			objects = out;
			utils.mc().gameSettings.viewBobbing = !lines || !bindLines || objects.isEmpty();
		}
	}

	@Override
	public void onDisabled() {
		utils.mc().gameSettings.viewBobbing = true;
		startLines = new double[3];
		objects.clear();
	}

	@SubscribeEvent
	public void worldRender(RenderWorldLastEvent e) {
		objects.forEach(EspObject::draw);
	}

	@Override
	public String moduleDesc() {
		return "Подсветка заданных объектов в мире";
	}

	@Override
	public Panel settingPanel() {
		return new Panel(new Button("EspBlock", blocks) {
			@Override
			public void onLeftClick() {
				buttonValue(blocks = !blocks);
			}

			@Override
			public String elementDesc() {
				return "Отрисовка блока";
			}
		}, new Button("TracerLine", lines) {
			@Override
			public void onLeftClick() {
				buttonValue(lines = !lines);
			}

			@Override
			public String elementDesc() {
				return "Отрисовка линий";
			}
		}, new Button("BindLines", bindLines) {
			@Override
			public void onLeftClick() {
				buttonValue(bindLines = !bindLines);
			}

			@Override
			public String elementDesc() {
				return "Привязка линий к курсору";
			}
		}, new Button("Monsters", monsters) {
			@Override
			public void onLeftClick() {
				buttonValue(monsters = !monsters);
			}

			@Override
			public String elementDesc() {
				return "Отображать монстров";
			}
		}, new Button("Animals", animals) {
			@Override
			public void onLeftClick() {
				buttonValue(animals = !animals);
			}

			@Override
			public String elementDesc() {
				return "Отображать животных";
			}
		}, new Button("Villagers", villagers) {
			@Override
			public void onLeftClick() {
				buttonValue(villagers = !villagers);
			}

			@Override
			public String elementDesc() {
				return "Отображать жителей";
			}
		}, new Button("CustomNPC", customnpc) {
			@Override
			public void onLeftClick() {
				buttonValue(customnpc = !customnpc);
			}

			@Override
			public String elementDesc() {
				return "Отображать неписей";
			}
		}, new Button("Players", players) {
			@Override
			public void onLeftClick() {
				buttonValue(players = !players);
			}

			@Override
			public String elementDesc() {
				return "Отображать игроков";
			}
		}, new Button("Drop", drop) {
			@Override
			public void onLeftClick() {
				buttonValue(drop = !drop);
			}

			@Override
			public String elementDesc() {
				return "Отображать выброшенные предметов";
			}
		}, new Button("Minecarts", minecarts) {
			@Override
			public void onLeftClick() {
				buttonValue(minecarts = !minecarts);
			}

			@Override
			public String elementDesc() {
				return "Отображать вагонетки";
			}
		}, new Button("ThaumAura", thaumaura) {
			@Override
			public void onLeftClick() {
				if (Loader.isModLoaded("Thaumcraft")) {
					buttonValue(thaumaura = !thaumaura);
				} else {
					buttonValue(false);
				}
			}

			@Override
			public String elementDesc() {
				if (Loader.isModLoaded("Thaumcraft")) {
					return "Отображать узлы ауры";
				}
				return "Thaumcraft не найден.";
			}
		}, new ScrollSlider("Radius", radius, 200) {
			@Override
			public void onScroll(int dir, boolean withShift) {
				radius = processSlider(dir, withShift);
			}

			@Override
			public String elementDesc() {
				return "Радиус поиска объектов";
			}
		}

		);
	}

	class EspObject implements IDraw {

		final double x, y, z;
		final float r, g, b;

		EspObject(Entity ent, float r, float g, float b) {
			this.x = ent.posX;
			this.y = ent.posY;
			this.z = ent.posZ;
			this.r = r;
			this.g = g;
			this.b = b;
		}
		
		
		EspObject(TileEntity ent, float r, float g, float b) {
			this.x = ent.xCoord;
			this.y = ent.yCoord;
			this.z = ent.zCoord;
			this.r = r;
			this.g = g;
			this.b = b;
		}

		@Override
		public void draw() {
			if ((bindLines) || (startLines[0] == 0D && startLines[1] == 0D && startLines[2] == 0D)) {
				startLines[0] = RenderManager.instance.viewerPosX;
				startLines[1] = RenderManager.instance.viewerPosY;
				startLines[2] = RenderManager.instance.viewerPosZ;
			}
			if (blocks) {
				render.WORLD.drawEspBlock(x - 0.5, y, z - 0.5, r, g, b, 0.4F, 1);
			}
			if (lines) {
				render.WORLD.drawEspLine(startLines[0], startLines[1], startLines[2], x, y, z, r, g, b, 0.6F, 1.5F);
			}
		}

	}

}
