package cvt;

import arc.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.mod.*;

import static mindustry.Vars.*;

public class Main extends Mod{
    public static String vText;
    public static final String defVText = "AYO THIS MF AIN'T CHANGED THE TEXT THINGY IN SETTINGS > GAME > VERSION TEXT !!!";

    public Main(){
//        loadDialog();
        Events.on(ClientLoadEvent.class, e -> replaceText());
    }

    public void rebuildMenu() {
        vText = Core.settings.getString("Version Text", defVText);
        ui.menuGroup.clear();
        ui.menufrag.build(ui.menuGroup);
    }

    public void replaceText() {
        Log.info("Replacing version text...");
        vText = Core.settings.getString("Version Text", defVText);

        ui.settings.game.areaTextPref("Version Text", defVText, e -> rebuildMenu());
        ui.menufrag = new NewMenuFrag();
        ((NewMenuFrag)ui.menufrag).createRenderer();
        ui.menuGroup.clear();
        ui.menufrag.build(ui.menuGroup);
    }
}
