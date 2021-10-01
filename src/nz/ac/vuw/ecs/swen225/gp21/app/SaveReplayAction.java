package nz.ac.vuw.ecs.swen225.gp21.app;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.DirectMove;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.TerrainChange;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.NoMovement;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.PlayerController;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.RandomMovement;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Monster;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.GameOver;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Replaying;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.CopperDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.CopperKey;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.ExitLock;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.ExitTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Free;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GoldDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GoldKey;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GreenDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GreenKey;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Info;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.OneWayEast;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.OneWayNorth;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.OneWaySouth;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.OneWayWest;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.SilverDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.SilverKey;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Teleporter;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Treasure;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Wall;
import nz.ac.vuw.ecs.swen225.gp21.recorder.RecorderException;

public class SaveReplayAction implements Action {

	private File saveFile;
	
	private XmlMapper xmlMapper;
	
	public SaveReplayAction(File saveFile) {
		this.saveFile = saveFile;
    this.xmlMapper = registerXML();
	}
	
	@Override
	public void execute(Controller control) {
		
		if (!control.gameLoop.getIsPlaying()) {
			control.warning("Cannot save unless a game is being played.");
			return;
		}
		
		if (control.gameLoop.getIsReplay()) {
			control.warning("Can't save a replay whilst in a replay.");
			return;
		}
		
		try {
			control.recorder.setLevel(control.levelNumber);
			control.recorder.save(saveFile, xmlMapper);
		} catch (RecorderException e) {
			control.warning("Something went wrong with saving the replay:\n" + e.getMessage());
			return;
		}
		
		control.report("Save successful");
	}

	@Override
	public String actionName() {
		return "SaveReplayAction";
	}
	
	/**
	 * NEED TO REGISTER SUBTYPES USING THIS BECAUSE THE RECORDER IS 
   * UNABLE TO HANDLE THIS BECAUSE OF THE DEPENDENCY 
   * REQUIREMENTS.
   * 
	 * @return the XmlMapper with registered types.
	 */
	public static XmlMapper registerXML() {
	  XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.registerSubtypes(
            new NamedType(GameUpdateProxy.class, "UpdateProxy"),
            new NamedType(DirectMove.class, "DirectMove"),
            new NamedType(TerrainChange.class, "TerrainChange"),
            
            new NamedType(Chip.class, "Chip"),
            new NamedType(Block.class, "Block"),
            new NamedType(Monster.class, "Monster"),

            new NamedType(KeyItem.class, "KeyItem"),

            new NamedType(NoMovement.class, "NoMovement"),
            new NamedType(PlayerController.class, "PlayerController"),
            new NamedType(RandomMovement.class, "RandomMovement"),

            new NamedType(Running.class, "Running"),
            new NamedType(GameOver.class, "GameOver"),
            new NamedType(Loading.class, "Loading"),
            new NamedType(Replaying.class, "Replaying"),

            new NamedType(CopperDoor.class, "CopperDoor"),
            new NamedType(CopperKey.class, "CopperKey"),
            new NamedType(ExitLock.class, "ExitLock"),
            new NamedType(ExitTile.class, "ExitTile"),
            new NamedType(Free.class, "Free"),
            new NamedType(GoldDoor.class, "GoldDoor"),
            new NamedType(GoldKey.class, "GoldKey"),
            new NamedType(GreenDoor.class, "GreenDoor"),
            new NamedType(GreenKey.class, "GreenKey"),
            new NamedType(Info.class, "Info"),
            new NamedType(OneWayEast.class, "OneWayEast"),
            new NamedType(OneWayNorth.class, "OneWayNorth"),
            new NamedType(OneWaySouth.class, "OneWaySouth"),
            new NamedType(OneWayWest.class, "OneWayWest"),
            new NamedType(SilverDoor.class, "SilverDoor"),
            new NamedType(SilverKey.class, "SilverKey"),
            new NamedType(Teleporter.class, "Teleporter"),
            new NamedType(Treasure.class, "Treasure"),
            new NamedType(Wall.class, "Wall"));
      xmlMapper.getFactory().getXMLOutputFactory().setProperty("javax.xml.stream.isRepairingNamespaces", false);
      return xmlMapper;
	}

}
