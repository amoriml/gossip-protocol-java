package br.com.ufabc.GossipProtocol.util;

import br.com.ufabc.GossipProtocol.model.PeerState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class JSONParse {

    private JSONParse() {
    }

    public static String HashToJson(HashMap<Integer, PeerState> mapa) {
        Type type = new TypeToken<HashMap<Integer, PeerState>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.toJson(mapa, type);
    }

    public static HashMap<Integer, PeerState> JsonToHash(String jsonRecive) {
        try {
            Type type = new TypeToken<HashMap<Integer, PeerState>>() {
            }.getType();
            Gson gson = new Gson();
            return gson.fromJson(jsonRecive, type);
        } catch (ClassCastException e) {
            System.out.println(e.getCause().getCause() + "\n");
            throw new RuntimeException(e);
        }
    }

}
