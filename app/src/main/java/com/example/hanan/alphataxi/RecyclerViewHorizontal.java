package com.example.hanan.alphataxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanan on 1/13/2016.
 */
public class RecyclerViewHorizontal extends Activity {
    private LinearLayoutManager layoutManager;
    int overallXScroll=0;
    int price=0;
    int tp;
    int total=0;
    int grd=0;
    TextView price1;
    Button tip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_horizontal);
        List<RowData> rowListItem = getRowList();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            price = bundle.getInt("price");
            tp = bundle.getInt("time");
            total=price+tp;

        }
        price1=(TextView) findViewById(R.id.price);
        price1.setText("$"+String.valueOf(total));
        tip=(Button) findViewById(R.id.tip_btn);
        tip.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Intent intent = new Intent(RecyclerViewHorizontal.this, Summary.class);

                                       startActivity(intent);
                                       //  finish();
                                   }
                               });


                layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                recyclerView.setLayoutManager(layoutManager);

                RecyclerViewAdapter adapter = new RecyclerViewAdapter(RecyclerViewHorizontal.this, rowListItem);
                recyclerView.setAdapter(adapter);

                layoutManager.offsetChildrenHorizontal(50);
                recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        int firstPos = layoutManager.findFirstVisibleItemPosition();
                        int lastPos = layoutManager.findLastVisibleItemPosition();
                        int middle = Math.abs(lastPos - firstPos) / 2 + firstPos;

                        super.onScrolled(recyclerView, dx, dy);

                        overallXScroll = overallXScroll + dx;
                        Log.i("check", "overall X  = " + overallXScroll + " position:" + middle);
                        grd = total + ((total / 100) * (middle * 5));
                        price1.setText("$"+String.valueOf(grd));

                    }
                });
            }

            private List<RowData> getRowList() {

                List<RowData> currentItem = new ArrayList<RowData>();

                currentItem.add(new RowData("0%"));
                currentItem.add(new RowData("5%"));
                currentItem.add(new RowData("10%"));
                currentItem.add(new RowData("15%"));
                currentItem.add(new RowData("20%"));
                currentItem.add(new RowData("25%"));
                currentItem.add(new RowData("30%"));
                currentItem.add(new RowData("35%"));
                currentItem.add(new RowData("40%"));


                return currentItem;
            }


        }
