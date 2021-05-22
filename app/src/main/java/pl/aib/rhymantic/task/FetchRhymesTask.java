package pl.aib.rhymantic.task;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import pl.aib.rhymantic.model.WordResult;

public class FetchRhymesTask extends AsyncTask<String, WordResult, List<WordResult>> {

    @Override
    protected List<WordResult> doInBackground(String... words) {
        try {
            return this.fetchForWord(words[0]);
        } catch (IOException e) {
            return  new ArrayList<>();
        }
    }

    private List<WordResult> fetchForWord(String word) throws IOException {
        String address = "https://rhymebrain.com/talk?function=getRhymes&word=" + word;
        String response = this.fetch(address);
        Gson gson = new Gson();
        WordResult[] data = gson.fromJson(response, WordResult[].class);

        return Arrays.asList(data);
    }

    private String fetch(String address) throws IOException {
        URL url = new URL(address);

        HttpURLConnection con=(HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

        List<String> lines = new ArrayList<>();
        reader.lines().forEach(lines::add);

        return lines.stream().collect(Collectors.joining());
    }
}
