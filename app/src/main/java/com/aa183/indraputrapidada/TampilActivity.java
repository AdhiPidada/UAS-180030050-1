package com.aa183.indraputrapidada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TampilActivity extends AppCompatActivity {

    private ImageView imgFilm;
    private TextView tvJudul, tvTanggal, tvCaption, tvGenre, tvSinFilm;
    private String linkBerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        imgFilm = findViewById(R.id.iv_film);
        tvJudul = findViewById(R.id.tv_judul);
        tvTanggal = findViewById(R.id.tv_tanggal);
        tvCaption = findViewById(R.id.tv_caption);
        tvGenre = findViewById(R.id.tv_genre);
        tvSinFilm = findViewById(R.id.tv_sinopsis_film);

        Intent terimaData = getIntent();
        tvJudul.setText(terimaData.getStringExtra("JUDUL"));
        tvTanggal.setText(terimaData.getStringExtra("TANGGAL"));
        tvCaption.setText(terimaData.getStringExtra("CAPTION"));
        tvGenre.setText(terimaData.getStringExtra("GENRE"));
        tvSinFilm.setText(terimaData.getStringExtra("SINOPSIS_FILM"));
        String imgLocation = terimaData.getStringExtra("GAMBAR");

        try {
            File file = new File(imgLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            imgFilm.setImageBitmap(bitmap);
            imgFilm.setContentDescription(imgLocation);
        } catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText( this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
        linkBerita = terimaData.getStringExtra("LINK");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tampil_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.item_bagikan)
        {
            Intent bagikanBerita = new Intent(Intent.ACTION_SEND);
            bagikanBerita.putExtra(Intent.EXTRA_SUBJECT, tvJudul.getText().toString());
            bagikanBerita.putExtra(Intent.EXTRA_TEXT, linkBerita);
            bagikanBerita.setType("text/plain");
            startActivity(Intent.createChooser(bagikanBerita,"Bagikan berita"));
        }
        return super.onOptionsItemSelected(item);

    }
}
