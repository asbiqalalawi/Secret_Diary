package com.example.secretdiary;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.secretdiary.adapter.NoteAdapter;
import com.example.secretdiary.db.NoteHelper;
import com.example.secretdiary.entity.Note;
import com.example.secretdiary.helper.MappingHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements LoadNotesCallback{
    private ProgressBar progressBar;
    private RecyclerView rvNotes;
    private NoteAdapter adapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        ViewPager viewPager = findViewById(R.id.view_pager);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);

        progressBar = v.findViewById(R.id.progressbar);
        rvNotes = v.findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNotes.setHasFixedSize(true);
        adapter = new NoteAdapter(getActivity());
        rvNotes.setAdapter(adapter);

//        FloatingActionButton fabAdd = v.findViewById(R.id.fab_add);
//        fabAdd.setOnClickListener(view -> {
//            Intent intent = new Intent(HomeFragment.class, NoteAddUpdateActivity.class);
//            startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_ADD);
//        });


//        if (savedInstanceState == null) {
//            new LoadNotesAsync( noteHelper, this).execute();
//        } else {
//            ArrayList<Note> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
//            if (list != null) {
//                adapter.setListNotes(list);
//            }
//        }
        return v;
    }


    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<Note> notes) {
        progressBar.setVisibility(View.INVISIBLE);
        if (notes.size() > 0) {
            adapter.setListNotes(notes);
        } else {
            adapter.setListNotes(new ArrayList<>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private static class LoadNotesAsync extends AsyncTask<Void, Void, ArrayList<Note>> {

        private final WeakReference<NoteHelper> weakNoteHelper;
        private final WeakReference<LoadNotesCallback> weakCallback;

        private LoadNotesAsync(NoteHelper noteHelper, LoadNotesCallback callback) {
            weakNoteHelper = new WeakReference<>(noteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Note> doInBackground(Void... voids) {
            Cursor dataCursor = weakNoteHelper.get().queryAll();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Note> notes) {
            super.onPostExecute(notes);

            weakCallback.get().postExecute(notes);

        }
    }


    private void showSnackbarMessage(String message) {
        Snackbar.make(rvNotes, message, Snackbar.LENGTH_SHORT).show();
    }
}

