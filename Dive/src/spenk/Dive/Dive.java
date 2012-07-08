package spenk.Dive;
import net.canarymod.Canary;
import net.canarymod.Logman;
import net.canarymod.hook.Hook;
import net.canarymod.plugin.*;

public class Dive extends Plugin{

	public void enable() {
		Canary.hooks().registerListener(new DiveListener(), this, Priority.NORMAL, Hook.Type.DAMAGE);
		Canary.hooks().registerListener(new DiveListener(), this, Priority.NORMAL, Hook.Type.COMMAND);
		Logman.logInfo("[Dive] - version 1.0Rby Spenk enabled!");
	}

	public void disable() {
		Logman.logInfo("[Dive] - version 1.0R by Spenk disabled!");
	}

}
