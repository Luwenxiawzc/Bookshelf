package com.com.jnu.recycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.com.jnu.recycleview.data.Book;
import com.com.jnu.recycleview.data.DataSaver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_ID_UPDATE = 1;
    private static final int MENU_ID_DELETE = 2;
    private static final int MENU_ID_DETAILS = 3;
    public ArrayList<Book> books;//Book列表
    private MainRecycleViewAdapter mainRecycleViewAdapter;

    private final ActivityResultLauncher<Intent> addDataLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (null != result) {
                    Intent intent = result.getData();//获得传回的intent
                    if (result.getResultCode() == EditBookActivity.RESULT_CODE_SUCCESS) {
                        assert intent != null;
                        Bundle bundle = intent.getExtras();
                        int position = bundle.getInt("position");//获得传回的当前数据的位置
                        books.add(position, new Book(bundle.getString("title"), R.drawable.book_header, bundle.getString("author"),
                                bundle.getString("translator"), bundle.getString("publisher"), bundle.getString("pubTime"),
                                bundle.getString("isbn"), true, bundle.getString("notes"), bundle.getString("website")));//添加一个新的Book
                        new DataSaver().Save(this, books);//数据保存
                        mainRecycleViewAdapter.notifyItemInserted(position);//通知适配器数据增加
                    }
                }
            });//新的数据传递（add的数据回传）
    private final ActivityResultLauncher<Intent> updateDataLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (null != result) {
                    Intent intent = result.getData();//获得传回的intent
                    if (result.getResultCode() == EditBookActivity.RESULT_CODE_SUCCESS) {
                        assert intent != null;
                        Bundle bundle = intent.getExtras();
                        int position = bundle.getInt("position");//获得传回的当前数据的位置
                        books.get(position).setTitle(bundle.getString("title"));
                        books.get(position).setAuthor(bundle.getString("author"));
                        books.get(position).setTranslator(bundle.getString("translator"));
                        books.get(position).setPublisher(bundle.getString("publisher"));
                        books.get(position).setPubTime(bundle.getString("pubTime"));
                        books.get(position).setIsbn(bundle.getString("isbn"));
                        books.get(position).setNotes(bundle.getString("notes"));
                        books.get(position).setWebsite(bundle.getString("website"));
                        new DataSaver().Save(this, books);//数据保存
                        mainRecycleViewAdapter.notifyItemChanged(position);//通知适配器数据更改
                    }
                }
            });//新的数据传递（update的数据回传）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain = findViewById(R.id.recycle_view_books);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//垂直
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        DataSaver dataSaver = new DataSaver();
        books = dataSaver.Load(this);

        if (books.size() == 0) {
            for (int i = 1; i < 2; ++i) {
                Book a = new Book("软件项目管理案例教程（第4版）", R.drawable.book_2, "韩万江", "姜立新", "新华出版社", "2002", "1234567", true, "已读", "http.ydrj");
                books.add(a);
                Book b = new Book("创新工程实践", R.drawable.book_no_name, "钱松", "徐振华", "化学工业出版社", "2018", "12345678", true, "已读", "http.cxgc");
                books.add(b);
                Book c = new Book("信息安全数学基础（第2版）", R.drawable.book_1, "徐茂智", "姜立新", "高等教育出版社", "2006", "12345678", true, "已读", "http.cxgc");
                books.add(c);
            }
        }
        mainRecycleViewAdapter = new MainRecycleViewAdapter(books);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

        //SearchView搜索
        SearchView searchView = findViewById(R.id.searchview);
        searchView.setIconifiedByDefault(true);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "search!", Toast.LENGTH_SHORT).show();//点击搜索框
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int i = 0;
                for (; i < books.size(); i++) {
                    if (query.equals(books.get(i).getTitle())||query.equals(books.get(i).getAuthor())||query.equals(books.get(i).getTranslator())
                            ||query.equals(books.get(i).getPublisher())||query.equals(books.get(i).getPubTime())||query.equals(books.get(i).getIsbn())
                            ||query.equals(books.get(i).getNotes())||query.equals(books.get(i).getWebsite()))
                    {
                        Intent intent_details=new Intent(MainActivity.this,BookDetailsActivity.class) ;
                        intent_details.putExtra("title",books.get(i).getTitle());
                        intent_details.putExtra("author",books.get(i).getAuthor());
                        intent_details.putExtra("translator",books.get(i).getTranslator());
                        intent_details.putExtra("publisher",books.get(i).getPublisher());
                        intent_details.putExtra("pubTime",books.get(i).getPubTime());
                        intent_details.putExtra("isbn",books.get(i).getIsbn());
                        intent_details.putExtra("notes",books.get(i).getNotes());
                        intent_details.putExtra("website",books.get(i).getWebsite());
                        startActivity(intent_details);
                        Toast.makeText(MainActivity.this, "The book On the Bookshelf!", Toast.LENGTH_SHORT).show();//搜索到这本书就转到详情页面
                        break;
                    }
                }
                if (i == books.size()) {
                    Toast.makeText(MainActivity.this, "There is no such book on the Bookshelf!", Toast.LENGTH_SHORT).show();//搜索不到这本书
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }//关闭搜索
        });

        //抽屉DrawerLayout
        ImageView imageView_1 = findViewById(R.id.book_show);
        imageView_1.setImageResource(R.drawable.book_cover);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.activity_main_navigationView);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        mToolbar.inflateMenu(R.menu.drawer_menu);//添加toolbar的menu部分

        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);//setDrawerListener弃用
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.drawer_menu:
                        mDrawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });

        //悬浮按钮（add）
        FloatingActionButton button=findViewById(R.id.addbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this ,EditBookActivity.class) ;
                intent.putExtra("position",books.size());//传递当前books的长度
                addDataLauncher.launch(intent);
            }
        });

        //悬浮按钮（settings）
        FloatingActionButton button_setting=findViewById(R.id.button_setting);
        button_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SettingsActivity.class) ;
                startActivity(intent);
            }
        });
        //悬浮按钮（about）
        FloatingActionButton button_about=findViewById(R.id.button_about);
        button_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AboutActivity.class) ;
                startActivity(intent);
            }
        });
        //悬浮按钮（existing books）
        FloatingActionButton button_exist=findViewById(R.id.button_exist);
        button_exist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
            }
        });
        //悬浮按钮（loaned books）
        FloatingActionButton button_loan=findViewById(R.id.button_loan);
        button_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AboutActivity.class) ;
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case MENU_ID_UPDATE:
                Intent intentupdate=new Intent(this, EditBookActivity.class);
                intentupdate.putExtra("position",item.getOrder());//传递当前位置
                intentupdate.putExtra("title",books.get(item.getOrder()).getTitle());//传递当前的title
                intentupdate.putExtra("author",books.get(item.getOrder()).getAuthor());
                intentupdate.putExtra("translator",books.get(item.getOrder()).getTranslator());
                intentupdate.putExtra("publisher",books.get(item.getOrder()).getPublisher());
                intentupdate.putExtra("pubTime",books.get(item.getOrder()).getPubTime());
                intentupdate.putExtra("isbn",books.get(item.getOrder()).getIsbn());
                intentupdate.putExtra("notes",books.get(item.getOrder()).getNotes());
                intentupdate.putExtra("website",books.get(item.getOrder()).getWebsite());
                updateDataLauncher.launch(intentupdate);//数据回传
                break;
            case MENU_ID_DELETE:
                AlertDialog alertDialog=new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                            books.remove(item.getOrder());
                            new DataSaver().Save(MainActivity.this,books);//数据保存
                            mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                        }).setNegativeButton(R.string.no, (dialog, which) -> {
                        }).create();
                alertDialog.show();//对话框
                break;
            case MENU_ID_DETAILS:
                Intent intent_details=new Intent(this, BookDetailsActivity.class);
                //Bitmap bitmap=BitmapFactory.decodeResource(getResources(), books.get(item.getOrder()).getCoverResourceId());
                //intent_details.putExtra("cover", bitmap);

                intent_details.putExtra("title",books.get(item.getOrder()).getTitle());
                intent_details.putExtra("author",books.get(item.getOrder()).getAuthor());
                intent_details.putExtra("translator",books.get(item.getOrder()).getTranslator());
                intent_details.putExtra("publisher",books.get(item.getOrder()).getPublisher());
                intent_details.putExtra("pubTime",books.get(item.getOrder()).getPubTime());
                intent_details.putExtra("isbn",books.get(item.getOrder()).getIsbn());
                intent_details.putExtra("notes",books.get(item.getOrder()).getNotes());
                intent_details.putExtra("website",books.get(item.getOrder()).getWebsite());
                startActivity(intent_details);
                break;
        }
        return super.onContextItemSelected(item);
    }

    public  ArrayList<Book> getListBooks(){
        return books;
    }

    public static class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {

        private final ArrayList<Book> localDataSet;

        public static final class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textView_title;
            private final TextView textView_author;
            private final ImageView imageview;

            public ViewHolder(View view) {
                super(view);
                textView_title = view.findViewById(R.id.text_view_book_title);
                textView_author = view.findViewById(R.id.text_view_book_author);
                imageview = view.findViewById(R.id.image_view_book_cover);

                view.setOnCreateContextMenuListener(this);
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,MENU_ID_UPDATE,getAdapterPosition(),"Update "+getAdapterPosition());
                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"Delete "+getAdapterPosition());
                contextMenu.add(0,MENU_ID_DETAILS,getAdapterPosition(),"Details "+getAdapterPosition());
            }//上下文菜单的设置
        }

        public MainRecycleViewAdapter(ArrayList<Book> dataSet) {
            localDataSet = dataSet;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_list_main, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Book book = localDataSet.get(position);
            viewHolder.imageview.setImageResource(book.getCoverResourceId());
            viewHolder.textView_title.setText(book.getTitle());
            viewHolder.textView_author.setText(book.getAuthor());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}

