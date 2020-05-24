package com.aa183.indraputrapidada;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class DatabaseHandler extends SQLiteOpenHelper {
    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM = "t_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_CAPTION = "Caption";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_SINOPSIS_FILM = "Sin_Film";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private Context context;


    public DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_CAPTION + " TEXT, "
                + KEY_GENRE + " TEXT, " + KEY_SINOPSIS_FILM + " TEXT, "
                + KEY_LINK + " TEXT);";
        db.execSQL(CREATE_TABLE_FILM);

        inisialisasiFilmAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void tambahFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_CAPTION, dataFilm.getCaption());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_SINOPSIS_FILM, dataFilm.getSinFilm());
        cv.put(KEY_LINK, dataFilm.getLink());
        db.insert(TABLE_FILM,null, cv);
        db.close();
    }

    public void tambahFilm (Film dataFilm, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_CAPTION, dataFilm.getCaption());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_SINOPSIS_FILM, dataFilm.getSinFilm());
        cv.put(KEY_LINK, dataFilm.getLink());
        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm(Film dataFilm) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_CAPTION, dataFilm.getCaption());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_SINOPSIS_FILM, dataFilm.getSinFilm());
        cv.put(KEY_LINK, dataFilm.getLink());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm(int idFilm) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm() {
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if (csr.moveToFirst()) {
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er) {
                    er.printStackTrace();
                }
                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)
                );

                dataFilm.add(tempFilm);

            } while (csr.moveToNext());
        }
        return dataFilm;
    }

    private String storeImageFile(int id){
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;

    }

    private void inisialisasiFilmAwal(SQLiteDatabase db){
        int idFilm = 0;
        Date tempDate = new Date();
        try {
            tempDate = sdFormat.parse("06/12/2019 00:00");
        } catch (ParseException er){
            er.printStackTrace();
        }


        Film film1 = new Film(
                idFilm,
                "Brahms: The Boy II (2020)",
                tempDate, storeImageFile(R.drawable.film1),
                "Setelah sebuah keluarga pindah ke Heelshire Mansion, putra muda mereka segera berteman dengan boneka mirip kehidupan yang disebut Brahms",
                "Horror, Drama, Mystery",
                "Film ‘Brahms: The Boy II’ mengisahkan tentang sebuah keluarga yang pindah ke Heelshire Mansion. Suatu hari, Jude (Christopher Convery) menemukan sebuah boneka laki-laki di tumpukan dedaunan. \n" +
                        "\n" +
                        "Jude bersama ibunya Liza (Katie Holmes) membersihkan dan memakaikan baju bersih pada Brahms.Mulai sejak itu, kejadian gaib mulai terjadi di keluarga kecil ini. \n" +
                        "\n" +
                        "Jude memberi tahu beberapa peraturan yang diinginkan Brahms seperti tidak boleh menerima tamu, tidak boleh menutup wajah Brahms, serta pakaian baru setiap hari untuknya.\n" +
                        "\n" +
                        " Keadaan akan memburuk ketika mereka melanggar peraturan Brahms.Boneka pendendam itu memiliki kekuatan yang luar biasa di balik penampilannya yang biasa. Ia berhasil memanipulasi mental keluarga ini.\n" +
                        "\n" +
                        " Setiap harinya keluarga ini seperti diawasi oleh Brahms.Hal tersebut membuat Liza curiga dan bertanya-tanya ada apa dibalik boneka Brahms. \n" +
                        "\n" +
                        "Ia pun menyelidiki masa lalu yang terjadi di Heelshire Mansion dan Brahms, boneka hidup tersebut.Perilisan film ini telah ditunda sebanyak dua kali.\n" +
                        "\n" +
                        " Rencana awalnya The Boy II akan dirilis pada 27 Juli 2019, lalu diundur menjadi 6 Desember 2019. Alasan pengunduran ini guna menghindari waktu rilis yang bersamaan dengan film Chucky Child's Play dan Annabelle Comes Home.\n",
                "https://www.imdb.com/title/tt9173418/"
        );

        tambahFilm(film1, db);
        idFilm++;

        tempDate = new Date();
        try {
            tempDate = sdFormat.parse("26/02/2020 00:00");
        } catch (ParseException er){
            er.printStackTrace();
        }
        Film film2 = new Film(
                idFilm,
                "Sonic the Hedgehog (2020)",
                tempDate,storeImageFile(R.drawable.film2)                ,
                "Setelah menemukan landak kecil, biru, dan cepat, seorang perwira polisi kota kecil harus membantunya mengalahkan seorang jenius jahat yang ingin melakukan percobaan padanya.",
                "Action, Adventure, Comedy",
                "Sonic The Hedgehog bercerita tentang Sonic, karakter berbentuk landak biru. Dia memiliki kemampuan berlari dan melakukan gerakan yang sangat cepat.\n" +
                        "\n" +
                        " Dia bisa menghindari berbagai peluru dan bom dengan kemampuannya. Saat Sonic sedang berlatih berlari, listrik sebagian negara padam. Hal tersebut membuat pimpinan militer harus turun tangan dan mencari penyebabnya. \n" +
                        "\n" +
                        "Pimpinan militer memerintahkan Dr. Robotnik (Jim Carrey) sebagai orang yang bertanggungjawab dalam proyek ini. Dalam proses pencariannya, Dr. Robotnik mengetahui bahwa pelaku yang membuat listrik padam adalah Sonic. \n" +
                        "\n" +
                        "Dia berencana menangkap Sonic untuk mengambil kekuatannya dan ingin menguasai dunia. Dalam masa pengejaran, Sonic bertemu dengan Tom Wachowski (James Marsden), pria yang akan menjadi sahabatnya kemudian. Sonic dan Tom harus menghindari pengejaran dari Dr. Robotnik.\n",
                "https://www.imdb.com/title/tt3794354/"
        );
        tambahFilm(film2, db);
        idFilm++;

        tempDate = new Date();
        try {
            tempDate = sdFormat.parse("07/08/2020 00:00");
        } catch (ParseException er){
            er.printStackTrace();
        }
        Film film3 = new Film(
                idFilm,
                "Peter Rabbit 2: The Runaway (2020)",
                tempDate,
                storeImageFile(R.drawable.film3),
                "Thomas dan Bea sekarang menikah dan tinggal bersama Peter dan keluarga kelinci-nya. Bosan dengan kehidupan di taman, Peter pergi ke kota besar, di mana ia bertemu tokoh-tokoh teduh dan akhirnya menciptakan kekacauan untuk seluruh keluarga.",
                "Adventure, Comedy, Family",
                "Konflik di antara mereka terjadi lantaran Peter dan keluarganya sering mencuri buah-buahan di kebuh Thomas. Peter tidak melakukan itu sendiri, selain dengan anggota keluarga yang isinya sama-sama kelinci, ada pula hewan lain seperti babi, rusa, landak dan lainnya. Thomas selalu ingin menyingkirkan Peter dan kawanannya. \n" +
                        "\n" +
                        "Namun Peter dan kawanannya juga tidak mudah ditaklukkan. Hingga tiba suatu hari, ada tetangga bernama Bea. Berbeda dengan Thomas, dia tipe perempuan yang menyukai binatang, terutama kelinci. Dia memiliki ikatan emosional khusus dengan kelinci sejak orang tuanya meninggal. Setiap harinya, Bea juga hobi melukis kelinci di tempat-tempat yang asri.\n" +
                        "\n" +
                        "Peter dan Thomas saling berebut perhatian Bea. Tentu ini tantangan bagi Thomas yang harus menaruh egonya yang selalu ingin membasmi binatang yang menurutnya mengganggu. Namun demi cinta, semua sangat mungkin dia lakukan. Pada film kedua ini, Thomas dan Bea hendak menikah. Hal ini membuat Peter cukup bimbang. \n" +
                        "\n" +
                        " Dia juga masih tidak menyukai Thomas, Peter menganggap Thomas masih membencinya. Seusai menikah, Thomas dan Bea berencana memiliki anak. Hal ini menambah potensi Peter untuk keluar dari kehidupan Bea. Kemudian Peter memutuskan pergi ke kota bersama teman-temannya, ke tempat yang memiliki banyak buah, pasar petani. \n" +
                        "\n"+
                        "Mereka mengambil, atau tepatnya mencuri, banyak buah di pasar itu. Sayangnya mereka tertangkap dan tidak semua bisa melarikan diri. Kini Peter harus mencari cara menyelamatkan teman-temannya yang tertangkap. Para pengisi suara pada film ini di antaranya James Corden, Rose Byrne, Domhnall Gleeson, David Oyelowo, Elizabeth Debicki, dan Margot Robbie.\n" ,
                "https://www.imdb.com/title/tt8376234/"
        );
        tambahFilm(film3,db);
        idFilm++;

        tempDate = new Date();
        try {
            tempDate = sdFormat.parse("18/03/2020 00:00");
        } catch (ParseException er){
            er.printStackTrace();
        }
        Film film4 = new Film(
                idFilm,
                "Quite Place pt 2 (2020)",
                tempDate,
                storeImageFile(R.drawable.film4),
                "Setelah kejadian di rumah, keluarga Abbott sekarang menghadapi teror dunia luar. Terpaksa menjelajah ke tempat yang tidak diketahui, mereka menyadari bahwa makhluk yang berburu dengan suara bukan satu-satunya ancaman yang bersembunyi di luar jalur pasir.\n",
                "Drama, Horror, Sci-Fi",
                "Peristiwa tragis yang pernah dilalui keluarga Abbott membuat mereka semakin waspada terhadap makhluk misterius yang menjajah belahan bumi.Akan tetapi, mereka telah mengetahui titik kelemahan makhluk-makhluk mengerikan tersebut.\n" +
                        "\n" +
                        "Dengan langkah kaki yang pelan dan penuh sunyi, seluruh anggota keluarga Abbott pun menelusuri berbagai daerah untuk melawan para makhluk pemangsa itu sekaligus menemukan tempat yang lebih aman lagi.\n",
                "https://www.imdb.com/title/tt8332922/"
        );
        tambahFilm(film4, db);
    }
}

