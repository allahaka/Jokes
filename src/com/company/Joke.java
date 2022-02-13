package com.company;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class Joke {
    public static final String API_URL = "https://v2.jokeapi.dev/joke/";

    public String category;
    public String type;
    public String joke;
    public String setup;
    public String delivery;
    public Integer id;
    public Boolean safe;
    public String language;
    public Boolean nsfw;
    public Boolean religious;
    public Boolean political;
    public Boolean racist;
    public Boolean sexist;
    public Boolean explicit;

    public Integer getJokeLength() { //for sorting by length
        if (this.joke != null) {
            return this.joke.length();
        } else {
            return this.setup.length() + delivery.length();
        }
    }

    public String toString() {
        return Objects.requireNonNullElseGet(this.joke, () -> this.setup + " " + delivery);
    }

    public static String getJokeData(String JokeCategory) throws IOException {

        URL url = new URL(API_URL + JokeCategory + "?format=json");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader( //for efficiency
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

    public static Joke getJoke(String jokeCategory) throws IOException {
        String jokeData = getJokeData(jokeCategory);
        JSONTokener tokener = new JSONTokener(jokeData);
        JSONObject obj = new JSONObject(tokener);

        Joke joke = new Joke();
        joke.category = obj.getString("category");
        joke.type = obj.getString("type");
        joke.joke = obj.has("joke") ? obj.getString("joke") : null;
        joke.setup = obj.has("setup") ? obj.getString("setup") : null;
        joke.delivery = obj.has("delivery") ? obj.getString("delivery") : null;
        joke.id = obj.getInt("id");
        joke.safe = obj.getBoolean("safe");
        joke.language = obj.getString("lang");
        joke.nsfw = obj.getJSONObject("flags").getBoolean("nsfw");
        joke.religious = obj.getJSONObject("flags").getBoolean("religious");
        joke.political = obj.getJSONObject("flags").getBoolean("political");
        joke.racist = obj.getJSONObject("flags").getBoolean("racist");
        joke.sexist = obj.getJSONObject("flags").getBoolean("sexist");
        joke.explicit = obj.getJSONObject("flags").getBoolean("explicit");

        return joke;
    }
}
