package com.com.jnu.recycleview;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.com.jnu.recycleview.data.Book;
import com.com.jnu.recycleview.data.DataSaver;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Loan_Activity<loanBooksAdapter> extends AppCompatActivity {
    private static final int MENU_ID_UPDATE = 1;
    public ArrayList<Book> books_loan;//Book列表
    private loanBooksAdapter loanAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);

        RecyclerView recyclerView_loan = findViewById(R.id.recycle_loan_books);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//垂直
        recyclerView_loan.setLayoutManager(linearLayoutManager);

        DataSaver dataSaver = new DataSaver();
        books_loan = dataSaver.Load(this);

        if (books_loan.size() == 0) {
            for (int i = 1; i < 2; ++i) {
                Book a = new Book("软件项目管理案例教程（第4版）", R.drawable.book_2, "韩万江", "姜立新", "新华出版社", "2002", "1234567", true, "已读", "http.ydrj");
                books_loan.add(a);
                Book b = new Book("创新工程实践", R.drawable.book_no_name, "钱松", "徐振华", "化学工业出版社", "2018", "12345678", true, "已读", "http.cxgc");
                books_loan.add(b);
                Book c = new Book("信息安全数学基础（第2版）", R.drawable.book_1, "徐茂智", "姜立新", "高等教育出版社", "2006", "12345678", true, "已读", "http.cxgc");
                books_loan.add(c);
            }
        }
        loanAdapter = new loanBooksAdapter(books_loan);
        recyclerView_loan.setAdapter(loanAdapter);

        //抽屉DrawerLayout
        Toolbar mToolbar = (Toolbar) findViewById(R.id.loan_toolbar);
        mToolbar.inflateMenu(R.menu.drawer_menu);//添加toolbar的menu部分

        //适配器
        class loanBooksAdapter extends RecyclerView.Adapter<loanBooksAdapter.ViewHolder_1> {

            private final ArrayList<Book> localDataSet;

            public loanBooksAdapter(ArrayList<Book> localDataSet) {
                this.localDataSet = localDataSet;
            }

            class ViewHolder_1 extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
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
                    contextMenu.add(0, MENU_ID_UPDATE, getAdapterPosition(), "Update " + getAdapterPosition());
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
    }
}
