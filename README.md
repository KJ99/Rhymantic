# Rhymantic
Projekt zaliczeniowy z przedmiotu Przetwarzanie danych w chmurze obliczeniowej

## Opis projektu
Aplikacja wyszukująca rymy dla podanego słowa za pomocą zewnętrznego API

## Wykorzystane API i biblioteki
1. Do wyszukiwania rymów aplikacja korzysta z API https://rhymebrain.com (dokładny url https://rhymebrain.com/talk?function=getRhymes)
1. Do deserializacji obiektów JSON'owych aplikacja używa biblioteki gson

```
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

```
## Zrzuty ekranowe

Ekran główny przed wyszukaniem rymów
![alt text](https://github.com/KJ99/Rhymantic/blob/master/screenshots/3.png?raw=true)

Rezultaty po wyszukaniu
![alt text](https://github.com/KJ99/Rhymantic/blob/master/screenshots/1.png?raw=true)
![alt text](https://github.com/KJ99/Rhymantic/blob/master/screenshots/2.png?raw=true)
![alt text](https://github.com/KJ99/Rhymantic/blob/master/screenshots/4.png?raw=true)
