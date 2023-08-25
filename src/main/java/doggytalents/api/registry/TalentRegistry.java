package doggytalents.api.registry;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import net.minecraftforge.common.MinecraftForge;

import doggytalents.DoggyLogger;
import doggytalents.api.inferface.ITalent;
import doggytalents.lib.Constants;

/**
 * @author ProPercivalalb
 */
public class TalentRegistry {

    public static List<ITalent> talents = new ArrayList<ITalent>();
    public static Map<String, ITalent> idtalent = new LinkedHashMap<String, ITalent>();
    public static Map<ITalent, String> talentid = new LinkedHashMap<ITalent, String>();

    public static void registerTalent(ITalent talent) {

        if (Constants.DISABLED_TALENTS.contains(talent.getKey())) {
            DoggyLogger.LOGGER
                .log(Level.WARNING, "The talent id {0} has been disabled in the config file", talent.getKey());
        }

        if (talents.contains(talent)) {
            DoggyLogger.LOGGER.log(Level.WARNING, "The talent id {0} has already been registered", talent.getKey());
        }

        else if (talent.getKey()
            .contains(":")) {
                DoggyLogger.LOGGER
                    .log(Level.WARNING, "A talent id can't have the character ':' in it ({0})", talent.getKey());
            }

        else {
            talents.add(talent);
            idtalent.put(talent.getKey(), talent);
            talentid.put(talent, talent.getKey());
            MinecraftForge.EVENT_BUS.register(talent);
            DoggyLogger.LOGGER.log(Level.INFO, "Register the talent with the id {0}", talent.getKey());
        }

    }

    public static List<ITalent> getTalents() {
        return talents;
    }

    public static ITalent getTalent(int index) {
        return talents.get(index);
    }

    public static ITalent getTalent(String id) {
        return idtalent.get(id);
    }
}
