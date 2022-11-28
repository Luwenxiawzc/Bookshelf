package com.com.jnu.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditBookActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 666;
    private int position;//传入与传出当前位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_activity);

        position=this.getIntent().getIntExtra("position",0);//传入当前位置
        ImageView imageView_edit_cover=findViewById(R.id.book_edit_cover);
        EditText book_edit_title=findViewById(R.id.book_edit_title);
        EditText book_edit_author=findViewById(R.id.book_edit_author);
        EditText book_edit_translator=findViewById(R.id.book_edit_translator);
        EditText book_edit_publisher=findViewById(R.id.book_edit_publisher);
        EditText book_edit_pubTime=findViewById((R.id.book_edit_pubTime));
        EditText book_edit_isbn=findViewById(R.id.book_edit_isbn);
        EditText book_edit_notes=findViewById(R.id.book_edit_notes);
        EditText book_edit_website=findViewById(R.id.book_edit_website);
        imageView_edit_cover.setImageResource(R.drawable.book_header);

        String title=this.getIntent().getStringExtra("title");//传入当前title
        if(null!=title){
            book_edit_title.setText(title);//将EditText的值修改为传过来的title
            book_edit_author.setText(this.getIntent().getStringExtra("author"));
            book_edit_translator.setText(this.getIntent().getStringExtra("translator"));
            book_edit_publisher.setText(this.getIntent().getStringExtra("publisher"));
            book_edit_pubTime.setText(this.getIntent().getStringExtra("pubTime"));
            book_edit_isbn.setText(this.getIntent().getStringExtra("isbn"));
            book_edit_notes.setText(this.getIntent().getStringExtra("notes"));
            book_edit_website.setText(this.getIntent().getStringExtra("website"));
        }//title为空是增加操作，不为空是更新操作

        Button button_yes=findViewById(R.id.button_确定);
        Button button_no=findViewById(R.id.button_取消);

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("title", book_edit_title.getText().toString());//传回新输入的title
                bundle.putString("author", book_edit_author.getText().toString());
                bundle.putString("translator", book_edit_translator.getText().toString());
                bundle.putString("publisher", book_edit_publisher.getText().toString());
                bundle.putString("pubTime", book_edit_pubTime.getText().toString());
                bundle.putString("isbn", book_edit_isbn.getText().toString());
                bundle.putString("notes", book_edit_notes.getText().toString());
                bundle.putString("website", book_edit_website.getText().toString());
                bundle.putInt("position",position);//传回当前位置

                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS, intent);//结果码
                EditBookActivity.this.finish();//记得关闭当前的activity
            }
        });
        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBookActivity.this.finish();//选择取消按钮退出EditBookActivity
            }
        });
    }
}