package forgefuck.team.xenobyte.api;

import forgefuck.team.xenobyte.render.Renderer;
import forgefuck.team.xenobyte.utils.Utils;

public interface Xeno {

	String mod_id = "xenobyte";
	String author = "N1nt3nd0";
	String mod_name = "S3X";
	String mod_version = "1.0.7";
	String format_prefix = "§8[§4" + mod_name + "§8]§r ";

	Renderer render = new Renderer();
	Utils utils = new Utils();

}