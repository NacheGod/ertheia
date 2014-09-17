package databuilder;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import l2s.gameserver.Config;
import l2s.gameserver.data.xml.parser.BaseStatsBonusParser;
import l2s.gameserver.data.xml.parser.LevelBonusParser;
import l2s.gameserver.data.xml.parser.NpcParser;
import l2s.gameserver.tables.SkillTable;
import l2s.gameserver.templates.item.ArmorTemplate;
import l2s.gameserver.templates.item.ArmorTemplate.ArmorType;
import l2s.gameserver.templates.item.ItemGrade;
import l2s.gameserver.templates.item.ItemTemplate;
import databuilder.utils.L2String;
import databuilder.utils.XmlPretty;
import databuilder.utils.diff_match_patch;
import databuilder.utils.diff_match_patch.Diff;
import databuilder.utils.diff_match_patch.Operation;
import databuilder.xml.builder.ItemBuilder;
import databuilder.xml.builder.SkillBuilder;
import databuilder.xml.holder.ItemHolder;
import databuilder.xml.parser.ItemParser;
import databuilder.xml.parser.NpcGaiParser;
import databuilder.xml.parser.SkillParser;

public class MainBuilder
{
	
	public static MainBuilder _instance;
	public static Connection _conn = null;
	public static final String _datapack_path = "/Users/mylove1412/Workspace/ertheia/dist/gameserver/";
	public static String _jdbc_connection = "jdbc:mysql://localhost/l2_raw?user=root&password=";
	
	private MainBuilder()
	{
		//BloodFakePlayers.getInstance();
	}
	
	public static Connection connection(){
		
		if(_conn == null)
		{
			try {
				_conn = DriverManager.getConnection(_jdbc_connection);

			    // Do something with the Connection

			} catch (SQLException ex) {
			    // handle any errors
			    System.out.println("SQLException: " + ex.getMessage());
			    System.out.println("SQLState: " + ex.getSQLState());
			    System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
		return _conn;
	}
	
	public static void buildNpc(){
		
		SkillTable.getInstance().load();
		BaseStatsBonusParser.getInstance().load();
		LevelBonusParser.getInstance().load();
		NpcParser.getInstance().load();
		NpcGaiParser.getInstance().load();
	}
	
	public static class CustomComparator implements Comparator<ItemTemplate> {
	    @Override
	    public int compare(ItemTemplate o1, ItemTemplate o2) {
	        if(!o1.getGrade().equals(o2.getGrade()))
	        	return o1.getGrade().compareTo(o2.getGrade());
	        
	        return 0;
	    }
	}
	
	
	
	
	
	
	
	public static void main(String[] args)
	{
//		System.currentTimeMillis();
		System.out.println("Start...");
		try {
			Config.DATAPACK_ROOT = new File(_datapack_path).getCanonicalFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		SkillTable.getInstance().load();
//		OptionDataParser.getInstance().load();
//		VariationDataParser.getInstance().load();
//		ItemParser.getInstance().load();
//		buildFated();
		
		
		
//		ItemBuilder.getInstance().build();
//		ItemBuilder.getInstance().store();
		
		SkillParser.getInstance().load();
		SkillBuilder.getInstance().buildAndStore();
		
		String text1 = "Attacks the enemy with 2581 Power added to P. Atk. Requires a dualsword. Over-hit and Critical are possible. Ignores Shield Defense. Enchant Power: Increases Power.";
		String text2 = "Attacks the enemy with 2858 Power added to P. Atk. Requires a dualsword. Over-hit and Critical are possible. Ignores Shield Defense. Enchant Power: Increases Power.";
		System.out.println(L2String.diffDesc(text1, text2));
//		System.out.println(L2String.diffDesc("Attacks the enemy with 517 Power added to P. Atk. Requires a dualsword. Over-hit and Critical are possible. Ignores Shield Defense.", "Attacks the enemy with 1527 Power added to P. Atk. Requires a dualsword. Over-hit and Critical are possible. Ignores Shield Defense."));
	}
	
	
	
	
	
}