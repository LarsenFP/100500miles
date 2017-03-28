package valery.pankov.a100500miles;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ArrayList<LatestNews> itemList;
    private LatestNews latestNews;
    private ListView listView;
    private RecyclerView mRecyclerView;
    private TextView textViewExplain;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;
    private String url;
    private String explain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemList = new ArrayList<LatestNews>();


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //mAdapter = new RecyclerAdapter(itemList);
        //mRecyclerView.setAdapter(mAdapter);

        ParseMain parseMain = new ParseMain();
        parseMain.execute();

        try {
            itemList = parseMain.get();

//            for (int i = 0; i<itemList.size();i++){
//                System.out.println("Number is " +i + ": " + itemList.get(i).getUrlImage());
//            }
           RecyclerAdapter arrayAdapter = new RecyclerAdapter(itemList);

            mRecyclerView.setAdapter(arrayAdapter);
            arrayAdapter.SetOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    url = itemList.get(position).getUrl();
                    Intent intent = new Intent(MainActivity.this, ExplainActivity.class);
                    intent.putExtra("URL",itemList.get(position).getUrl());
                    intent.putExtra("URLIMAGE", itemList.get(position).getUrlImage());
                    startActivity(intent);
                    System.out.println(itemList.get(position).getTitle());
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    class ParseMain extends AsyncTask<Void, Void, ArrayList<LatestNews>>{

        @Override
        protected ArrayList<LatestNews> doInBackground(Void... voids) {
            //HashMap<String,String> hashMap = new HashMap<>();
            try {
                //Document document = Jsoup.connect("http://www.ndu.edu.ua/index.php/ua/vsi-novuni").get();
                Document document = Jsoup.connect("https://100500miles.ru").userAgent("Mozilla").get();
                Elements elements = document.select(".row ");
                Elements elements1 = elements.get(elements.size()-1).remove();
                for (Element element:elements){

                    System.out.println(elements.size());

                    Element element1 = element.select(".col-md-10").first();
                    if(!element1.equals(null)){
                        System.out.println("Null");
                    }
//                    String str = element1.text();
 //                   Element element2 = element.select(".col-md-10 h2 a[href]").first();
   //                 Element element3 = element.select(".col-md-2 .post-thumbnail img").first();
     //               Element element4 = element.select(".post-meta").first();
                    //hashMap.put(element1.text(),element2.attr("abs:href"));
                    System.out.println("--------------");
//                    System.out.println(str);
//                    System.out.println(element2.attr("abs:href"));
//                    System.out.println(element3.attr("abs:src"));
//                    latestNews = new LatestNews(element1.select("h2").first().text(),element2.attr("abs:href"),element3.attr("abs:src"),"views");
 //                   itemList.add(latestNews);

                    //System.out.println(element1.text() + " ---- " + element2.attr("abs:href") + " ---- " + element3.attr("abs:src"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


//            try {
//                Document document = Jsoup.connect("https://www.google.ru/").get();
//
//                Elements elements = document.select("col-lg-3");
//                for (Element element:elements){
//
//                    Element element1 = elements.select("a[href]").first();
//                    hashMap.put(element.text(),element1.attr("abs:href"));
//
//                    System.out.println("--------------");
//                    System.out.println(hashMap.entrySet().toString());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            return itemList;
        }

    }


}
