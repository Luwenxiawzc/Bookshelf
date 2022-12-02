package com.com.jnu.recycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.com.jnu.recycleview.data.Book;
import com.com.jnu.recycleview.data.DataSaver_loan;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LoanActivity extends AppCompatActivity {
    private static final int MENU_ID_RETURN = 1;
    public ArrayList<Book> books_loan;//Book列表
    private loanBooksAdapter loanAdapter;
    public static final int RESULT_CODE_SUCCESS_Loan = 777;

    //适配器
    static class loanBooksAdapter extends RecyclerView.Adapter<loanBooksAdapter.ViewHolder_1> {

        private final ArrayList<Book> localDataSet;

        public loanBooksAdapter(ArrayList<Book> localDataSet) {
            this.localDataSet = localDataSet;
        }

        static class ViewHolder_1 extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textView_title;
            private final TextView textView_author;
            private final ImageView imageview;

            public ViewHolder_1(View view) {
                super(view);
                textView_title = view.findViewById(R.id.text_view_book_title_loan);
                textView_author = view.findViewById(R.id.text_view_book_author_loan);
                imageview = view.findViewById(R.id.image_view_book_cover_loan);
                view.setOnCreateContextMenuListener(this);
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0, MENU_ID_RETURN, getAdapterPosition(), "return" + getAdapterPosition());
            }//上下文菜单的设置
        }

        @Override
        @NonNull
        public ViewHolder_1 onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_list_loan, viewGroup, false);
            return new ViewHolder_1(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder_1 holder, int position) {
            Book book = localDataSet.get(position);
            holder.imageview.setImageResource(book.getCoverResourceId());
            holder.textView_title.setText(book.getTitle());
            holder.textView_author.setText(book.getAuthor());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==MENU_ID_RETURN) {
            AlertDialog alertDialog_loan = new AlertDialog.Builder(this)
                    .setTitle(R.string.confirmation)
                    .setMessage(R.string.sure_to_return)
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                        Intent intent_return = new Intent();//将要还的书本的书籍传递回MainActivity
                        intent_return.putExtra("title", books_loan.get(item.getOrder()).getTitle());
                        intent_return.putExtra("author", books_loan.get(item.getOrder()).getAuthor());
                        intent_return.putExtra("translator", books_loan.get(item.getOrder()).getTranslator());
                        intent_return.putExtra("publisher", books_loan.get(item.getOrder()).getPublisher());
                        intent_return.putExtra("pubTime", books_loan.get(item.getOrder()).getPubTime());
                        intent_return.putExtra("isbn", books_loan.get(item.getOrder()).getIsbn());
                        intent_return.putExtra("notes", books_loan.get(item.getOrder()).getNotes());
                        intent_return.putExtra("website", books_loan.get(item.getOrder()).getWebsite());
                        setResult(RESULT_CODE_SUCCESS_Loan, intent_return);//结果码
                        books_loan.remove(item.getOrder());//在Loan_Activity删除已归还的书本
                        new DataSaver_loan().Save(LoanActivity.this, books_loan);//数据保存
                        loanAdapter.notifyItemRemoved(item.getOrder());
                        LoanActivity.this.finish();//记得关闭当前的activity

                    }).setNegativeButton(R.string.no, (dialog, which) -> {
                    }).create();
            alertDialog_loan.show();//对话框
        }
        return super.onContextItemSelected(item);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        RecyclerView recyclerView_loan = findViewById(R.id.recycle_loan_books);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//垂直
        recyclerView_loan.setLayoutManager(linearLayoutManager);

        DataSaver_loan dataSaver = new DataSaver_loan();
        books_loan = dataSaver.Load(this);

        //没有借出的书本时
        if (books_loan.size() == 0) {
            Toast.makeText(LoanActivity.this,R.string.book_null, Toast.LENGTH_SHORT).show();//点击搜索框
        }
        loanAdapter = new loanBooksAdapter(books_loan);
        recyclerView_loan.setAdapter(loanAdapter);

        //抽屉DrawerLayout
        Toolbar mToolbar = (Toolbar) findViewById(R.id.loan_toolbar);
        mToolbar.inflateMenu(R.menu.drawer_menu);//添加toolbar的menu部分

        if(this.getIntent().getStringExtra("title")!=null) {//当从Loaned books悬浮按钮转过来的
            String title=this.getIntent().getStringExtra("title");//传入当前title;
            String author = this.getIntent().getStringExtra("author");
            String translator = this.getIntent().getStringExtra("translator");
            String publisher = this.getIntent().getStringExtra("publisher");
            String pubTime = this.getIntent().getStringExtra("pubTime");
            String isbn = this.getIntent().getStringExtra("isbn");
            String notes = this.getIntent().getStringExtra("notes");
            String website = this.getIntent().getStringExtra("website");
            Book book_new_loan = new Book();
            book_new_loan.setTitle(title);
            book_new_loan.setAuthor(author);
            book_new_loan.setCoverResourceId(R.drawable.book_header);
            book_new_loan.setTranslator(translator);
            book_new_loan.setPublisher(publisher);
            book_new_loan.setPubTime(pubTime);
            book_new_loan.setIsbn(isbn);
            book_new_loan.setNotes(notes);
            book_new_loan.setWebsite(website);
            books_loan.add(book_new_loan);
            new DataSaver_loan().Save(this, books_loan);//数据保存
            loanAdapter.notifyItemInserted(books_loan.size());//通知适配器数据增加
        }
        Button button_return=findViewById(R.id.button_loan_return);//返回按钮
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoanActivity.this.finish();//记得关闭当前的activity
            }
        });
    }
}
