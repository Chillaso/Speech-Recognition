package com.atsistemas.almunia.utils;

import java.util.HashMap;
import java.util.Map;

public class Commands {
	
	public static final String[] CAMELAR= {"google","cómo","aprobar","segundo"};
	public static final String[] IES_ALMUNIA= {"Miguel", "mejores", "alumnos"};
	public static final String[] YOU_ARE = {"google","puto","amo","quién"};
	
	public static final String[][] COMMANDS = {CAMELAR, IES_ALMUNIA, YOU_ARE};
	
	private Map<String[],String> mapCommands;
	
	public Commands()
	{
		mapCommands = new HashMap<>();
		mapCommands.put(CAMELAR, ConstantUtils.CAMELAR);
		mapCommands.put(IES_ALMUNIA, ConstantUtils.IES_ALMUNIA);
		mapCommands.put(YOU_ARE, ConstantUtils.YOU_ARE);
	}

	public Map<String[], String> getMapCommands() {
		return mapCommands;
	}
	
}
