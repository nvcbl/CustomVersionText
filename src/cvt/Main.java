package cvt;

import arc.*;
import arc.struct.*;
import arc.util.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.fragments.*;

import cvt.NewMenuFrag.*;

import static mindustry.Vars.*;

public class Main extends Mod{
    public static String vText;
    public static final String defVText = "AYO THIS MF AIN'T CHANGED THE TEXT THINGY IN SETTINGS > GAME > VERSION TEXT !!!";

    public Main(){
        Events.on(ClientLoadEvent.class, e -> replaceText());
    }

    public static void rebuildMenu() {
        vText = Core.settings.getString("Version Text", defVText);
        ui.menuGroup.clear();
        ui.menufrag.build(ui.menuGroup);
    }

    public static void replaceText() {
        Log.info("Replacing version text...");
        vText = Core.settings.getString("Version Text", defVText);

        ui.settings.game.areaTextPref("Version Text", defVText, e -> rebuildMenu());
        ui.menufrag.addButton("New Background", Icon.refresh, () -> ((NewMenuFrag) ui.menufrag).createRenderer());
        setMenuFrag();
    }

    public static <T> T cast(Object obj, Class<T> cls) {
        return cls != null && cls.isInstance(obj) ? cls.cast(obj) : null;
    }

    public static void setMenuFrag() {
        var oldButtons = new Seq<MenuButton>();

        if (ui.menufrag instanceof NewMenuFrag nmf) {
            oldButtons.set(nmf.customButtons);
        } else {
            try {
                var btnsField = ui.menufrag.getClass().getDeclaredField("customButtons");
                btnsField.setAccessible(true);
                var btns = cast(btnsField.get(ui.menufrag), Seq.class);

                for (var button : btns) {
                    if (button instanceof MenuFragment.MenuButton btn) {
                        oldButtons.add(new MenuButton(btn));
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException("wow i love java", e);
            }
        }

        ui.menufrag = new NewMenuFrag();
        var asNew = (NewMenuFrag) ui.menufrag;
        asNew.createRenderer();
        asNew.customButtons.set(oldButtons);
        rebuildMenu();
    }
}
