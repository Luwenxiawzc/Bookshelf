package com.com.jnu.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.com.jnu.recycleview.data.Book;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Intent intent = getIntent();

        ImageView imageView_show_cover=findViewById(R.id.book_show_cover);
        TextView textView_show_title=findViewById(R.id.book_show_title);
        TextView textView_show_author=findViewById(R.id.book_show_author);
        TextView textView_show_translator=findViewById(R.id.book_show_translator);
        TextView textView_show_publisher=findViewById(R.id.book_show_publisher);
        TextView textView_show_pubTime=findViewById((R.id.book_show_pubTime));
        TextView textView_show_isbn=findViewById(R.id.book_show_isbn);
        TextView textView_show_notes=findViewById(R.id.book_show_notes);
        TextView textView_show_website=findViewById(R.id.book_show_website);
        Button button_return=findViewById(R.id.button_return);//返回按钮

        //Bitmap bitmap = intent.getParcelableExtra("bitmap");
        //imageView_show_cover.setImageBitmap(bitmap);

        imageView_show_cover.setImageResource(R.drawable.book_header);

        //imageView_show_cover.setImageResource(intent.getIntExtra("cover"));

        textView_show_title.setText(intent.getStringExtra("title"));
        textView_show_author.setText(intent.getStringExtra("author"));
        textView_show_translator.setText(intent.getStringExtra("translator"));
        textView_show_publisher.setText(intent.getStringExtra("publisher"));
        textView_show_pubTime.setText(intent.getStringExtra("pubTime"));
        textView_show_isbn.setText(intent.getStringExtra("isbn"));
        textView_show_notes.setText(intent.getStringExtra("notes"));
        textView_show_website.setText(intent.getStringExtra("website"));

        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookDetailsActivity.this.finish();//记得关闭当前的activity
            }
        });
    }
}