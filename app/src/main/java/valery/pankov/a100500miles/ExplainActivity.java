package valery.pankov.a100500miles;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Valery on 17.03.2017.
 */

public class ExplainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvExplain;
    private TextView tvWhenDates;
    private String url;
    private ArrayList<String> datum;
    private String exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explain);

        imageView = (ImageView) findViewById(R.id.img);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvExplain = (TextView) findViewById(R.id.tvExplain);
        tvWhenDates = (TextView) findViewById(R.id.tvWhenDates);

        Bundle extras = getIntent().getExtras();
        final String urlImage = extras.getString("URLIMAGE");
        url = extras.getString("URL");

        Glide.with(this)
                .load(urlImage)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);

        ParseMain parseMain = new ParseMain();
        parseMain.execute();

        try {
            String explain =  new String();
            datum = parseMain.get();
            tvTitle.setText(datum.get(0));
            tvDate.setText(datum.get(1));

            for(int i =2;i<datum.size()-1;i++){
                explain =  explain + datum.get(i);

            }
            Spannable s = (Spannable) Html.fromHtml(explain);
            for (URLSpan u: s.getSpans(0, s.length(), URLSpan.class)) {
                s.setSpan(new UnderlineSpan() {
                    public void updateDrawState(TextPaint tp) {
                        tp.setUnderlineText(false);

                    }
                }, s.getSpanStart(u), s.getSpanEnd(u), 0);
            }
            tvExplain.setText(s);
            //tvWhenDates.setText(datum.get(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    class ParseMain extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            //HashMap<String,String> hashMap = new HashMap<>();

            try {
                //Document document = Jsoup.connect("http://www.ndu.edu.ua/index.php/ua/vsi-novuni").get();
                Document document = Jsoup.connect(url).userAgent("Mozilla").get();
                datum = new ArrayList<>();
                datum.add(document.select("h1").first().text());
                datum.add(document.select(".meta-date").first().text());
                Elements elements = document.select("p");

                for (Element element:elements){
                    datum.add(element.toString());

                    if(element.text().contains("ример")){
                        System.out.println("-fgbfgsbdgndhg---");
                        break;
                    }
                    System.out.println("-----PPPPP-------");
                   // System.out.println(element.toString());
                }

                //datum.add(document.select("p").get(1).text());
                //datum.add(document.select("p").get(3).text());
                //datum.add(document.select(".content").first().toString());
                //System.out.println("------");
                //System.out.println(document.select(".content").first().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }


            return datum;
        }
    }
}
